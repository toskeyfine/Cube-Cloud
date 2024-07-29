package com.toskey.cube.service.sequence.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.cache.component.CubeLock;
import com.toskey.cube.common.cache.component.IDistributedLock;
import com.toskey.cube.common.core.annotation.EntityProperties;
import com.toskey.cube.common.core.annotation.EntityProperty;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.tenant.component.TenantContextHolder;
import com.toskey.cube.service.sequence.business.config.SequenceProperties;
import com.toskey.cube.service.sequence.business.constant.SequenceConstants;
import com.toskey.cube.service.sequence.business.constant.enums.IncrementType;
import com.toskey.cube.service.sequence.business.constant.enums.RuleType;
import com.toskey.cube.service.sequence.business.constant.enums.SequenceCodeType;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceSection;
import com.toskey.cube.service.sequence.business.domain.entity.Sequence;
import com.toskey.cube.service.sequence.business.domain.entity.SequenceRule;
import com.toskey.cube.service.sequence.business.domain.mapper.SequenceMapper;
import com.toskey.cube.service.sequence.business.vo.CombinedId;
import com.toskey.cube.service.sequence.business.vo.SequenceFormVO;
import com.toskey.cube.service.sequence.business.vo.SequenceQueryResultVO;
import com.toskey.cube.service.sequence.business.vo.SequenceQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * SequenceServiceImpl
 *
 * @author zhongxing
 * @date 2023/8/21 15:06
 */
@Service
@RequiredArgsConstructor
public class SequenceService extends ServiceImpl<SequenceMapper, Sequence> implements IService<Sequence> {

    private final SequenceRuleService sequenceRuleService;

    private final SequenceSectionService sequenceSectionService;

    private final IDistributedLock distributedLock;

    private final RedisTemplate<String, Object> redisTemplate;

    private final DataSource dataSource;

    private final SequenceProperties sequenceProperties;

    public PageData<SequenceQueryResultVO> page(SequenceQueryVO query) {
        IPage<Sequence> page = PageUtils.buildPage();
        List<Sequence> list = baseMapper.selectList(
                page,
                Wrappers.<Sequence>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), Sequence::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), Sequence::getCode, query.getCode())
                        .eq(Objects.nonNull(query.getRuleType()), Sequence::getRuleType, query.getRuleType())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(list, SequenceQueryResultVO.class));
    }

    public SequenceQueryResultVO findById(String id) {
        Sequence sequence = baseMapper.selectSequenceById(id);
        if (Objects.nonNull(sequence)) {
            List<SequenceRule> ruleList = sequence.getRuleList();
            if (CollectionUtils.isNotEmpty(ruleList)) {
                ruleList.stream()
                        .filter(rule -> rule.getType() == SequenceCodeType.ID_SECTION)
                        .forEach(rule -> {
                            SequenceSection sequenceSection = sequenceSectionService.getOne(Wrappers.<SequenceSection>lambdaQuery()
                                    .eq(SequenceSection::getRuleId, rule.getId()));
                            rule.setSequenceSection(sequenceSection);
                        });
            }
            return sequence.toMapper(SequenceQueryResultVO.class, EntityProperty.Strategy.RESULT);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveSequence(SequenceFormVO form) {
        Sequence sequence = form.toEntity();
        return saveSequence(sequence);
    }

    public boolean saveSequence(Sequence sequence) {
        if (checkCodeExists(null, sequence.getCode())) {
            throw new BusinessException("已存在相同编码的序列.");
        }
        sequence.setTenantId(TenantContextHolder.getContext().getId());
        super.save(sequence);

        String code = sequence.getCode();

        if (sequence.getRuleType() == RuleType.INCREMENT_DB) {
            // 初始化数据库自增序列
            baseMapper.insertDbSequence(SequenceConstants.DB_ID_PREFIX + code, 1L, sequence.getIncrementStepLength(), 1L, null, false);
        } else if (sequence.getRuleType() == RuleType.INCREMENT_RDS) {
            // 初始化redis自增序列
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            redisTemplate.opsForValue().set(SequenceConstants.buildRedisIncrementValueKey(code), 1L);
        } else if (sequence.getRuleType() == RuleType.COMBINED) {
            List<SequenceRule> ruleList = sequence.getRuleList();
            ruleList.forEach(r -> r.setSequenceId(sequence.getId()));
            sequenceRuleService.saveBatchSequenceRule(ruleList);
            // 重新排序
            List<CombinedId.CombinedProperty> combinedProperties = ruleList.stream()
                    .map(r -> {
                        SequenceCodeType codeType = r.getType();
                        if (codeType == SequenceCodeType.MACHINE_CODE) {
                            return CombinedId.CombinedProperty.of(codeType,
                                    String.valueOf(sequenceProperties.getMachineId()),
                                    r.getOrdered()
                            );
                        } else if (codeType == SequenceCodeType.DATETIME) {
                            return CombinedId.CombinedProperty.of(codeType,
                                    r.getFormat(),
                                    r.getOrdered()
                            );
                        } else if (codeType == SequenceCodeType.FIXED_CODE ||
                                codeType == SequenceCodeType.MODULE_CODE ||
                                codeType == SequenceCodeType.VERSION) {
                            return CombinedId.CombinedProperty.of(codeType,
                                    r.getContent(),
                                    r.getOrdered()
                            );
                        } else {
                            return CombinedId.CombinedProperty.of(codeType, null, r.getOrdered());
                        }
                    })
                    .sorted(Comparator.comparing(CombinedId.CombinedProperty::getOrdered))
                    .toList();

            boolean requiredDatetime = combinedProperties.stream()
                    .anyMatch(p -> p.getType() == SequenceCodeType.DATETIME);
            long sectionCount = combinedProperties.stream()
                    .filter(p -> p.getType() == SequenceCodeType.ID_SECTION)
                    .count();
            if (!requiredDatetime || sectionCount == 0) {
                throw new BusinessException("自定义序列必须包含日期或号段，否则无法保证唯一.");
            }
            if (sectionCount > 1) {
                throw new BusinessException("自定义序列只允许包含一个号段规则.");
            }

            CombinedId combinedId = CombinedId.of(Boolean.FALSE, combinedProperties);

            Optional<CombinedId.CombinedProperty> sectionProperty = combinedProperties.stream()
                    .filter(p -> p.getType() == SequenceCodeType.ID_SECTION)
                    .findFirst();
            sectionProperty.ifPresent(property -> {
                combinedId.setHasSection(Boolean.TRUE);
                // 处理号段参数
                Optional<SequenceRule> rule = ruleList.stream()
                        .filter(r -> r.getType() == SequenceCodeType.ID_SECTION)
                        .findFirst();
                rule.ifPresent(r -> {
                    SequenceSection sequenceSection = r.getSequenceSection();
                    redisTemplate.opsForValue().set(SequenceConstants.buildSectionParamKey(code), sequenceSection);
                    if (sequenceProperties.getSectionMode().equals("redis")) {
                        // 初始化序列
                        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                        redisTemplate.opsForValue().set(SequenceConstants.buildSectionCurrentKey(code), sequenceSection.getBegin());
                    } else {
                        // 初始化数据库自增序列
                        baseMapper.insertDbSequence(SequenceConstants.DB_ID_PREFIX + code, sequenceSection.getBegin(), sequenceSection.getStep(), sequenceSection.getBegin(), sequenceSection.getFinish(), false);
                    }
                });
            });
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate.opsForValue().set(SequenceConstants.buildCombinedKey(code), combinedId);
        } else if (sequence.getRuleType() == RuleType.SNOWFLAKE) {
            if (sequenceProperties.getSnowflakeMode().equals("redis")) {
                redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                redisTemplate.opsForValue().set(SequenceConstants.buildSnowflakeSequenceKey(code), 1L);
                redisTemplate.opsForValue().set(SequenceConstants.buildSnowflakeTimeKey(code), System.currentTimeMillis());
            } else {
                // 初始化数据库自增序列
                baseMapper.insertDbSequence(SequenceConstants.DB_ID_PREFIX + code, 1L, 1, 1L, null, false);
            }
        } else {
            throw new BusinessException("错误的规则类型.");
        }
        return Boolean.TRUE;
    }

    public String nextId(String code) {
        Sequence sequence = baseMapper.selectOne(Wrappers.<Sequence>lambdaQuery().eq(Sequence::getCode, code));
        if (sequence == null) {
            throw new BusinessException("序列不存在或已被删除.");
        }
        RuleType ruleType = sequence.getRuleType();
        return switch (ruleType) {
            case INCREMENT_DB -> incrementIdGenerate(sequence, IncrementType.DB);
            case INCREMENT_RDS -> incrementIdGenerate(sequence, IncrementType.REDIS);
            case SNOWFLAKE -> snowflakeIdGenerate(code);
            case COMBINED -> combinedIdGenerate(code);
            default -> throw new BusinessException("未知的序列类型.");
        };
    }

    /**
     * 删除序列
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        Sequence sequence = baseMapper.selectById(id);
        if (Objects.nonNull(sequence)) {
            String code = sequence.getCode();

            super.removeById(id);

            switch (sequence.getRuleType()) {
                case INCREMENT_DB ->  baseMapper.dropDbSequence(SequenceConstants.DB_ID_PREFIX + code);
                case INCREMENT_RDS -> redisTemplate.delete(SequenceConstants.buildRedisIncrementValueKey(code));
                case SNOWFLAKE -> {
                    // 防止本次启动时切换了雪花算法序列的存储模式，所以redis和db中的序列全部删除
                    redisTemplate.delete(SequenceConstants.buildSnowflakeSequenceKey(code));
                    redisTemplate.delete(SequenceConstants.buildSnowflakeTimeKey(code));
                    baseMapper.dropDbSequence(SequenceConstants.DB_ID_PREFIX + code);
                }
                case COMBINED -> {
                    redisTemplate.delete(SequenceConstants.buildSectionParamKey(code));
                    redisTemplate.delete(SequenceConstants.buildSectionCurrentKey(code));
                    baseMapper.dropDbSequence(SequenceConstants.DB_ID_PREFIX + code);
                    redisTemplate.delete(SequenceConstants.buildCombinedKey(code));
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 重置
     *
     * @param code
     * @return
     */
    public boolean reset(String code) {
        Sequence sequence = baseMapper.selectOne(Wrappers.<Sequence>lambdaQuery().eq(Sequence::getCode, code));
        if (Objects.nonNull(sequence)) {
            return switch (sequence.getRuleType()) {
                case INCREMENT_DB -> {          // 数据库自增序列
                    baseMapper.restartSequence(SequenceConstants.DB_ID_PREFIX + code, 1L);
                    yield true;
                }
                case INCREMENT_RDS -> {         // Redis自增序列
                    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                    redisTemplate.opsForValue().set(SequenceConstants.buildRedisIncrementValueKey(code), 1L);
                    yield true;
                }
                case COMBINED -> {              // 自定义组合序列
                    SequenceSection sequenceSection = (SequenceSection) redisTemplate.opsForValue().get(SequenceConstants.buildSectionParamKey(code));
                    if (Objects.nonNull(sequenceSection)) {
                        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                        redisTemplate.opsForValue().set(SequenceConstants.buildSectionCurrentKey(code), sequenceSection.getBegin());
                    }
                    yield true;
                }
                default -> false;
            };
        }
        return Boolean.FALSE;
    }

    /**
     * 规则组合序列生成
     *
     * @param code 序列编码
     * @return
     */
    private String combinedIdGenerate(String code) {
        // 先获取组合ID对象
        CombinedId combinedId = (CombinedId) redisTemplate.opsForValue().get(SequenceConstants.buildCombinedKey(code));
        if (Objects.nonNull(combinedId)) {
            // 得到组合属性列表
            List<CombinedId.CombinedProperty> properties = combinedId.getProperties();
            if (CollectionUtils.isNotEmpty(properties)) {
                StringBuilder genIdBuilder = new StringBuilder();
                // 遍历属性，依次拼接
                for (int i = 0, size = properties.size(); i < size; i++) {
                    CombinedId.CombinedProperty property = properties.get(i);
                    if (property.getType() == SequenceCodeType.DATETIME) {
                        // 日期类型按需格式化
                        if (StringUtils.isNotBlank(property.getContent())) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(property.getContent());
                            genIdBuilder.append(formatter.format(LocalDateTime.now()));
                        } else {
                            genIdBuilder.append(System.currentTimeMillis());
                        }
                    } else if (property.getType() == SequenceCodeType.ID_SECTION) {
                        // 号段根据配置类型选择数据库序列或Redis序列
                        SequenceSection sequenceSection = (SequenceSection) redisTemplate.opsForValue().get(SequenceConstants.buildSectionParamKey(code));
                        if (sequenceProperties.getSectionMode().equals("redis")) {
                            if (Objects.nonNull(sequenceSection)) {
                                long sectionId = redisTemplate.opsForValue().increment(SequenceConstants.buildSectionCurrentKey(code), sequenceSection.getStep());
                                if (sectionId > sequenceSection.getFinish()) {
                                    // TODO throw exception...
                                }
                                genIdBuilder.append(strPadding(String.valueOf(sectionId), String.valueOf(sequenceSection.getFinish()).length(), "0"));
                            }
                        } else {
                            PostgresSequenceMaxValueIncrementer incrementer = new PostgresSequenceMaxValueIncrementer(dataSource, SequenceConstants.DB_ID_PREFIX + code);
                            incrementer.setPaddingLength(String.valueOf(sequenceSection.getFinish()).length());
                            String localSectionId = incrementer.nextStringValue();
                            if (StringUtils.isBlank(localSectionId)) {
                                // TODO throw exception...
                            }
                            genIdBuilder.append(localSectionId);
                        }
                    } else {
                        // 其他类型按顺序拼接
                        genIdBuilder.append(property.getContent());
                    }
                }
                return genIdBuilder.toString();
            }
        }
        return null;
    }

    /**
     * 自增序列生成
     *
     * @param sequence 序列对象
     * @param type     类型
     * @return
     */
    private String incrementIdGenerate(Sequence sequence, IncrementType type) {
        String code = sequence.getCode();
        String result = null;
        // 获取锁
        try (CubeLock lock = distributedLock.lock(code, 10L, TimeUnit.SECONDS, false)) {
            if (type == IncrementType.DB) {
                // 数据库自增序列
                PostgresSequenceMaxValueIncrementer incrementer = new PostgresSequenceMaxValueIncrementer(dataSource, SequenceConstants.DB_ID_PREFIX + sequence.getCode());
                incrementer.setPaddingLength(sequence.getTotalLength());
                result = incrementer.nextStringValue();
            } else if (type == IncrementType.REDIS) {
                // Redis自增序列
                redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                String key = SequenceConstants.buildRedisIncrementValueKey(code);
                Object cache = redisTemplate.opsForValue().get(key);
                if (cache != null) {
                    result = String.valueOf(redisTemplate.opsForValue().increment(key, sequence.getIncrementStepLength()));
                }
            }

            if (StringUtils.isNotBlank(result) && sequence.getPadding() == 1) {
                return strPadding(result, sequence.getTotalLength(), sequence.getPaddingStr());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成基于雪花算法的ID
     * 参考Twitter的雪花算法，避免时钟回拨问题
     *
     * @param code
     * @return
     */
    private String snowflakeIdGenerate(String code) {
        long sequence = -1;
        long lastTimestamp = System.currentTimeMillis();
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        Object cache = redisTemplate.opsForValue().get(SequenceConstants.buildSnowflakeTimeKey(code));
        if (Objects.nonNull(cache)) {
            lastTimestamp = (long) cache;
        }

        if (sequenceProperties.getSnowflakeMode().equals("redis")) {
            sequence = redisTemplate.opsForValue().increment(SequenceConstants.buildSnowflakeSequenceKey(code));
        } else {
            PostgresSequenceMaxValueIncrementer incrementer = new PostgresSequenceMaxValueIncrementer(dataSource, SequenceConstants.DB_ID_PREFIX + code);
            sequence = incrementer.nextLongValue();
        }
        // 获取当前的时间戳，单位是毫秒
        long timestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 下面是说假设在同一个毫秒内，又发送了一个请求生成一个id
        // 这个时候就得把seqence序号给递增1，最多就是4096
        if (lastTimestamp == timestamp) {
            // 避免1ms内产生大于4095的序列
            sequence = sequence & 0xFFF;
            // 当某一毫秒的时间，产生的id数超过4095，系统会进入等待，直到下一毫秒，系统继续产生ID
            if (sequence == 0) {
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else { // 时间戳改变，毫秒内序列重置
            sequence = 0;
            if (sequenceProperties.getSnowflakeMode().equals("local")) {
                baseMapper.restartSequence(SequenceConstants.DB_ID_PREFIX + code, 1L);
            } else {
                redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                redisTemplate.opsForValue().set(SequenceConstants.buildSnowflakeSequenceKey(code), 1L);
            }
        }

        // 上次生成ID的时间截
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.opsForValue().set(SequenceConstants.buildSnowflakeTimeKey(code), timestamp);

        // 这儿就是最核心的二进制位运算操作，生成一个64bit的id
        // 先将当前时间戳左移，放到41 bit那儿；将机房id左移放到5 bit那儿；将机器id左移放到5 bit那儿；将序号放最后12 bit
        // 最后拼接起来成一个64 bit的二进制数字，转换成10进制就是个long型
        long result = ((timestamp - 1585644268888L) << 22) |
                (sequenceProperties.getDatacenterId() << 5) |
                (sequenceProperties.getMachineId() << 5) | sequence;
        return String.valueOf(result);
    }

    public boolean checkCodeExists(String id, String code) {
        return exists(Wrappers.<Sequence>lambdaQuery()
                .eq(Sequence::getCode, code)
                .eq(StringUtils.isNotBlank(id), Sequence::getId, id)
        );
    }
    /**
     * 字符串填充
     * 将不足长度的字符串前位补'0'
     *
     * @param str
     * @param length
     * @return
     */
    private String strPadding(String str, int length, String paddingStr) {
        if (str.length() < length) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length - str.length(); ++i) {
                sb.append(paddingStr);
            }
            sb.append(str);
            return sb.toString();
        }
        return str;
    }

}
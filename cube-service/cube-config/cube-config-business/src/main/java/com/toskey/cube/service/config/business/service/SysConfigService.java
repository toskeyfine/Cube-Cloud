package com.toskey.cube.service.config.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.service.config.business.domain.entity.SysConfig;
import com.toskey.cube.service.config.business.domain.mapper.SysConfigMapper;
import com.toskey.cube.service.config.business.vo.config.ConfigFormVO;
import com.toskey.cube.service.config.business.vo.config.ConfigQueryResultVO;
import com.toskey.cube.service.config.business.vo.config.ConfigQueryVO;
import com.toskey.cube.service.config.interfaces.dto.ConfigDTO;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * SysConfigService
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Service
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> implements IService<SysConfig> {

    private final RedisTemplate<String, Object> redisTemplate;

    public SysConfigService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public PageData<ConfigQueryResultVO> findPage(ConfigQueryVO query) {
        IPage<SysConfig> page = PageUtils.buildPage();
        List<SysConfig> list = baseMapper.selectList(
                page,
                Wrappers.<SysConfig>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getName()), SysConfig::getName, query.getName())
                        .like(StringUtils.isNotBlank(query.getCode()), SysConfig::getCode, query.getCode())
                        .eq(StringUtils.isNotBlank(query.getStatus()), SysConfig::getStatus, query.getStatus())
                        .eq(StringUtils.isNotBlank(query.getGroupId()), SysConfig::getGroupId, query.getGroupId())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(list, ConfigQueryResultVO.class));
    }

    public ConfigQueryResultVO findById(String id) {
        SysConfig config = getById(id);
        if (config != null) {
            return config.toMapper(ConfigQueryResultVO.class);
        }
        return null;
    }

    public SysConfig findByCode(String code) {
        Object cacheObj = redisTemplate.opsForValue().get(CacheConstants.CACHE_CONFIG_PREFIX + code);
        if (cacheObj != null) {
            return (SysConfig) cacheObj;
        } else {
            SysConfig config = getOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getCode, code));
            redisTemplate.opsForValue().set(CacheConstants.CACHE_CONFIG_PREFIX + code, config);
            return config;
        }
    }

    public ConfigQueryResultVO findVOByCode(String code) {
        SysConfig config = findByCode(code);
        if (config != null) {
            return config.toMapper(ConfigQueryResultVO.class);
        }
        return null;
    }

    public ConfigDTO findDTOByCode(String code) {
        SysConfig config = findByCode(code);
        if (config != null) {
            return config.toMapper(ConfigDTO.class);
        }
        return null;
    }

    public boolean saveConfig(SysConfig config) {
        config.setId(null);
        if (checkCodeExists(null, config.getCode())) {
            throw new BusinessException("配置编码已存在.");
        }
        return save(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveConfig(ConfigFormVO form) {
        SysConfig config = form.toEntity();
        return saveConfig(config);
    }

    public boolean updateConfig(SysConfig config) {
        if (StringUtils.isBlank(config.getId())) {
            throw new BusinessException("配置ID为空.");
        }
        if (checkCodeExists(config.getId(), config.getCode())) {
            throw new BusinessException("配置编码已存在.");
        }
        redisTemplate.delete(CacheConstants.CACHE_CONFIG_PREFIX + config.getCode());
        return updateById(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateConfig(ConfigFormVO form) {
        SysConfig config = form.toEntity();
        return updateConfig(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeConfig(String[] ids) {
        List<SysConfig> configs = baseMapper.selectBatchIds(Arrays.asList(ids));
        configs.stream()
                .map(config -> buildCacheKey(config.getCode()))
                .forEach(redisTemplate::delete);
        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkCodeExists(String id, String code) {
        return exists(
                Wrappers.<SysConfig>lambdaQuery()
                        .eq(SysConfig::getCode, code)
                        .ne(StringUtils.isNotBlank(id), SysConfig::getId, id)
        );
    }

    private String buildCacheKey(String code) {
        return String.format("%s:%s", CacheConstants.CACHE_CONFIG_PREFIX, code);
    }

}

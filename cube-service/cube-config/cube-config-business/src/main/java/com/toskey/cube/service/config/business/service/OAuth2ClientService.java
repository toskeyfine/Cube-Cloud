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
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.config.business.domain.entity.OAuth2Client;
import com.toskey.cube.service.config.business.domain.mapper.OAuth2ClientMapper;
import com.toskey.cube.service.config.business.vo.client.ClientFormVO;
import com.toskey.cube.service.config.business.vo.client.ClientQueryResultVO;
import com.toskey.cube.service.config.business.vo.client.ClientQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * OAuth2ClientService
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Service
public class OAuth2ClientService extends ServiceImpl<OAuth2ClientMapper, OAuth2Client> implements IService<OAuth2Client> {

    private final RedisTemplate<String, Object> redisTemplate;

    public OAuth2ClientService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public PageData<ClientQueryResultVO> findPage(ClientQueryVO query) {
        IPage<OAuth2Client> page = PageUtils.buildPage();
        List<OAuth2Client> list = baseMapper.selectList(
                page,
                Wrappers.<OAuth2Client>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getClientName()), OAuth2Client::getClientName, query.getClientName())
                        .like(StringUtils.isNotBlank(query.getClientId()), OAuth2Client::getClientId, query.getClientId())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(list, ClientQueryResultVO.class));
    }

    public ClientQueryResultVO findById(String id) {
        OAuth2Client client = getById(id);
        return client.toMapper(ClientQueryResultVO.class);
    }

    public boolean saveClient(OAuth2Client client) {
        if (StringUtils.isNotBlank(client.getId())) {
            client.setId(null);
        }
        if (checkClientIdExists(null, client.getClientId())) {
            throw new BusinessException("客户端ID已存在.");
        }
        String cacheKey = buildCacheKey(client.getClientId());
        redisTemplate.opsForValue().set(cacheKey, client.toRegisteredClient());
        return save(client);
    }
    /**
     * 新增
     *
     * @param form 客户端信息
     * @return
     */
    public boolean saveClient(ClientFormVO form) {
        OAuth2Client client = form.toEntity();
        String sk = String.format("sk-%s", UUID.randomUUID().toString().replace("-", ""));
        client.setClientSecret(sk);
        // 校验参数格式
        return saveClient(client);
    }

    public boolean updateClient(OAuth2Client client) {
        if (StringUtils.isBlank(client.getId())) {
            throw new BusinessException("客户端数据错误.");
        }
        if (checkClientIdExists(client.getId(), client.getClientId())) {
            throw new BusinessException("客户端ID已存在");
        }
        if (StringUtils.isNotBlank(client.getClientSecret())) {
            client.setClientSecret(null);
        }

        String cacheKey = buildCacheKey(client.getClientId());
        redisTemplate.delete(cacheKey);
        redisTemplate.opsForValue().set(cacheKey, client.toRegisteredClient());

        return updateById(client);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateClient(ClientFormVO form) {
        OAuth2Client client = form.toEntity();
        return updateClient(client);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeClient(String[] ids) {
        List<OAuth2Client> list = baseMapper.selectBatchIds(Arrays.asList(ids));
        list.stream()
                .map(OAuth2Client::getClientId)
                .map(this::buildCacheKey)
                .forEach(redisTemplate::delete);

        return removeBatchByIds(Arrays.asList(ids));
    }

    public boolean checkClientIdExists(String id, String clientId) {
        return exists(
                Wrappers.<OAuth2Client>lambdaQuery()
                        .eq(OAuth2Client::getClientId, clientId)
                        .ne(StringUtils.isNotBlank(id), OAuth2Client::getId, id)
        );
    }

    private String buildCacheKey(String clientId) {
        return String.format("%s:%s:%s", CacheConstants.CACHE_GLOBAL_PREFIX, CacheConstants.CACHE_REGISTERED_CLIENT_KEY, clientId);
    }

}

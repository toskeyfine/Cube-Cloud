package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.resource.server.principal.LoginUser;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.interfaces.service.RemoteUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Optional;

/**
 * CubePasswordUserDetailsServiceImpl
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:13
 */
public class CubePasswordUserDetailsServiceImpl implements CubeUserDetailsService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RemoteUserService remoteUserService;

    public CubePasswordUserDetailsServiceImpl(final RedisTemplate<String, Object> redisTemplate,
            final RemoteUserService remoteUserService) {
        this.redisTemplate = redisTemplate;
        this.remoteUserService = remoteUserService;
    }
    @Override
    public boolean support(AuthorizationGrantType grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType.getValue());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String cacheKey = buildCacheKey(username);
        Object cache = redisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            return (LoginUser) cache;
        }
        RestResult<UserDTO> result = remoteUserService.loadUserByUsername(username);
        if (result == null || !result.isSuccess()) {
            throw new BusinessException("");
        }
        LoginUser loginUser = (LoginUser) Optional.ofNullable(result.getData())
                .map(this::buildUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        redisTemplate.opsForValue().set(cacheKey, loginUser);
        return loginUser;
    }

    private String buildCacheKey(String username) {
        return String.format("%s:login_user:%s", CacheConstants.CACHE_GLOBAL_PREFIX, username);
    }
}

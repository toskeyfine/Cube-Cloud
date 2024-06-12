package com.toskey.cube.common.resource.server.component;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * CubePasswordUserDetailsServiceImpl
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:13
 */
public class CubePasswordUserDetailsServiceImpl implements CubeUserDetailsService {
    @Override
    public boolean support(AuthorizationGrantType grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType.getValue());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

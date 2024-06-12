package com.toskey.cube.common.resource.server.component;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * CubeUserDetailsService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:49
 */
public interface CubeUserDetailsService extends UserDetailsService {

    boolean support(AuthorizationGrantType grantType);

}

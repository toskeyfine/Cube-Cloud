package com.toskey.cube.common.security.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.exception.BusinessException;
import com.toskey.cube.common.security.principal.LoginUser;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.interfaces.service.RemoteUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Optional;

/**
 * CubeSmsUserDetailsServiceImpl
 *
 * @author toskey
 * @version 1.0.0
 */
public class SmsUserDetailsServiceImpl implements CubeUserDetailsService {

    private final RemoteUserService remoteUserService;

    public SmsUserDetailsServiceImpl(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RestResult<UserDTO> result = remoteUserService.loadUserByMobile(username);
        if (result == null || !result.isSuccess()) {
            throw new BusinessException("用户查询错误.");
        }
        return Optional.ofNullable(result.getData())
                .map(this::buildUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在."));
    }
}

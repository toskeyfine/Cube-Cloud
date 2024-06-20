package com.toskey.cube.service.sas.interfaces.service.fallback;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.interfaces.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * RemoteUserServiceFallbackFactory
 *
 * @author toskey
 * @version 1.0.0
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService() {
            @Override
            public RestResult<UserDTO> loadUserByUsername(String username) {
                return RestResult.failure();
            }

            @Override
            public RestResult<UserDTO> loadUserByMobile(String mobile) {
                return RestResult.failure();
            }

            @Override
            public RestResult<Boolean> lock(String username) {
                return RestResult.failure();
            }
        };
    }
}

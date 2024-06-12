package com.toskey.cube.sas.interfaces.service.fallback;

import com.toskey.cube.sas.interfaces.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * RemoteUserServiceFallbackFactory
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:09
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return null;
    }
}

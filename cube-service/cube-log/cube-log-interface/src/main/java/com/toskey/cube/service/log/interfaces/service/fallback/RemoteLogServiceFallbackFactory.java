package com.toskey.cube.service.log.interfaces.service.fallback;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import com.toskey.cube.service.log.interfaces.service.RemoteLogService;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * RemoteLogServiceFallbackFactory
 *
 * @author toskey
 * @version 1.0.0
 */
public class RemoteLogServiceFallbackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable cause) {
        return new RemoteLogService() {
            @Override
            public RestResult<Boolean> saveOperationLog(OperationLogDTO operationLogDTO) {
                return null;
            }

            @Override
            public RestResult<Boolean> saveLoginLog(LoginLogDTO loginLogDTO) {
                return null;
            }
        };
    }
}

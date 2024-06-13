package com.toskey.cube.service.log.interfaces.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.constant.ServiceNameConstants;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import com.toskey.cube.service.log.interfaces.service.fallback.RemoteLogServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * RemoteLogService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:12
 */
@FeignClient(contextId = "remoteLogService", name = ServiceNameConstants.SERVICE_LOG, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {

    @PostMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/log/operation")
    RestResult<Boolean> saveOperationLog(@RequestBody OperationLogDTO operationLogDTO, @RequestHeader(SecurityConstants.HEADER_SERVICE) String serviceHeader);

    @PostMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/log/login")
    RestResult<Boolean> saveLoginLog(@RequestBody LoginLogDTO loginLogDTO, @RequestHeader(SecurityConstants.HEADER_SERVICE) String serviceHeader);

}

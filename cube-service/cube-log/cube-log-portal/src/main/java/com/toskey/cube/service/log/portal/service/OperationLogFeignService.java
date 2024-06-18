package com.toskey.cube.service.log.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.service.log.business.service.OperationLogService;
import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RemoteOperationLogService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:58
 */
@RestController
@RequestMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/log/operation")
public class OperationLogFeignService {

    private final OperationLogService operationLogService;

    public OperationLogFeignService(final OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @PostMapping
    public RestResult<Boolean> saveLog(@Validated @RequestBody OperationLogDTO logDTO) {
        return RestResult.success(operationLogService.saveLog(logDTO));
    }

}

package com.toskey.cube.service.log.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import com.toskey.cube.service.log.business.service.LoginLogService;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginLogFeignService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:58
 */
@RestController
@RequestMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/log/login")
public class LoginLogFeignService {

    private final LoginLogService loginLogService;

    public LoginLogFeignService(final LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @AuthIgnore
    @PostMapping
    public RestResult<Boolean> saveLog(@Validated @RequestBody LoginLogDTO loginLogDTO) {
        return RestResult.success(loginLogService.saveLog(loginLogDTO));
    }
}

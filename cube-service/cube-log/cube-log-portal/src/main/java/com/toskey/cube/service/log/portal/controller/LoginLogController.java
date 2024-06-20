package com.toskey.cube.service.log.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.log.business.service.LoginLogService;
import com.toskey.cube.service.log.business.vo.LoginLogQueryResultVO;
import com.toskey.cube.service.log.business.vo.LoginLogQueryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginLogController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/log/login")
public class LoginLogController {

    private final LoginLogService loginLogService;

    public LoginLogController(final LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @GetMapping("/page")
    public RestResult<PageData<LoginLogQueryResultVO>> page(LoginLogQueryVO query) {
        return RestResult.success(loginLogService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<LoginLogQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(loginLogService.findById(id));
    }

}

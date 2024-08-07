package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.log.annotation.RequestLog;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import com.toskey.cube.service.sas.business.service.SysUserService;
import com.toskey.cube.service.sas.business.vo.user.UserFormVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryResultVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysUserController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final SysUserService userService;

    public SysUserController(final SysUserService userService) {
        this.userService = userService;
    }

    @RequestLog("列表查询")
    @GetMapping("/page")
    public RestResult<PageData<UserQueryResultVO>> page(UserQueryVO query) {
        return RestResult.success(userService.findPage(query));
    }

    @RequestLog(value = "查询详情", content = "查询ID为#｛id｝的用户详情")
    @GetMapping("/{id}")
    public RestResult<UserQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(userService.findById(id));
    }

    @RequestLog("新增用户")
    @PostMapping
    public RestResult<Boolean> save(@RequestBody UserFormVO form) {
        return RestResult.success(userService.saveUser(form));
    }

    @RequestLog("修改用户")
    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody UserFormVO form) {
        return RestResult.success(userService.updateUser(form));
    }

    @RequestLog("删除用户")
    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(userService.removeUser(ids));
    }

}

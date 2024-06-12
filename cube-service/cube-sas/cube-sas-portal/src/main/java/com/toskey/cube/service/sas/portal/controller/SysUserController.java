package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysUserService;
import com.toskey.cube.service.sas.business.vo.user.UserFormVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryResultVO;
import com.toskey.cube.service.sas.business.vo.user.UserQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysUserController
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:58
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final SysUserService userService;

    public SysUserController(final SysUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public RestResult<PageData<UserQueryResultVO>> page(UserQueryVO query) {
        return RestResult.success(userService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<UserQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(userService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody UserFormVO form) {
        return RestResult.success(userService.saveUser(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody UserFormVO form) {
        return RestResult.success(userService.updateUser(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(userService.removeUser(ids));
    }


}

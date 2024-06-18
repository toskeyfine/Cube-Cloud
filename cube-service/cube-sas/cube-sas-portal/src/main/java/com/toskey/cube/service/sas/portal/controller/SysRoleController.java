package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysRoleService;
import com.toskey.cube.service.sas.business.vo.role.PermissionBindVO;
import com.toskey.cube.service.sas.business.vo.role.RoleFormVO;
import com.toskey.cube.service.sas.business.vo.role.RoleQueryResultVO;
import com.toskey.cube.service.sas.business.vo.role.RoleQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysRoleController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private final SysRoleService roleService;

    public SysRoleController(final SysRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/page")
    public RestResult<PageData<RoleQueryResultVO>> page(RoleQueryVO query) {
        return RestResult.success(roleService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<RoleQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(roleService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody RoleFormVO form) {
        return RestResult.success(roleService.saveRole(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody RoleFormVO form) {
        return RestResult.success(roleService.updateRole(form));
    }

    @PostMapping("/permission")
    public RestResult<Boolean> bindPermission(@Validated @RequestBody PermissionBindVO bindVO) {
        return RestResult.success(roleService.bindPermission(bindVO));
    }

}

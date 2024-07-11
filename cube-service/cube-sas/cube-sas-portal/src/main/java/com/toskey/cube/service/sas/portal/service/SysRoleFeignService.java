package com.toskey.cube.service.sas.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import com.toskey.cube.service.sas.business.service.SysRoleService;
import com.toskey.cube.service.sas.interfaces.dto.RoleDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysRoleFeignService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/11 14:48
 */
@RestController
@RequestMapping("/ms/sys/role")
public class SysRoleFeignService {

    private final SysRoleService roleService;

    public SysRoleFeignService(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @AuthIgnore
    @GetMapping("/{userId}/list")
    public RestResult<List<RoleDTO>> listByUserId(@PathVariable("userId") String userId) {
        List<RoleDTO> result = roleService.findListByUserId(userId);
        if (result == null) {
            return RestResult.failure();
        }
        return RestResult.success(result);
    }
}

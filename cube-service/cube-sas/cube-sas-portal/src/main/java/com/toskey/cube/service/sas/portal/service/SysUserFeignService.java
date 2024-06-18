package com.toskey.cube.service.sas.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.business.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysUserFeignService
 *
 * @author toskey
 * @version 1.0.0
 */
@AuthIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/user")
public class SysUserFeignService {
    
    private final SysUserService userService;

    @GetMapping("/account/{username}")
    public RestResult<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        return RestResult.success(userService.findDTOByUsername(username));
    }

    @GetMapping("/mobile/{mobile}")
    public RestResult<UserDTO> getUserByMobile(@PathVariable("mobile") String mobile) {
        return RestResult.success(userService.findDTOByMobile(mobile));
    }

    @PutMapping("/{username}/locked")
    public RestResult<Boolean> lock(@PathVariable("username") String username) {
        return RestResult.success(userService.lock(username));
    }

    @PutMapping("/{username}/unlocked")
    public RestResult<Boolean> unLock(@PathVariable("username") String username) {
        return RestResult.success(userService.unlock(username));
    }

}

package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.service.sas.business.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final SysUserService userService;

    public ProfileController(final SysUserService userService) {
        this.userService = userService;
    }

}

package com.toskey.cube.service.config.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.resource.server.annotation.AuthIgnore;
import com.toskey.cube.service.config.business.service.SysConfigService;
import com.toskey.cube.service.config.interfaces.dto.ConfigDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysConfigFeignService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/20 17:46
 */
@AuthIgnore
@RestController
@RequestMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/config")
public class SysConfigFeignService {

    private final SysConfigService configService;

    public SysConfigFeignService(SysConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/{code}")
    public RestResult<ConfigDTO> getByCode(@PathVariable("code") String code) {
        return RestResult.success(configService.findDTOByCode(code));
    }
}

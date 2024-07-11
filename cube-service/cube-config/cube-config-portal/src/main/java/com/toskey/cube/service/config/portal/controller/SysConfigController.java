package com.toskey.cube.service.config.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.config.business.service.SysConfigService;
import com.toskey.cube.service.config.business.vo.config.ConfigFormVO;
import com.toskey.cube.service.config.business.vo.config.ConfigQueryResultVO;
import com.toskey.cube.service.config.business.vo.config.ConfigQueryVO;
import org.springframework.web.bind.annotation.*;

/**
 * SysConfigController
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/24 13:19
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    private final SysConfigService configService;

    public SysConfigController(SysConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/page")
    public RestResult<PageData<ConfigQueryResultVO>> page(ConfigQueryVO query) {
        return RestResult.success(configService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<ConfigQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(configService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@RequestBody ConfigFormVO form) {
        return RestResult.success(configService.saveConfig(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@RequestBody ConfigFormVO form) {
        return RestResult.success(configService.updateConfig(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(configService.removeConfig(ids));
    }
}

package com.toskey.cube.service.config.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.config.business.service.SysConfigGroupService;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupFormVO;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupQueryResultVO;
import com.toskey.cube.service.config.business.vo.group.ConfigGroupQueryVO;
import org.springframework.web.bind.annotation.*;

/**
 * SysConfigGroupController
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/24 13:19
 */
@RestController
@RequestMapping("/sys/config/group")
public class SysConfigGroupController {

    private final SysConfigGroupService configGroupService;

    public SysConfigGroupController(SysConfigGroupService configGroupService) {
        this.configGroupService = configGroupService;
    }

    @GetMapping("/page")
    public RestResult<PageData<ConfigGroupQueryResultVO>> page(ConfigGroupQueryVO query) {
        return RestResult.success(configGroupService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<ConfigGroupQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(configGroupService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@RequestBody ConfigGroupFormVO form) {
        return RestResult.success(configGroupService.saveConfigGroup(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@RequestBody ConfigGroupFormVO form) {
        return RestResult.success(configGroupService.updateConfigGroup(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(configGroupService.removeConfigGroup(ids));
    }
}

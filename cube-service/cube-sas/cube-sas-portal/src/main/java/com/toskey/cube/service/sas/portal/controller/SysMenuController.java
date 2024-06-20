package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysMenuService;
import com.toskey.cube.service.sas.business.vo.menu.MenuFormVO;
import com.toskey.cube.service.sas.business.vo.menu.MenuQueryResultVO;
import com.toskey.cube.service.sas.business.vo.menu.MenuQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysMenuController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    private final SysMenuService menuService;

    public SysMenuController(final SysMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/tree")
    public RestResult<List<MenuQueryResultVO>> tree(MenuQueryVO query) {
        return RestResult.success(menuService.findTreeList(query));
    }

    @GetMapping("/{id}")
    public RestResult<MenuQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(menuService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody MenuFormVO form) {
        return RestResult.success(menuService.saveMenu(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody MenuFormVO form) {
        return RestResult.success(menuService.updateMenu(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam String[] ids) {
        return RestResult.success(menuService.removeMenu(ids));
    }
}

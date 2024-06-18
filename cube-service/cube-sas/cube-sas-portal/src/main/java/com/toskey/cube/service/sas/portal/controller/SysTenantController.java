package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysTenantService;
import com.toskey.cube.service.sas.business.vo.tenant.TenantFormVO;
import com.toskey.cube.service.sas.business.vo.tenant.TenantQueryResultVO;
import com.toskey.cube.service.sas.business.vo.tenant.TenantQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * SysTenantController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantController {


    private final SysTenantService tenantService;

    public SysTenantController(final SysTenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/page")
    public RestResult<PageData<TenantQueryResultVO>> page(TenantQueryVO query) {
        return RestResult.success(tenantService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<TenantQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(tenantService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody TenantFormVO form) {
        return RestResult.success(tenantService.saveTenant(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody TenantFormVO form) {
        return RestResult.success(tenantService.updateTenant(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam("ids") String[] ids) {
        return RestResult.success(tenantService.removeTenant(ids));
    }

}

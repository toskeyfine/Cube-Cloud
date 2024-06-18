package com.toskey.cube.service.sas.portal.controller;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sas.business.service.SysDeptService;
import com.toskey.cube.service.sas.business.vo.dept.DeptFormVO;
import com.toskey.cube.service.sas.business.vo.dept.DeptQueryResultVO;
import com.toskey.cube.service.sas.business.vo.dept.DeptQueryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysDeptController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    private final SysDeptService deptService;

    public SysDeptController(final SysDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("/tree")
    public RestResult<List<DeptQueryResultVO>> tree(DeptQueryVO query) {
        return RestResult.success(deptService.findTreeList(query));
    }

    @GetMapping("/{id}")
    public RestResult<DeptQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(deptService.findById(id));
    }

    @PostMapping
    public RestResult<Boolean> save(@Validated @RequestBody DeptFormVO form) {
        return RestResult.success(deptService.saveDept(form));
    }

    @PutMapping
    public RestResult<Boolean> update(@Validated @RequestBody DeptFormVO form) {
        return RestResult.success(deptService.updateDept(form));
    }

    @DeleteMapping
    public RestResult<Boolean> remove(@RequestParam String[] ids) {
        return RestResult.success(deptService.removeDept(ids));
    }


}

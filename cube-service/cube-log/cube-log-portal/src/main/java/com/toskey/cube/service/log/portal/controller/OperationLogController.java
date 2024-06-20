package com.toskey.cube.service.log.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.log.business.service.OperationLogService;
import com.toskey.cube.service.log.business.vo.OperationLogQueryResultVO;
import com.toskey.cube.service.log.business.vo.OperationLogQueryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OperationLogController
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/log/operation")
public class OperationLogController {

    private final OperationLogService operationLogService;

    public OperationLogController(final OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @GetMapping("/page")
    public RestResult<PageData<OperationLogQueryResultVO>> page(OperationLogQueryVO query) {
        return RestResult.success(operationLogService.findPage(query));
    }

    @GetMapping("/{id}")
    public RestResult<OperationLogQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(operationLogService.findById(id));
    }

}

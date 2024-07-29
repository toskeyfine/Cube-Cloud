package com.toskey.cube.service.sequence.portal.controller;

import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.service.sequence.business.service.SequenceService;
import com.toskey.cube.service.sequence.business.vo.SequenceFormVO;
import com.toskey.cube.service.sequence.business.vo.SequenceQueryResultVO;
import com.toskey.cube.service.sequence.business.vo.SequenceQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 发号器API接口
 *
 * @author toskey
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sequence")
@RequiredArgsConstructor
public class SequenceController {

    private final SequenceService sequenceService;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @GetMapping("/page")
    public RestResult<PageData<SequenceQueryResultVO>> page(SequenceQueryVO vo) {
        return RestResult.success(sequenceService.page(vo));
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/{id}/info")
    public RestResult<SequenceQueryResultVO> info(@PathVariable("id") String id) {
        return RestResult.success(sequenceService.findById(id));
    }

    /**
     * 生成ID
     * @param code
     * @return
     */
    @GetMapping("/{code}/gen")
    public RestResult<String> nextId(@PathVariable("code") String code) {
        return RestResult.success(sequenceService.nextId(code));
    }

    /**
     * 重置序列
     *
     * @param code
     * @return
     */
    @PutMapping("/reset/{code}")
    public RestResult<Boolean> reset(@PathVariable("code") String code) {
        return RestResult.success(sequenceService.reset(code));
    }

    /**
     * 新增序列
     * @param form
     * @return
     */
    @PostMapping
    public RestResult<Boolean> save(@RequestBody SequenceFormVO form) {
        return RestResult.success(sequenceService.saveSequence(form));
    }

    /**
     * 删除序列
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public RestResult<Boolean> delete(@PathVariable("id") String id) {
        return RestResult.success(sequenceService.removeById(id));
    }


}

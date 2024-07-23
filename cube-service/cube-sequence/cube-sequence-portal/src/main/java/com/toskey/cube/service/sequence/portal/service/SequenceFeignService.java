package com.toskey.cube.service.sequence.portal.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.service.sequence.business.service.SequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SequenceFeignService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/23 15:24
 */
@RestController
@RequestMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/sequence")
@RequiredArgsConstructor
public class SequenceFeignService {

    private final SequenceService sequenceService;

    @GetMapping("/{code}/next")
    public RestResult<String> nextId(@PathVariable("code") String code) {
        return RestResult.success(sequenceService.nextId(code));
    }

}

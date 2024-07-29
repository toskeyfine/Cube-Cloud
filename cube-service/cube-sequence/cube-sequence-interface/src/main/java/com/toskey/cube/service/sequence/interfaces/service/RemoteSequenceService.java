package com.toskey.cube.service.sequence.interfaces.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RemoteSequenceService
 *
 * @author toskey
 * @version 1.0.0
 */
@FeignClient(
        contextId = "remoteSequenceService",
        path = CommonConstants.FEIGN_SERVICE_PATH_PREFIX,
        value = ServiceNameConstants.SERVICE_SEQUENCE
)
public interface RemoteSequenceService {

    @GetMapping("/sequence/{code}/next")
    RestResult<String> nextId(@PathVariable("code") String code);

}

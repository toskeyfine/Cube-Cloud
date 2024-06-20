package com.toskey.cube.service.config.interfaces.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.ServiceNameConstants;
import com.toskey.cube.service.config.interfaces.dto.ConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RemoteConfigService
 *
 * @author toskey
 * @version 1.0.0
 */
@FeignClient(
        contextId = "remoteConfigService",
        name = ServiceNameConstants.SERVICE_CONFIG,
        path = CommonConstants.FEIGN_SERVICE_PATH_PREFIX
)
public interface RemoteConfigService {

    @GetMapping("/sys/config/{code}")
    RestResult<ConfigDTO> get(@PathVariable("code") String code);
}

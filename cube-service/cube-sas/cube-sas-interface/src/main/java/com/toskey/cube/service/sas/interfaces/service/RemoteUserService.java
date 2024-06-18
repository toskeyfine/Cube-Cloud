package com.toskey.cube.service.sas.interfaces.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.ServiceNameConstants;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import com.toskey.cube.service.sas.interfaces.service.fallback.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * RemoteUserService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:04
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SERVICE_SAS, fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    @GetMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/user/account/{username}")
    RestResult<UserDTO> loadUserByUsername(@PathVariable("username") String username);

    @GetMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/user/mobile/{mobile}")
    RestResult<UserDTO> loadUserByMobile(@PathVariable("mobile") String mobile);

    @PostMapping(CommonConstants.FEIGN_SERVICE_PATH_PREFIX + "/user/{username}/locked")
    RestResult<Boolean> lock(@PathVariable("username") String username);

}

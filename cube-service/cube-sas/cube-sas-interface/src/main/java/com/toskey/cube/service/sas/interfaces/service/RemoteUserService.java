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
 * @author toskey
 * @version 1.0.0
 */
@FeignClient(
        contextId = "remoteUserService",
        value = ServiceNameConstants.SERVICE_SAS,
        path = CommonConstants.FEIGN_SERVICE_PATH_PREFIX,
        fallbackFactory = RemoteUserServiceFallbackFactory.class
)
public interface RemoteUserService {

    @GetMapping("/user/account/{username}")
    RestResult<UserDTO> loadUserByUsername(@PathVariable("username") String username);

    @GetMapping("/user/mobile/{mobile}")
    RestResult<UserDTO> loadUserByMobile(@PathVariable("mobile") String mobile);

    @PostMapping("/user/{username}/locked")
    RestResult<Boolean> lock(@PathVariable("username") String username);

}

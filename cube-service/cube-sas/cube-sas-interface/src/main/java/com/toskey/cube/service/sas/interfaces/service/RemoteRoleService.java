package com.toskey.cube.service.sas.interfaces.service;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.ServiceNameConstants;
import com.toskey.cube.service.sas.interfaces.dto.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * RemoteRoleService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/11 14:23
 */
@FeignClient(
        contextId = "remoteRoleService",
        value = ServiceNameConstants.SERVICE_SAS,
        path = CommonConstants.FEIGN_SERVICE_PATH_PREFIX
)
public interface RemoteRoleService {

    @GetMapping("/sys/role/{userId}/list")
    RestResult<List<RoleDTO>> listByUserId(@PathVariable("userId") String userId);

}

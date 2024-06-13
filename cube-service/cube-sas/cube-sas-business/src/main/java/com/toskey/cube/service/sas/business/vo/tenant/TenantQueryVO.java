package com.toskey.cube.service.sas.business.vo.tenant;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysTenant;

import java.time.LocalDateTime;

/**
 * TenantQueryVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 13:28
 */
@EntityMapper(entity = SysTenant.class)
public class TenantQueryVO extends BaseEntityMapper {

    private String name;

    private String code;

    private String type;

    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

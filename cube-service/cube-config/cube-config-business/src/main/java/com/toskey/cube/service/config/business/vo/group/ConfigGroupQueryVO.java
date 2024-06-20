package com.toskey.cube.service.config.business.vo.group;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.config.business.domain.entity.SysConfigGroup;

/**
 * ClientGroupQueryVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = SysConfigGroup.class)
public class ConfigGroupQueryVO extends BaseEntityMapper {

    private String name;

    private String code;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.toskey.cube.service.sas.business.vo.dept;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysDept;

/**
 * DeptQueryVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = SysDept.class)
public class DeptQueryVO extends BaseEntityMapper {

    private String id;

    private String parentId;

    private String name;

    private String code;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

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

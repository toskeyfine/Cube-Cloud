package com.toskey.cube.service.sas.business.vo.post;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysPost;

/**
 * PostQueryVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 13:23
 */
@EntityMapper(entity = SysPost.class)
public class PostQueryVO extends BaseEntityMapper {

    private String id;

    private String name;

    private String code;

    private String status;

    private String deptId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}

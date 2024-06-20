package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.tenant.entity.TenantEntity;

/**
 * SysPost
 *
 * @author toskey
 * @version 1.0.0
 */
@TableName("sys_post")
public class SysPost extends TenantEntity {

    private String name;

    private String code;

    private Integer ordered;

    private String status;

    private String deptId;

    @TableField(exist = false)
    private SysDept dept;

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

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
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

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }
}
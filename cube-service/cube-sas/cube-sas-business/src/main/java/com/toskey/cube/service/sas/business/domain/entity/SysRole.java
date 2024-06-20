package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.tenant.entity.TenantEntity;
import com.toskey.cube.common.datascope.enums.DataScopeType;

import java.util.List;

/**
 * SysRole
 *
 * @author toskey
 * @version 1.0.0
 */
@TableName("sys_role")
public class SysRole extends TenantEntity {

    private String name;

    private String code;

    private Integer userLimit;

    private Integer ordered;

    private String status;

    private DataScopeType dataScopeType;

    @TableField(exist = false)
    private List<SysMenu> menus;

    public SysRole() {

    }

    public SysRole(String id) {
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

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
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

    public DataScopeType getDataScopeType() {
        return dataScopeType;
    }

    public void setDataScopeType(DataScopeType dataScopeType) {
        this.dataScopeType = dataScopeType;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }
}

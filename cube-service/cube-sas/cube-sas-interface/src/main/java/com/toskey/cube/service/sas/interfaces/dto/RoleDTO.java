package com.toskey.cube.service.sas.interfaces.dto;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;

import java.time.LocalDateTime;

/**
 * RoleDTO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/11 14:25
 */
@EntityMapper
public class RoleDTO extends BaseEntityMapper {

    private String id;

    private String name;

    private String code;

    private Integer userLimit;

    private Integer ordered;

    private String status;

    private String dataScopeType;

    private String[] dataScopeDeptIds;

    private String remark;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

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

    public String getDataScopeType() {
        return dataScopeType;
    }

    public void setDataScopeType(String dataScopeType) {
        this.dataScopeType = dataScopeType;
    }

    public String[] getDataScopeDeptIds() {
        return dataScopeDeptIds;
    }

    public void setDataScopeDeptIds(String[] dataScopeDeptIds) {
        this.dataScopeDeptIds = dataScopeDeptIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

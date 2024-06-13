package com.toskey.cube.service.sas.business.vo.tenant;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysTenant;

import java.time.LocalDateTime;

/**
 * TenantQueryResultVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 13:28
 */
@EntityMapper(entity = SysTenant.class)
public class TenantQueryResultVO extends BaseEntityMapper {

    private String id;

    private String name;

    private String code;

    private LocalDateTime registerTime;

    private String type;

    private Integer ordered;

    private String status;

    private LocalDateTime authorizationBeginTime;

    private LocalDateTime authorizationEndTime;

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

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDateTime getAuthorizationBeginTime() {
        return authorizationBeginTime;
    }

    public void setAuthorizationBeginTime(LocalDateTime authorizationBeginTime) {
        this.authorizationBeginTime = authorizationBeginTime;
    }

    public LocalDateTime getAuthorizationEndTime() {
        return authorizationEndTime;
    }

    public void setAuthorizationEndTime(LocalDateTime authorizationEndTime) {
        this.authorizationEndTime = authorizationEndTime;
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

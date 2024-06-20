package com.toskey.cube.service.sas.business.vo.tenant;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysTenant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * TenantFormVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = SysTenant.class)
public class TenantFormVO extends BaseEntityMapper {

    private String id;

    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    @Size(min = 1, max = 20)
    private String code;

    private LocalDateTime registerTime;

    private String type;

    private Integer ordered;

    @NotNull
    private String status;

    @NotNull
    private LocalDateTime authorizationBeginTime;

    private LocalDateTime authorizationEndTime;

    private String remark;

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
}

package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;

import java.time.LocalDateTime;

/**
 * SysTenant
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:27
 */
@TableName("sys_tenant")
public class SysTenant extends DataEntity {

    private String name;

    private String code;

    private LocalDateTime registerTime;

    private String type;

    private Integer ordered;

    private String status;

    private LocalDateTime authorizationBeginTime;

    private LocalDateTime authorizationEndTime;

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
}

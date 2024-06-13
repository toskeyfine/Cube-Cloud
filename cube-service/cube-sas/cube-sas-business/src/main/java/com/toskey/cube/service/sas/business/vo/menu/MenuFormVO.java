package com.toskey.cube.service.sas.business.vo.menu;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysMenu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;


/**
 * MenuFormVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 13:23
 */
@EntityMapper(entity = SysMenu.class)
public class MenuFormVO extends BaseEntityMapper {

    private String id;

    @NotBlank
    private String parentId;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    private String path;

    private String component;

    private String routeType;

    private String target;

    private String cached;

    @NotNull
    private String type;

    private String visible;

    private String perms;

    @NotNull
    private String status;

    private Integer ordered;

    private String remark;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * SysMenu
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:25
 */
@TableName("sys_menu")
public class SysMenu extends DataEntity {

    private String parentId;

    private String name;

    private String path;

    private String component;

    private String routeType;

    private String target;

    private String cached;

    private String type;

    private String visible;

    private String perms;

    private String status;

    private Integer ordered;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    public SysMenu() {

    }

    public SysMenu(String id) {
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

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}

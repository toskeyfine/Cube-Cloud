package com.toskey.cube.service.config.interfaces.dto;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;

/**
 * ConfigDTO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 16:25
 */
@EntityMapper
public class ConfigDTO extends BaseEntityMapper {

    private String id;

    private String groupId;

    private String name;

    private String code;

    private String value;

    private String status;

    private Integer ordered;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}

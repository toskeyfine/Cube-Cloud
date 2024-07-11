package com.toskey.cube.service.config.business.vo.config;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.config.business.domain.entity.SysConfig;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ClientFormVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = SysConfig.class)
public class ConfigFormVO extends BaseEntityMapper {

    private String id;

    private String groupId;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Size(min = 2, max = 20)
    private String code;

    @Size(max = 64)
    private String value;

    private String status;

    @Min(0)
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

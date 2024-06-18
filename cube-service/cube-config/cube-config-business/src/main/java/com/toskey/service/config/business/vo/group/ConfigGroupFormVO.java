package com.toskey.service.config.business.vo.group;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.service.config.business.domain.entity.SysConfigGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ClientGroupFormVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 16:17
 */
@EntityMapper(entity = SysConfigGroup.class)
public class ConfigGroupFormVO extends BaseEntityMapper {

    private String id;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotBlank
    @Size(min = 1, max = 20)
    private String code;

    private String status;

    private Integer ordered;

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

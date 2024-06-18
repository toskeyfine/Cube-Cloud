package com.toskey.service.config.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;

/**
 * SysConfigGroup
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 16:13
 */
@TableName("sys_config_group")
public class SysConfigGroup extends DataEntity {

    private String name;
    
    private String code;

    private String status;
    
    private Integer ordered;

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

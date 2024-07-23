package com.toskey.cube.common.tenant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.toskey.cube.common.core.base.BaseEntity;
import com.toskey.cube.common.data.entity.DataEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TenantEntity
 *
 * @author toskey
 * @version 1.0.0
 */
public class TenantEntity extends DataEntity {

    @TableField(fill = FieldFill.INSERT)
    protected String tenantId;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}

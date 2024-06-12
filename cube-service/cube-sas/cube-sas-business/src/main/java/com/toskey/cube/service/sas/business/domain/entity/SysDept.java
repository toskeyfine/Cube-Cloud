package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.common.tenant.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysDept
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends TenantEntity {

    private String parentId;

    private String name;

    private String code;

    private String manager;

    private String phone;

    private Integer ordered;

    private String status;

}

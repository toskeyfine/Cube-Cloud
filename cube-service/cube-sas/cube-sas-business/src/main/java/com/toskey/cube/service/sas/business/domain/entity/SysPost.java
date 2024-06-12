package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.common.tenant.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysPost
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_post")
public class SysPost extends TenantEntity {

    private String name;

    private String code;

    private Integer ordered;

    private String status;

    private String deptId;

    @TableField(exist = false)
    private SysDept dept;

}

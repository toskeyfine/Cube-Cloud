package com.toskey.cube.service.sas.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.common.tenant.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SysUser
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends TenantEntity {

    private String username;

    private String password;

    private String passwordStrength;

    private String name;

    private Integer age;

    private String gender;

    private String avatar;

    private String mobile;

    private String email;

    private String userType;

    private String status;

    private String lastLoginIp;

    private LocalDateTime lastLoginTime;

    private String deptId;

    private String postId;

    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private SysPost post;

    @TableField(exist = false)
    private List<SysRole> roles;

}

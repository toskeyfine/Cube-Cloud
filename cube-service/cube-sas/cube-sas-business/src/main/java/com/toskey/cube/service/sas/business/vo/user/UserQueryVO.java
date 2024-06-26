package com.toskey.cube.service.sas.business.vo.user;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

/**
 * UserQueryVO
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@EntityMapper(entity = SysUser.class)
public class UserQueryVO extends BaseEntityMapper {

    private String username;

    private String name;

    private String mobile;

    private String email;

    private String userType;

    private String status;

    private String deptId;

    private String postId;

}

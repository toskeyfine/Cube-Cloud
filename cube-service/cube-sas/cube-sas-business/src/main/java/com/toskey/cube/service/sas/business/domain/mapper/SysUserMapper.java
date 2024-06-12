package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysUserMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:48
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    int deleteUserRoleRel(@Param("userId") String userId);

    int insertUserRoleRel(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);
}

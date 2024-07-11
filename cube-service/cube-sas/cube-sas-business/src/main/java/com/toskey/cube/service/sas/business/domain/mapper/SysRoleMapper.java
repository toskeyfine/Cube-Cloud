package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysRoleMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    int deleteRoleMenuRel(@Param("roleId") String roleId);

    int insertRoleMenuRel(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds);

    List<SysRole> selectListByUserId(@Param("userId") String userId);

    List<String> selectBindDeptIds(@Param("roleId") String roleId);

}

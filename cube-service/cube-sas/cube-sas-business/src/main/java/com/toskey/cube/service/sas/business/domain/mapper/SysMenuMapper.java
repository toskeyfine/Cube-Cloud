package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysMenuMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:50
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    int deleteMenuRoleRel(@Param("menuId") String menuId);
}

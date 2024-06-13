package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysDeptMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:49
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    int deptBindCount(@Param("id") String id);

}

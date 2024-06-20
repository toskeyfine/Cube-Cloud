package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysPostMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    int postBindCount(@Param("id") String id);

}

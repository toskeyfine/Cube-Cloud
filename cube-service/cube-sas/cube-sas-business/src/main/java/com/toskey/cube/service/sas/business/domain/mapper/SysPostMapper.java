package com.toskey.cube.service.sas.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.sas.business.domain.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysPostMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:49
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    int postBindCount(@Param("id") String id);

}

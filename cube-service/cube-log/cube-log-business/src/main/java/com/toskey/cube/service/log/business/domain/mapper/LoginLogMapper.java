package com.toskey.cube.service.log.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.log.business.domain.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * LoginLogMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}

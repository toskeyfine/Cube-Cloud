package com.toskey.cube.service.log.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.log.business.domain.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * LoginLogMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:23
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}

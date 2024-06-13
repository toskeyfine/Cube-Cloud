package com.toskey.cube.service.log.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.log.business.domain.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * OperationLogMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:24
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}

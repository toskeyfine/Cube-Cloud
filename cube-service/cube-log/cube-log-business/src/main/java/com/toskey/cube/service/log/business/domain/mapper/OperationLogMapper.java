package com.toskey.cube.service.log.business.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.toskey.cube.service.log.business.domain.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * OperationLogMapper
 *
 * @author toskey
 * @version 1.0.0
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}

package com.toskey.cube.service.log.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.log.business.domain.entity.OperationLog;
import com.toskey.cube.service.log.business.domain.mapper.OperationLogMapper;
import com.toskey.cube.service.log.business.vo.OperationLogQueryResultVO;
import com.toskey.cube.service.log.business.vo.OperationLogQueryVO;
import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OperationLogService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:25
 */
@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLog> implements IService<OperationLog> {

    public PageData<OperationLogQueryResultVO> findPage(OperationLogQueryVO query) {
        IPage<OperationLog> page = PageUtils.buildPage();
        List<OperationLog> logList = baseMapper.selectList(
                page,
                Wrappers.<OperationLog>lambdaQuery()
                        .like(StringUtils.isNotBlank(query.getTitle()), OperationLog::getTitle, query.getTitle())
                        .like(StringUtils.isNotBlank(query.getRequestRemoteHost()), OperationLog::getRequestRemoteHost, query.getRequestRemoteHost())
                        .eq(StringUtils.isNotBlank(query.getModule()), OperationLog::getModule, query.getModule())
                        .eq(StringUtils.isNotBlank(query.getLogType()), OperationLog::getLogType, query.getLogType())
                        .eq(StringUtils.isNotBlank(query.getServiceId()), OperationLog::getServiceId, query.getServiceId())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(logList, OperationLogQueryResultVO.class));
    }

    public OperationLogQueryResultVO findById(String id) {
        OperationLog operationLog = getById(id);
        return operationLog.toMapper(OperationLogQueryResultVO.class);
    }

    public boolean saveLog(OperationLogDTO operationLogDTO) {
        // DTO转Entity时，因依赖原因无法指定entity类型，所以需要显式传递
        OperationLog operationLog = operationLogDTO.toEntity(OperationLog.class);
        return save(operationLog);
    }

}

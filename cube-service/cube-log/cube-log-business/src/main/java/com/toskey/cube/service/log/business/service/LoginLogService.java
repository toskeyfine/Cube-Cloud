package com.toskey.cube.service.log.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.toskey.cube.common.core.base.PageData;
import com.toskey.cube.common.core.util.EntityUtils;
import com.toskey.cube.common.core.util.PageUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.service.log.business.domain.entity.LoginLog;
import com.toskey.cube.service.log.business.domain.mapper.LoginLogMapper;
import com.toskey.cube.service.log.business.vo.LoginLogQueryResultVO;
import com.toskey.cube.service.log.business.vo.LoginLogQueryVO;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LoginLogService
 *
 * @author toskey
 * @version 1.0.0
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> implements IService<LoginLog> {

    public PageData<LoginLogQueryResultVO> findPage(LoginLogQueryVO query) {
        IPage<LoginLog> page = PageUtils.buildPage();
        List<LoginLog> logList = baseMapper.selectList(
                page,
                Wrappers.<LoginLog>lambdaQuery()
                        .eq(StringUtils.isNotBlank(query.getType()), LoginLog::getType, query.getType())
                        .like(StringUtils.isNotBlank(query.getClientIp()), LoginLog::getClientIp, query.getClientIp())
                        .eq(StringUtils.isNotBlank(query.getResult()), LoginLog::getResult, query.getResult())
        );
        return PageUtils.buildPageData(page, EntityUtils.toMapper(logList, LoginLogQueryResultVO.class));
    }

    public LoginLogQueryResultVO findById(String id) {
        LoginLog loginLog = getById(id);
        return loginLog.toMapper(LoginLogQueryResultVO.class);
    }

    public boolean saveLog(LoginLogDTO loginLogDTO) {
        // DTO转Entity时，因依赖原因无法指定entity类型，所以需要显式传递
        LoginLog loginLog = loginLogDTO.toEntity(LoginLog.class);
        return save(loginLog);
    }
}

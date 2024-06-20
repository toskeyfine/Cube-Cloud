package com.toskey.cube.service.log.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.log.business.domain.entity.OperationLog;

/**
 * OperationLogQueryVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = OperationLog.class)
public class OperationLogQueryVO extends BaseEntityMapper {

    private String title;                   // 日志标题

    private String module;                  // 所属模块

    private String logType;                 // 日志类型

    private String requestRemoteHost;       // 请求端地址

    private String serviceId;               // 所属服务ID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getRequestRemoteHost() {
        return requestRemoteHost;
    }

    public void setRequestRemoteHost(String requestRemoteHost) {
        this.requestRemoteHost = requestRemoteHost;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}

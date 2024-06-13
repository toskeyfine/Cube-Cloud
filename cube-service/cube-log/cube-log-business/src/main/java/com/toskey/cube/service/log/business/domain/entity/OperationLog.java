package com.toskey.cube.service.log.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.common.tenant.entity.TenantEntity;
import com.toskey.cube.common.core.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * OperationLog
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:19
 */
@TableName("log_operation")
public class OperationLog extends TenantEntity {

    private String title;                   // 日志标题

    private String content;                 // 日志内容

    private String module;                  // 所属模块

    private String logType;                 // 日志类型

    private String requestRemoteHost;       // 请求端地址

    private String requestUserAgent;        // 请求端标识

    private String requestUri;              // 请求URI

    private String requestMethod;           // 请求方式

    private String requestParams;           // 请求参数

    private Long executeTime;               // 执行时间

    private String exMsg;                   // 错误信息

    private String serviceId;               // 所属服务ID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    public void setRequestUserAgent(String requestUserAgent) {
        this.requestUserAgent = requestUserAgent;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}

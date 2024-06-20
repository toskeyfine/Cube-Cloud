package com.toskey.cube.service.log.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.log.business.domain.entity.LoginLog;

import java.time.LocalDateTime;

/**
 * LoginLogQueryResultVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:26
 */
@EntityMapper(entity = LoginLog.class)
public class LoginLogQueryResultVO extends BaseEntityMapper {

    private String id;

    /**
     * 1。登录
     * 2。注销
     */
    private String type;

    /**
     * 登录请求类型
     */
    private String grantType;

    /**
     * 请求客户端IP
     */
    private String clientIp;

    /**
     * 结果
     * 0.失败
     * 1.成功
     */
    private String result;

    private String reason;

    private String createBy;

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

package com.toskey.cube.service.log.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.tenant.entity.TenantEntity;

/**
 * LoginLog
 *
 * @author toskey
 * @version 1.0.022
 */
@TableName("log_login")
public class LoginLog extends TenantEntity {

    /**
     * 1。登录
     * 2。注销
     */
    private String type;

    /**
     * 登录授权类型
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
}

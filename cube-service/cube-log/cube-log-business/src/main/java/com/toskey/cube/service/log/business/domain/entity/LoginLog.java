package com.toskey.cube.service.log.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.common.tenant.entity.TenantEntity;

/**
 * LoginLog
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:22
 */
@TableName("log_login")
public class LoginLog extends TenantEntity {

    /**
     * 1。登录
     * 2。注销
     */
    private String type;

    /**
     * 登录类：
     * 10001.授权码模式
     * 10002.用户密码模式
     * 10003.手机号验证码登录（预留）
     * 10004.邮箱验证码登录（预留）
     * 10005.客户端凭证模式（预留）
     * 10006.第三方登录（预留）
     * 注销类：
     * 20001.主动
     * 20002.强制
     * 20003.超时
     */
    private String loginType;

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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
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

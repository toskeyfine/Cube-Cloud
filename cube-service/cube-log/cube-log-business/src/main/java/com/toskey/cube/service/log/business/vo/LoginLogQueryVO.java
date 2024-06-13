package com.toskey.cube.service.log.business.vo;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.log.business.domain.entity.LoginLog;

/**
 * LoginLogQueryVO
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:24
 */
@EntityMapper(entity = LoginLog.class)
public class LoginLogQueryVO extends BaseEntityMapper {

    /**
     * 1。登录
     * 2。注销
     */
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}

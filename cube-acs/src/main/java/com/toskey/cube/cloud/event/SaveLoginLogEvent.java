package com.toskey.cube.cloud.event;

import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;

/**
 * 登录日志存储事件定义
 *
 * @author toskey
 * @version 1.0.0
 */
public class SaveLoginLogEvent {

    private final LoginLogDTO logDTO;

    public SaveLoginLogEvent(LoginLogDTO logDTO) {
        this.logDTO = logDTO;
    }

    public LoginLogDTO getLogDTO() {
        return logDTO;
    }
}

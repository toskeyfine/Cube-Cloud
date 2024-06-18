package com.toskey.cube.cloud.event;

import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;

/**
 * 登录日志存储事件定义
 *
 * @author lis
 * @date 2023/5/9 13:39
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

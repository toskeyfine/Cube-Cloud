package com.toskey.cube.cloud.event;

import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.service.log.interfaces.service.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 登录日志存储监听器
 *
 * @author lis
 * @date 2023/5/9 13:40
 */
@Component
public class SaveLogEventListener {

    private final RemoteLogService remoteLogService;

    public SaveLogEventListener(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }

    @Async
    @Order
    @EventListener(SaveLoginLogEvent.class)
    public void saveLog(SaveLoginLogEvent event) {
        remoteLogService.saveLoginLog(event.getLogDTO());
    }

}

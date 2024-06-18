package com.toskey.cube.common.log.event;

import com.toskey.cube.service.log.interfaces.service.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志存储事件监听器
 *
 * @author lis
 * @date 2023/5/9 13:40
 */
@RequiredArgsConstructor
public class SaveOperationLogEventListener {

    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(SaveOperationLogEvent.class)
    public void saveOperationLog(SaveOperationLogEvent event) {
        remoteLogService.saveOperationLog(event.getLogDTO());
    }
}

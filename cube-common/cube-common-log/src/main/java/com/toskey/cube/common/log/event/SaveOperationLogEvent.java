package com.toskey.cube.common.log.event;

import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志存储事件
 *
 * @author toskey
 * @version 1.0.0
 */
@AllArgsConstructor
public class SaveOperationLogEvent {

    @Getter
    private final OperationLogDTO logDTO;
}

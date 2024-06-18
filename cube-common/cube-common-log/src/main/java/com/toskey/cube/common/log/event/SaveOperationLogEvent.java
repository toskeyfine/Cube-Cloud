package com.toskey.cube.common.log.event;

import com.toskey.cube.service.log.interfaces.dto.OperationLogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志存储事件
 *
 * @author lis
 * @date 2023/5/9 13:39
 */
@AllArgsConstructor
public class SaveOperationLogEvent {

    @Getter
    private final OperationLogDTO logDTO;
}

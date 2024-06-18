package com.toskey.cube.common.excel.listener;

/**
 * 读取错误事件
 *
 * @author toskey
 * @version 1.0.0
 */
@FunctionalInterface
public interface ReadError {

    void doError(ExcelError error);

    static ReadError withDefault() {
        return t -> {};
    }
}

package com.toskey.cube.common.excel.listener;

import java.util.List;

/**
 * 读取完成后的事件
 *
 * @author toskey
 * @version 1.0.0
 */
@FunctionalInterface
public interface ReadAfter<T> {

    void doAfter(Long totalRow, List<T> dataList);

    static <T> ReadAfter<T> withDefault() {
        return (r, t) -> {};
    }

}

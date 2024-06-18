package com.toskey.cube.common.excel.util;

/**
 * 定制器接口
 *
 * @author toskey
 * @version 1.0.0
 */
@FunctionalInterface
public interface Customizer<T> {

    void customize(T t);

    static <T> Customizer<T> withDefaults() {
        return (t) -> {
        };
    }
}

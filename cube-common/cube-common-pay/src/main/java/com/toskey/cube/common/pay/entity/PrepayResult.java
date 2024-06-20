package com.toskey.cube.common.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 预支付结果
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class PrepayResult<T> {

    private int code;

    private T data;

    public static <T> PrepayResult<T> success(T data) {
        return PrepayResult.of(0, data);
    }

    public static <T> PrepayResult<T> error() {
        return PrepayResult.of(-1, null);
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

}

package com.toskey.cube.common.core.base;

import com.toskey.cube.common.core.constant.enums.RestResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * restful api response model
 *
 * @version 1.0.0
 * @author toskey
 * @since 2024/6/5
 */
@Getter
@AllArgsConstructor(staticName = "of")
public final class RestResult<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    private RestResult() {}

    public boolean isSuccess() {
        return this.code == RestResultCode.SUCCESS.getCode();
    }

    public static <T> RestResult<T> success() {
        return RestResult.of(RestResultCode.SUCCESS);
    }

    public static <T> RestResult<T> success(String msg, T data) {
        return RestResult.of(RestResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> RestResult<T> success(T data) {
        return RestResult.of(RestResultCode.SUCCESS, data);
    }

    public static <T> RestResult<T> failure() {
        return RestResult.of(RestResultCode.FAILURE);
    }

    public static <T> RestResult<T> failure(String msg, T data) {
        return RestResult.of(RestResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> RestResult<T> failure(T data) {
        return RestResult.of(RestResultCode.SUCCESS, data);
    }

    public static <T> RestResult<T> of(int code, String msg) {
        return RestResult.of(code, msg, null);
    }

    public static <T> RestResult<T> of(RestResultCode restResultCode) {
        return RestResult.of(restResultCode.getCode(), restResultCode.getMsg(), null);
    }

    public static <T> RestResult<T> of(RestResultCode restResultCode, T data) {
        return RestResult.of(restResultCode.getCode(), restResultCode.getMsg(), data);
    }


}

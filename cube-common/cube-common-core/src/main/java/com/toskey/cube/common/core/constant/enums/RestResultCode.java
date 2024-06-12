package com.toskey.cube.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RestResultCode
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/5 17:03
 */
@Getter
@AllArgsConstructor
public enum RestResultCode {

    SUCCESS(200, "success"),
    FAILURE(500, "failure");

    private final int code;

    private final String msg;

}

package com.toskey.cube.common.log.enums;

/**
 * LoginLogType
 *
 * @author toskey
 * @version 1.0.0
 */
public enum LoginLogType {

    LOGIN("1", "登录"),
    LOGOUT("2", "注销");

    private final String value;

    private final String text;

    LoginLogType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }
}

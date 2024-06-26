package com.toskey.cube.common.core.constant.enums;

/**
 * PasswordStrength
 *
 * @author toskey
 * @version 1.0.0
 */
public enum PasswordStrength {

    LOWER("1", "低"),
    MIDDLE("2", "中"),
    HIGH("3", "强");

    private final String value;

    private final String text;

    PasswordStrength(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

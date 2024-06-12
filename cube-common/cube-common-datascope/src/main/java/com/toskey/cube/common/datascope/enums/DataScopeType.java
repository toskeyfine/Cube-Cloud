package com.toskey.cube.common.datascope.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * DataScopeConstants
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:35
 */
@Getter
@AllArgsConstructor
public enum DataScopeType {

    UNKNOWN("-1", "未知"),
    ALL("1", "全部"),
    SELF_DEPT("2", "所在部门"),
    SELECTED_DEPT("3", "选择部门"),
    SELF("4", "当前用户");

    @EnumValue
    private final String value;

    private final String text;

    public static DataScopeType of(String value) {
        return Arrays.stream(DataScopeType.values())
                .filter(dst -> dst.getValue().equals(value))
                .findAny()
                .orElse(UNKNOWN);
    }

}

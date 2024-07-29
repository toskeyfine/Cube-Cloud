package com.toskey.cube.service.sequence.business.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * RuleTypeEnum
 *
 * @author toskeu
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum RuleType {

    UNKNOWN(-1, "未知"),
    INCREMENT_DB(1, "数据库自增"),
    INCREMENT_RDS(2, "Redis自增"),
    SNOWFLAKE(3, "雪花算法"),
    COMBINED(4, "自定义组合");

    @EnumValue
    @JsonValue
    private final int code;

    private final String description;

    public static RuleType of(int code) {
        return Arrays.stream(RuleType.values())
                .filter(r -> r.getCode() == code)
                .findFirst()
                .orElse(UNKNOWN);
    }

}

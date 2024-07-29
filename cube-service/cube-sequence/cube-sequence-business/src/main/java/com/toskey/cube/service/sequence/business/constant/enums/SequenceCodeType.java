package com.toskey.cube.service.sequence.business.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * SequenceCodeType
 *
 * @author tosksey
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SequenceCodeType {

    UNKNOWN(-1, "未知"),
    MACHINE_CODE(1, "机器码"),
    DATETIME(2, "时间"),
    FIXED_CODE(3, "固定序列"),
    MODULE_CODE(4, "模块编码"),
    VERSION(5, "版本号"),
    ID_SECTION(6, "号段");

    @EnumValue
    @JsonValue
    private final int code;

    private final String description;

    public static SequenceCodeType of(int code) {
        return Arrays.stream(SequenceCodeType.values())
                .filter(s -> s.getCode() == code)
                .findFirst()
                .orElse(UNKNOWN);
    }

}

package com.toskey.cube.common.excel.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Excel读取错误对象
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ExcelError {

    private long row;       // 发生错误的行数

    private Set<String> errors = new HashSet<>();   // 错误消息

}

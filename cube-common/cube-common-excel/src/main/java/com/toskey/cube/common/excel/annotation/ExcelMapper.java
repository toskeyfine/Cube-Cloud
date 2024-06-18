package com.toskey.cube.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel读取注解
 *
 * @author lis
 * @date 2023/11/2 14:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMapper {

    /**
     * 标题头所占行数，默认为1行
     */
    int headRowNumber() default 1;

    /**
     * 是否跳过空行，默认为true
     */
    boolean ignoreEmptyRow() default true;

}
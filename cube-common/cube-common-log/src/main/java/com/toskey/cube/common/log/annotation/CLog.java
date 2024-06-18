package com.toskey.cube.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CLog
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/14 9:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CLog {

    /**
     * 标题
     * @return
     */
    String value() default "";

    /**
     * 内容
     * @return
     */
    String content() default "";

    /**
     * 模块
     * @return
     */
    String module() default "";

}

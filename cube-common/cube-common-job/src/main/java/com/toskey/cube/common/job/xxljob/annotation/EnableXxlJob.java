package com.toskey.cube.common.job.xxljob.annotation;

import com.toskey.cube.common.job.xxljob.config.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启xxl-job注解
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableXxlJob {
}

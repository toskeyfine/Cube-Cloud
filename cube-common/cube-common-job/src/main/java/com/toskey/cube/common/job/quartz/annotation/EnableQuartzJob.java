package com.toskey.cube.common.job.quartz.annotation;

import com.toskey.cube.common.job.quartz.config.QuartzJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableQuartz
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({ QuartzJobAutoConfiguration.class })
public @interface EnableQuartzJob {
}

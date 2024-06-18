package com.toskey.cube.common.job.quartz.annotation;


import com.toskey.cube.common.job.quartz.constant.QuartzMisfireStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ZxJob
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CJob {

    String id() default "";

    String group() default "";

    int priority() default -1;

    String cron() default "";

    boolean async() default true;

    QuartzMisfireStrategy misfireStrategy() default QuartzMisfireStrategy.DEFAULT;

}

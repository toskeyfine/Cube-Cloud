package com.toskey.cube.common.job.quartz.constant;

/**
 * QuartzMisfireStrategy
 *
 * @author toskey
 * @version 1.0.0
 */
public enum QuartzMisfireStrategy {

    DEFAULT,
    IGNORE_MISFIRES,
    FIRE_AND_PROCEED,
    DO_NOTHING

}

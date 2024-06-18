package com.toskey.cube.common.job.quartz.component;

import java.util.Date;

/**
 * ZxJobBean
 *
 * @author toskey
 * @version 1.0.0
 */
public interface QuartzJobBean {

    void execute();

    default Date startAt() {
        return null;
    }

    default Date endAt() {
        return null;
    }

}

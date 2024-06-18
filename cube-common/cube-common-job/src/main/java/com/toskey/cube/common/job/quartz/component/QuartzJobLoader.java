package com.toskey.cube.common.job.quartz.component;

import java.util.List;

/**
 * QuartzJobLoader
 *
 * @author toskey
 * @version 1.0.0
 */
public interface QuartzJobLoader {

    List<QuartzJobBean> load();

}

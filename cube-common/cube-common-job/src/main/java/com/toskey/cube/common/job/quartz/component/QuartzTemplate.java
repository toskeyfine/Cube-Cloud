package com.toskey.cube.common.job.quartz.component;

import com.toskey.cube.common.job.quartz.util.QuartzUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * QuartzTemplate
 *
 * @author toskey
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class QuartzTemplate {

    private final Scheduler scheduler;

    public void insertJob(QuartzJobBean quartzJobBean) throws SchedulerException {
        QuartzUtils.createJob(scheduler, quartzJobBean);
    }

    public void pauseJob(String id, String group) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(id, group));
    }

    public void resumeJob(String id, String group) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(id, group));
    }

    public void removeJob(String id, String group) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(id, group));
    }

}

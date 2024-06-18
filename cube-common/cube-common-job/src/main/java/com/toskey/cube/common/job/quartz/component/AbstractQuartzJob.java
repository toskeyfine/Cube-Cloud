package com.toskey.cube.common.job.quartz.component;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * AbstractQuartzJob
 *
 * @author toskey
 * @version 1.0.0
 */
public abstract class AbstractQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            doExecute(jobExecutionContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doExecute(JobExecutionContext context)  {
        Object jobBean = context.getMergedJobDataMap().get("JOB_BEAN");
        if (jobBean instanceof QuartzJobBean quartzJobBean) {
            quartzJobBean.execute();;
        }
    }

}

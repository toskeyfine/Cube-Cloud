package com.toskey.cube.common.job.quartz.util;

import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.job.quartz.annotation.*;
import com.toskey.cube.common.job.quartz.component.AsyncQuartzJobExecutor;
import com.toskey.cube.common.job.quartz.component.QuartzJobBean;
import com.toskey.cube.common.job.quartz.component.QuartzJobExecutor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.quartz.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * QuartzUtils
 *
 * @author toskey
 * @version 1.0.0
 */
@UtilityClass
public class QuartzUtils {

    @SneakyThrows
    public List<QuartzJobBean> getAllLocalJobs() {
        Map<String, QuartzJobBean> beanMap = SpringContextHolder.getBeansOfType(QuartzJobBean.class);
        return beanMap.values().stream()
                .filter(bean -> AnnotationUtils.findAnnotation(bean.getClass(), CJob.class) != null)
                .collect(Collectors.toList());
    }

    public void createJob(Scheduler scheduler, QuartzJobBean quartzJobBean) throws SchedulerException {
        CJob cJob = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), CJob.class);
        String id = cJob.id();
        if (StringUtils.isBlank(id)) {
            JobId jobId = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), JobId.class);
            if (jobId == null) {
                throw new IllegalArgumentException("Job Id is Null.");
            }
            id = jobId.value();
        }
        String group = cJob.group();
        if (StringUtils.isBlank(group)) {
            JobGroup jobGroup = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), JobGroup.class);
            if (jobGroup == null) {
                throw new IllegalArgumentException("Job Group is Null.");
            }
            group = jobGroup.value();
        }
        int priority = cJob.priority();
        if (priority < 0) {
            JobPriority jobPriority = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), JobPriority.class);
            if (jobPriority != null) {
                priority = jobPriority.value();
            }
        }
        String cron = cJob.cron();
        if (StringUtils.isBlank(cron)) {
            JobCron jobCron = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), JobCron.class);
            if (jobCron == null) {
                throw new IllegalArgumentException("Job Group is Null.");
            }
            cron = jobCron.value();
        }
        AsyncJob asyncJob = AnnotationUtils.getAnnotation(quartzJobBean.getClass(), AsyncJob.class);
        boolean async = asyncJob != null ? cJob.async() && asyncJob.value() : cJob.async();

        Class<? extends Job> job = async ? AsyncQuartzJobExecutor.class : QuartzJobExecutor.class;

        JobDetail jobDetail = JobBuilder.newJob(job)
                .withIdentity(id, group)
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // 设置执行错误时的处理策略
        switch (cJob.misfireStrategy()) {
            case IGNORE_MISFIRES -> cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            case FIRE_AND_PROCEED -> cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            case DO_NOTHING -> cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        }

        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(id, group)
                .withSchedule(cronScheduleBuilder);

        if (priority >= 0) {
            triggerBuilder.withPriority(priority);
        }
        // 设置生效时间
        Date startAt = quartzJobBean.startAt();
        if (startAt != null) {
            triggerBuilder.startAt(startAt);
        }
        // 设置失效时间
        Date endAt = quartzJobBean.endAt();
        if (endAt != null) {
            triggerBuilder.endAt(endAt);
        }
        CronTrigger trigger = triggerBuilder.build();

        jobDetail.getJobDataMap().put("JOB_BEAN", quartzJobBean);

        scheduler.scheduleJob(jobDetail, trigger);
    }

}

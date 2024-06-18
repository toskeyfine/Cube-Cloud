package com.toskey.cube.common.job.quartz.config;

import com.toskey.cube.common.job.quartz.component.QuartzJobBean;
import com.toskey.cube.common.job.quartz.component.QuartzTemplate;
import com.toskey.cube.common.job.quartz.util.QuartzUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * ZxQuartzJobAutoConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
@RequiredArgsConstructor
@EnableScheduling
public class QuartzJobAutoConfiguration {

    private final Scheduler scheduler;

    @Bean
    public QuartzTemplate quartzTemplate() {
        return new QuartzTemplate(scheduler);
    }

    @PostConstruct
    public void jobInitialize() throws SchedulerException {
        List<QuartzJobBean> localJobBeans = QuartzUtils.getAllLocalJobs();
        for (QuartzJobBean quartzJobBean : localJobBeans) {
            QuartzUtils.createJob(scheduler, quartzJobBean);
        }
    }

}
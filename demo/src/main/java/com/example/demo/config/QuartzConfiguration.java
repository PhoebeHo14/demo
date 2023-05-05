package com.example.demo.config;

import com.example.demo.schedule.QuartzTask;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.time.LocalDate;

@Configuration
public class QuartzConfiguration {

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger... triggers) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setOverwriteExistingJobs(true);
        bean.setStartupDelay(1);
        bean.setTriggers(triggers);
        return bean;
    }

    @Bean(name = "calculateWorkTimeJobDetail")
    public MethodInvokingJobDetailFactoryBean jobDetail(QuartzTask task) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("calculateWorkTime");
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean jobTrigger(JobDetail calculateWorkTimeJobDetail) {
        String cron = "0/5 * * * * ? ";  //todo execute per 5 seconds
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(calculateWorkTimeJobDetail);
        trigger.setCronExpression(cron);
        return trigger;
    }

}

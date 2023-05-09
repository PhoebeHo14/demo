package com.example.demo.config;

import com.example.demo.schedule.QuartzTask;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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
        String cron = "0/20 0/1 * * * ? ";
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(calculateWorkTimeJobDetail);
        trigger.setCronExpression(cron);
        return trigger;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("QuartzTask-");
        executor.initialize();
        return executor;
    }
}

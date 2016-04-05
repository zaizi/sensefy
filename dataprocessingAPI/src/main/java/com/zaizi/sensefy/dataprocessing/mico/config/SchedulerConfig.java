package com.zaizi.sensefy.dataprocessing.mico.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.zaizi.sensefy.dataprocessing.mico.admin.scheduling.CronJob;

@Configuration
public class SchedulerConfig {

	@Bean
	public JobDetailFactoryBean micoIndexUpdateJob() {
		JobDetailFactoryBean indexUpdateJob = new JobDetailFactoryBean();
		indexUpdateJob.setJobClass(CronJob.class);
        indexUpdateJob.setDurability(true);
		return indexUpdateJob;
	}

	@Bean
	@Autowired
	public CronTriggerFactoryBean micoIndexUpdateJobTrigger(JobDetail micoIndexUpdateJob,
			@Value("${mico.cron.expression}") String cronExpression) {
		CronTriggerFactoryBean indexUpdateTrigger = new CronTriggerFactoryBean();
        indexUpdateTrigger.setJobDetail(micoIndexUpdateJob);
        indexUpdateTrigger.setName("micocrontrigger");
        indexUpdateTrigger.setCronExpression(cronExpression);
        return indexUpdateTrigger;
	}
	
	@Bean(name = "micocronscheduler")
    @Autowired
    public SchedulerFactoryBean scheduler(Trigger micoIndexUpdateJobTrigger)
    {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(micoIndexUpdateJobTrigger);
        scheduler.setSchedulerName("micoCronScheduler");
        return scheduler;
    }
}

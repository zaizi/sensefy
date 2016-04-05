package com.zaizi.sensefy.dataprocessing.mico.config;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.zaizi.sensefy.dataprocessing.mico.admin.MicoIndexUpdater;
import com.zaizi.sensefy.dataprocessing.mico.admin.scheduling.CronJob;

@Configuration
public class SchedulerConfig {

	@Bean
	public JobDetailFactoryBean micoIndexUpdateJob(MicoIndexUpdater micoIndexUpdater) {
		JobDetailFactoryBean indexUpdateJob = new JobDetailFactoryBean();
		indexUpdateJob.setJobClass(CronJob.class);
		JobDataMap map = new JobDataMap();
		map.put("micoIndexUpdater", micoIndexUpdater);
        indexUpdateJob.setDurability(true);
        indexUpdateJob.setJobDataAsMap(map);
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
	
	@Bean
	public MicoIndexUpdater micoIndexUpdater(){
		return new MicoIndexUpdater();
	}
}

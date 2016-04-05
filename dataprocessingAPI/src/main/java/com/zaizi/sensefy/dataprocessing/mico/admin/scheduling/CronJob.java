package com.zaizi.sensefy.dataprocessing.mico.admin.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zaizi.sensefy.dataprocessing.mico.admin.MicoIndexUpdater;

public class CronJob extends QuartzJobBean{
	
	private MicoIndexUpdater micoIndexUpdater;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		micoIndexUpdater.updateSolrIndex();
	}

	public void setMicoIndexUpdater(MicoIndexUpdater micoIndexUpdater) {
		this.micoIndexUpdater = micoIndexUpdater;
	}

}

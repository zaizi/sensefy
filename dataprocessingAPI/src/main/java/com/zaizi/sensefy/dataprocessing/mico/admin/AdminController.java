package com.zaizi.sensefy.dataprocessing.mico.admin;

import java.text.ParseException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Provide admin user functionality to schedule jobs for updating solr index with mico data
 * @author Chalitha Perera
 *
 */

@Controller
@RequestMapping(value = "/mico/admin")
public class AdminController {
	
	private static Logger LOG = Logger.getLogger(AdminController.class);
	
	@Autowired
	private ApplicationContext appContext;
	
	private void rescheduleCronJob(String cronExpression){
		StdScheduler scheduler = (StdScheduler) appContext.getBean("micocronscheduler");
		try{
			CronTriggerImpl indexUpdateTrigger = (CronTriggerImpl) scheduler.getTrigger(new TriggerKey("micocrontrigger"));
	        indexUpdateTrigger.setCronExpression(cronExpression);
	        // write configuration changes to properties
	        PropertiesConfiguration props = new PropertiesConfiguration("config/application.properties");
	        props.setProperty("mico.cron.expression", cronExpression);
	        props.save();
		}
		catch (SchedulerException e)
        {
            LOG.error("Error in Reschedling", e);
        }
        catch (ParseException e)
        {
            LOG.error("Cannot parse cron expression");
        }
        catch (ConfigurationException e)
        {
            LOG.error("Error occured in save rescheduled configuration", e);
        }
        LOG.info("Rescheduled the cron job with expression "+cronExpression);
	}

}

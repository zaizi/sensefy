package com.zaizi.sensefy.dataprocessing.mico.admin;

import java.text.ParseException;

import javax.ws.rs.core.Response;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaizi.sensefy.dataprocessing.dto.response.AbstractDataprocessingResponse;
import com.zaizi.sensefy.dataprocessing.dto.response.ResponseError;
import com.zaizi.sensefy.dataprocessing.dto.response.ResponseHeader;
import com.zaizi.sensefy.dataprocessing.dto.response.jobschedule.SchedulerResponse;
import com.zaizi.sensefy.dataprocessing.exception.ComponentCode;

/**
 * Provide admin user functionality to schedule jobs for updating solr index with mico data
 * @author Chalitha Perera
 *
 */

@Controller
@RequestMapping(value = "/mico/admin")
public class AdminController {
	
	private static Logger LOG = Logger.getLogger(AdminController.class);
	
	private static final String DEFAULT_CRON_EXPRESSION = "0 0 1 1 2 ? 2050";
	
	@Autowired
	private ApplicationContext appContext;
	
	@RequestMapping(value = "/createcron", method = RequestMethod.POST)
    @ResponseBody
    public SchedulerResponse createCronJob(@RequestParam("expression") String cronExpression)
    {
        return this.rescheduleCronJob(cronExpression);
    }
	
	@RequestMapping(value = "/deletecron", method = RequestMethod.GET)
    @ResponseBody
    public SchedulerResponse deleteCronJob()
    {
        return this.rescheduleCronJob(DEFAULT_CRON_EXPRESSION);
    }
	
	private SchedulerResponse rescheduleCronJob(String cronExpression){
		StdScheduler scheduler = (StdScheduler) appContext.getBean("micocronscheduler");
		SchedulerResponse response = new SchedulerResponse();
		ResponseHeader header = new ResponseHeader();
		response.setHeader(header);
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
            processException(response, e, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        catch (ParseException e)
        {
            LOG.error("Cannot parse cron expression");
            processException(response, e, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        catch (ConfigurationException e)
        {
            LOG.error("Error occured in save rescheduled configuration", e);
            processException(response, e, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        return response;
	}
	
	private void processException(AbstractDataprocessingResponse response, Exception e, int statusCode){
		ResponseError error = new ResponseError(statusCode, ComponentCode.DATAPROCESSING, e.getMessage());
		response.setError(error);
		response.setStatus(statusCode);
	}

}

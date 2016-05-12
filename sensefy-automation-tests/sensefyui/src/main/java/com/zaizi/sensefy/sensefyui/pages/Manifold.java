package com.zaizi.sensefy.sensefyui.pages;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.Link;

public class Manifold 
{
private static final Logger LOGGER = LogManager.getLogger(Manifold.class);
	
	public WebDriver driver;
	
	
	public Manifold(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }
	
	public void navigateToJobs() throws InterruptedException
	{
		Thread.sleep(5000);
		System.out.println("xxxx");
		Link jobs = new Link(driver, By.xpath("//a[text()='Status and Job Management']"));
		jobs.Click();		
		System.out.println("yyyy");
	}

	public void startTheJob() throws InterruptedException
	{
		navigateToJobs();
		System.out.println("Refresh before starting the job");
	
		Thread.sleep(3000);
		Link startJob = new Link(driver, By.xpath("//tbody//tr[3]//td[1]//a[text()='Start']"));
		//tbody//tr[3]//td[1]//a[1]
		startJob.Click();
		System.out.println("Job started");
	}
	
	public void refreshJob() throws InterruptedException
	{
		
		int i;
		Thread.sleep(3000);
		for(i=1;i<=10;i++)
		{			
			Button refreshButton = new Button(driver, By.xpath("//tbody//tr[5]//td//a[@alt='Refresh status']"));
			//tbody//tr[5]//td//a
			Thread.sleep(8000);
			refreshButton.Click();			
			System.out.println("Refresh "+i+" time/s");
			
		}
		
	}

}

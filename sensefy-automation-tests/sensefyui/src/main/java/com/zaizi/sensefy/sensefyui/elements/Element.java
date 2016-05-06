package com.zaizi.sensefy.sensefyui.elements;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;


public class Element {
	//Define WebDriver
	private WebDriver driver;
	//Define WebElement
	private WebElement element;
	
	public static  ExtentReports extent = ExtentReports.get(Element.class);
	
	public Element(WebDriver driver, By elementIdentifier)
	{
		//WebDriver parameterizing
		this.driver=driver;
		//WebElement parameterizing
		element=this.driver.findElement(elementIdentifier);
	}
	
	//Returning WebElement
	public WebElement getElement()
	{
		return this.element;
	}
	
	//Returning WebDrive
	public WebDriver getDriver()
	{
		return this.driver;
	}
	
	//Returning elements' attribute
	public String getElementAttribute(String attribute)
    {
        return element.getAttribute(attribute);
    }
	
	public final static void reportInitial(WebDriver driver,String htmlName) throws IOException
	{        
		//extent.init("/Users/ljayasinghe/Desktop/"+htmlName+".html", true);    
		extent.init(TestCaseProperties.REPORT_TEST_PATH +htmlName+".html", true);
	}

	public static void waitForLoad(WebDriver driver2) {
		// TODO Auto-generated method stub
		
	}
	
	public static Boolean isElementPresent(WebDriver driver, By xPath)
			throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			driver.findElement(xPath);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}
	
	public final static void takescreenshot(WebDriver driver,String className,String Screenshotname) throws IOException
    {
		    File scrFile1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile1, new File(TestCaseProperties.REPORT_TEST_PATH+className+"/"+Screenshotname+".jpg"));
		    extent.attachScreenshot("./"+className+"/"+Screenshotname+".jpg");  
            System.out.println("Screenshot Taken Successfully!!!!");        
          
    }

}

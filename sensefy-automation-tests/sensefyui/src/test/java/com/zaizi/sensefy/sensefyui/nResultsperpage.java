package com.zaizi.sensefy.sensefyui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.sensefypage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class nResultsperpage 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
		
	/**
	 * Variable Declaration
	 */
	public String username1;
	public String password1;
	public String searchTerm;
	public String results1;
	public String results2;
	public String results3;
	public String screenshot_name;
	

	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(nResultsperpage.class.getName());
		
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(nResultsperpage.class);
	
	/**
	 * defining class name
	 */
	public static String className = nResultsperpage.class.getSimpleName();

	/**
	 * 
	 * @param username1
	 * @param password1
	 * @param searchTerm
	 * @param screenshot_name
	 */
	public nResultsperpage(String username1, String password1, String searchTerm,String results1, String results2, String results3, String screenshot_name) 
	{
		this.username1 = username1;
		this.password1 = password1;
		this.searchTerm = searchTerm;
		this.results1 = results1;
		this.results2 = results2;
		this.results3 = results3;
		this.screenshot_name = screenshot_name;
	}
	
	/**
	 * Declares Parameters Here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "nResultsperpage");
        return TestCaseValues.documentLibraryTestValues("nResultsperpage");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Verify the Results Per page and the Pagination Control");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Verify the Results Per page and the Pagination Control");
    }
	
	@Test
	public void a_LogintoSensefy() throws IOException, InterruptedException
	{
		LOGGER.info("Login to Sensefy Mico");
		extent.startTest("Login to Sensefy Mico");
		
		LOGGER.info("Navigate to Sensefy Mico Url");
        driver = TestCaseProperties.getSensefyMico();
        driver.manage().window().setSize(new Dimension(1920, 1920)); 
        String currentUrl1 = driver.getCurrentUrl().toString();
           
        System.out.println(currentUrl1);
        
        sensefypage sensefypage = new sensefypage(driver);
        sensefypage.logintosensefy(username1, password1);
        
        String sensefyurl = "http://mico.zaizicloud.net";
            
        String currentUrl2 = driver.getCurrentUrl().toString();

        if(!currentUrl2.equalsIgnoreCase(sensefyurl))
        {
        	LOGGER.info("Successfully Login to Sensefy Mico");
        	extent.log(LogStatus.PASS, "Successfully Login to Sensefy Mico");
        		
        	Element.takescreenshot(driver,className,screenshot_name+"1");
        }
        else
        {
           	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        	LOGGER.error("UnSuccessfull - Login Failed");
        	Element.takescreenshot(driver,className,screenshot_name+"2");
        }                  
	}
	
	@Test
	public void b_resultsPerPage() throws IOException, InterruptedException
	{
		LOGGER.info("Search for the Documents and Verify the Pagination Control");
		extent.startTest("Search for the Documents and Verify the Pagination Control");
		
		TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchfield.clearText();
		searchfield.enterText(searchTerm);
		driver.findElement(By.xpath("//div[@id='header']/div[1]/div/div[1]/div[2][text()='Search']")).click();
		Thread.sleep(3000);
			
		String displayed_result1 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final1 = driver.findElement(By.xpath(displayed_result1)).getText().toString();
		System.out.println(final1);
			
		if(results1.equals(final1))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + results1);
			extent.log(LogStatus.INFO, "Current Results : " + final1);
			LOGGER.info("Successful : Successfully navigated to the Results Page");
       		extent.log(LogStatus.PASS, "Successful : Successfully navigated to the Results Page");
       		Element.takescreenshot(driver,className,screenshot_name+"3");
       		
       		String default_feature1 = "//div[@id='se-results']/div[1]/div/div/div[2]/div[1]/div/div/div/div[text()='10']";
       		String default_feature1_result = driver.findElement(By.xpath(default_feature1)).getText().toString();
       		System.out.println(default_feature1_result);
       		
       		if(default_feature1_result.equals("10"))
       		{
       			extent.log(LogStatus.INFO, "Expected Results : 10");
   				extent.log(LogStatus.INFO, "Current Results : " + default_feature1_result);
   				LOGGER.info("Successful : Results per page 10");
           		extent.log(LogStatus.PASS, "Successful :  Results per page 10");
            		
           		String default_feature2 = "//div[@id='se-results']/div[1]/div/div/div[1]/span[text()='Showing results 1-10 of 121']";
           		String default_feature2_result = driver.findElement(By.xpath(default_feature2)).getText().toString();
           		System.out.println(default_feature2_result);
           		String expected_result2 = "Showing results 1-10 of 121";
            		
           		if(expected_result2.equals(default_feature2_result))
           		{
           			extent.log(LogStatus.INFO, "Expected Results : "+expected_result2);
       				extent.log(LogStatus.INFO, "Current Results : " + default_feature2_result);
       				LOGGER.info("Successful : Number of Showing Results are Correct");
               		extent.log(LogStatus.PASS, "Successful :  Number of Showing Results are Correct");
               		Element.takescreenshot(driver,className,screenshot_name+"4");
           		}
           		else
           		{
           			extent.log(LogStatus.INFO, "Expected Results : "+expected_result2);
       				extent.log(LogStatus.INFO, "Current Results : " +default_feature2_result);
       				extent.log(LogStatus.FAIL, "Unsuccessful : Failure in displaying the number of Showing Results.");
               		LOGGER.error("Unsuccessful : Failure : Failure in displaying the number of Showing Results.");
               		Element.takescreenshot(driver,className,screenshot_name+"5");
           		}            		
       		}
       		else
       		{
       			extent.log(LogStatus.INFO, "Expected Results : 10");
   				extent.log(LogStatus.INFO, "Current Results : " + default_feature1_result);
   				extent.log(LogStatus.FAIL, "Unsuccessful : Failure : Results per page 10");
           		LOGGER.error("Unsuccessful : Failure : Results per page 10");
           		Element.takescreenshot(driver,className,screenshot_name+"6");
       		}
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + results1);
			extent.log(LogStatus.INFO, "Current Results : " + final1);
			extent.log(LogStatus.FAIL, "Unsuccessful : Failure in Navigating to the Results Page");
       		LOGGER.error("Unsuccessful : Failure in Navigating to the Results Page");
       		Element.takescreenshot(driver,className,screenshot_name+"7");
		}
		
		//Navigate to the 2nd Page
		driver.findElement(By.xpath("//div[@id='se-results']//dir-pagination-controls//ul//li[4]//a[text()='2']")).click();
		Thread.sleep(5000);
		
		String displayed_result2 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final2 = driver.findElement(By.xpath(displayed_result2)).getText().toString();
		System.out.println(final2);
		
		if(results2.equals(final2))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + results2);
			extent.log(LogStatus.INFO, "Current Results : " + final2);
			LOGGER.info("Successful : Successfully navigated to the 2nd Results Page");
       		extent.log(LogStatus.PASS, "Successful : Successfully navigated to the 2nd Results Page");
       		Element.takescreenshot(driver,className,screenshot_name+"8");
       		
       		String default_feature3 = "//div[@id='se-results']/div[1]/div/div/div[2]/div[1]/div/div/div/div[text()='10']";
       		String default_feature3_result = driver.findElement(By.xpath(default_feature3)).getText().toString();
       		System.out.println(default_feature3_result);
       		
       		if(default_feature3_result.equals("10"))
       		{
       			extent.log(LogStatus.INFO, "Expected Results : 10");
   				extent.log(LogStatus.INFO, "Current Results : " + default_feature3_result);
   				LOGGER.info("Successful : Results per page 10");
           		extent.log(LogStatus.PASS, "Successful :  Results per page 10");
           		
           		String default_feature4 = "//div[@id='se-results']/div[1]/div/div/div[1]/span[text()='Showing results 10-20 of 121']";
           		String default_feature4_result = driver.findElement(By.xpath(default_feature4)).getText().toString();
           		System.out.println(default_feature4_result);
           		String expected_result4 = "Showing results 10-20 of 121";
           		
           		if(expected_result4.equals(default_feature4_result))
           		{
           			extent.log(LogStatus.INFO, "Expected Results : "+expected_result4);
       				extent.log(LogStatus.INFO, "Current Results : " + default_feature4_result);
       				LOGGER.info("Successful : Number of Showing Results are Correct");
               		extent.log(LogStatus.PASS, "Successful :  Number of Showing Results are Correct");
               		Element.takescreenshot(driver,className,screenshot_name+"9");
           		}
           		else
           		{
           			extent.log(LogStatus.INFO, "Expected Results : "+expected_result4);
       				extent.log(LogStatus.INFO, "Current Results : " +default_feature4_result);
       				extent.log(LogStatus.FAIL, "Unsuccessful : Failure in displaying the number of Showing Results.");
               		LOGGER.error("Unsuccessful : Failure : Failure in displaying the number of Showing Results.");
               		Element.takescreenshot(driver,className,screenshot_name+"10");
           		}            		
       		}
       		else
       		{
       			extent.log(LogStatus.INFO, "Expected Results : 10");
   				extent.log(LogStatus.INFO, "Current Results : " + default_feature3_result);
   				extent.log(LogStatus.FAIL, "Unsuccessful : Failure : Results per page 10");
           		LOGGER.error("Unsuccessful : Failure : Results per page 10");
           		Element.takescreenshot(driver,className,screenshot_name+"11");
       		}
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + results2);
			extent.log(LogStatus.INFO, "Current Results : " + final2);
			extent.log(LogStatus.FAIL, "Unsuccessful : Failure in Navigating to the 2nd Results Page");
       		LOGGER.error("Unsuccessful : Failure in Navigating to the 2nd Results Page");
       		Element.takescreenshot(driver,className,screenshot_name+"12");
		}		

	}
	
	
}

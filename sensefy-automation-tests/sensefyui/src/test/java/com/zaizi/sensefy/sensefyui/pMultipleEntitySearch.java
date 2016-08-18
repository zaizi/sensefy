package com.zaizi.sensefy.sensefyui;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
import com.zaizi.sensefy.sensefyui.elements.Label;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.sensefypage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class pMultipleEntitySearch 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	
	/**
	 * Variable declaration
	 */
	public String username;
	public String password;
	public String searchTerm_1;
	public String searchTerm_2;
	public String searchTerm_3;
	public String screenshot_name;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(pMultipleEntitySearch.class.getName());

	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(pMultipleEntitySearch.class);
	
	/**
	 * Defining class name
	 */
	public static String className = pMultipleEntitySearch.class.getSimpleName();

	public pMultipleEntitySearch(String username, String password, String searchTerm_1, String searchTerm_2,
			String searchTerm_3, String screenshot_name) 
	{
		this.username = username;
		this.password = password;
		this.searchTerm_1 = searchTerm_1;
		this.searchTerm_2 = searchTerm_2;
		this.searchTerm_3 = searchTerm_3;
		this.screenshot_name = screenshot_name;
	}
	
	/**
	 * Declares Parameters here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
	public static Iterable<Object[]> data() throws IterableException
	{
		LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING,"pMultipleEntitySearch");
		return TestCaseValues.documentLibraryTestValues("pMultipleEntitySearch");
	}
	
	/**
	 * Extent Report Configurations
	 * @throws IOException
	 */
	@BeforeClass
	public static void beforeClass() throws IOException
	{
		Element.reportInitial(driver, className);
    	extent.config().documentTitle("Multiple Entity Search in Sensefy");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Multiple Entity Search in Sensefy");
	}
	
	@Test
	public void a_logintoSenefy() throws InterruptedException, IOException
	{
		LOGGER.info("Login to Sensefy Local");
		extent.startTest("Login to Sensefy Local");
		
		LOGGER.info("Navigate to Sensefy Local Url");
		driver = TestCaseProperties.getSensefyLocalHost();
		driver.manage().window().setSize(new Dimension(1920, 1920));
		
		String currentUrl1 = driver.getCurrentUrl().toString();
		
		System.out.println(currentUrl1);
		
		sensefypage sensefypage = new sensefypage(driver);
		sensefypage.logintosensefy(username, password);
		
		String sensefyLocalUrl = "";
		String currentUrl2 = driver.getCurrentUrl().toString();
		
		if(!currentUrl2.equalsIgnoreCase(sensefyLocalUrl))
		{
			LOGGER.info("Successfully Login to Sensefy Local");
			extent.log(LogStatus.PASS,"Successfully Login to Senesfy Local");
			
			Element.takescreenshot(driver, className, screenshot_name+"1");
		}
		else
		{
			LOGGER.info("Unsuccessfully Login to Sensefy Local - Login Failed");
			extent.log(LogStatus.FAIL, "Unsuccessful - Login Failed");
			
			Element.takescreenshot(driver, className, screenshot_name+"2");
		}			
	}
	
	@Test
	public void b_EntitySearch() throws InterruptedException, IOException
	{
		LOGGER.info("Multiple Entity Search in Sensefy - Search for two Entities");
		extent.startTest("Multiple Entity Search in Sensefy - Search for two Entities");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 
		TextField searchField = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchField.clearText();
		Thread.sleep(10000);
		
//		searchField.enterText("p");
//		Thread.sleep(3000);
//		searchField.enterText("r");
//		Thread.sleep(3000);
//		searchField.enterText("e");
//		Thread.sleep(5000);
//		searchField.enterText("s");
//		Thread.sleep(3000);
//		searchField.enterText("i");
//		Thread.sleep(3000);
//		searchField.enterText("d");
//		Thread.sleep(5000);
//		searchField.enterText("e");
//		Thread.sleep(3000);		
//		searchField.enterText("n");
//		Thread.sleep(3000);
//		searchField.enterText("t");
//		Thread.sleep(10000);
		
		String test_element_1 ="President";
		
	    String[] alphabets = test_element_1.split("");
	    for(String alphabet : alphabets)
	    {
	        System.out.println(alphabet);
	        searchField.enterText(alphabet);
	        Thread.sleep(3000);
	    }
	    
		String test_element1 = "//div[@class='sub-header'][text()='People']";
		String test_element2 = "//div[@class='et-table et-wrapper']//following::span[text()='"+searchTerm_1+"']";
		
		if(Label.isElementPresent(driver, By.xpath(test_element1)))
		{
			LOGGER.info("Successful : The Entity type is available");
    		extent.log(LogStatus.PASS, "Successful :The Entity type is available.");
    		
			if(Label.isElementPresent(driver, By.xpath(test_element2)))
			{
				LOGGER.info("Successful : The 1st Entity is available");
        		extent.log(LogStatus.PASS, "Successful :The 1st Entity is available.");
        		Element.takescreenshot(driver,className,screenshot_name+"3");
			}
			else
			{
				LOGGER.info("Unsuccessful : The 1st Entity is not available");
        		extent.log(LogStatus.FAIL, "Unsuccessful :The 1st Entity is not available.");
        		Element.takescreenshot(driver,className,screenshot_name+"4");
			}
		}
		else
		{
			LOGGER.info("Unsuccessful : The Entity type is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity type is not available.");
		}
		
		driver.findElement(By.xpath(test_element2)).click();
		
//		Thread.sleep(3000);
//		searchField.enterText("s");
//		Thread.sleep(3000);
//		searchField.enterText("e");
//		Thread.sleep(3000);
//		searchField.enterText("n");
//		Thread.sleep(3000);
//		searchField.enterText("d");
//		Thread.sleep(3000);
//		searchField.enterText("a");
//		Thread.sleep(3000);
//		searchField.enterText("i");
//		Thread.sleep(10000);
		
		String test_element_2 ="Sendai";
		
	    String[] alphabets_2 = test_element_2.split("");
	    for(String alphabet : alphabets_2)
	    {
	        System.out.println(alphabet);
	        searchField.enterText(alphabet);
	        Thread.sleep(3000);
	    }
		
		String test_element3 = "//div[@class='et-cell et-value-wrap']//following::span[text()='"+searchTerm_2+"']";
		
		if(Label.isElementPresent(driver, By.xpath(test_element3)))
		{
			LOGGER.info("Successful : The 2nd Entity is available");
    		extent.log(LogStatus.PASS, "Successful :The 2nd Entity is available.");
    		Element.takescreenshot(driver,className,screenshot_name+"5");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 2nd Entity is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 2nd Entity is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"6");
		}
		 
		driver.findElement(By.xpath(".//*[@id='header']/div[1]/div/div/div/div[2]/ul[2]/li/div/div[2]/div[3]/div/div/div/div/span/span")).click();
		
		driver.findElement(By.xpath("//div[@class='ui yellow button ser-btn']")).click();
		
		LOGGER.info("click on the Search");
		Thread.sleep(3000);
	}
	
	@Test
	public void c_EntitySearch() throws InterruptedException, IOException
	{
		LOGGER.info("Single Entity Search in Sensefy - Verify the Entity Information");
		extent.startTest("Multiple Entity Search in Sensefy - Verify the Entity Information");
		
		String entity_info_1 = "//div[@id='se-results']/div[3]/div[1]/div/div[text()='"+searchTerm_1+"']";
		String entity_info_2 = "//div[@id='se-results']/div[3]/div[2]/div/div[text()='"+searchTerm_2+"']";
		String entity_Information_1 = driver.findElement(By.xpath(entity_info_1)).getText().toString();
		String entity_Information_2 = driver.findElement(By.xpath(entity_info_2)).getText().toString();
		
		if(Label.isElementPresent(driver, By.xpath(entity_info_1)) && driver.findElement(By.xpath(entity_info_1)).getText().toString().equals(entity_Information_1) &&
				Label.isElementPresent(driver, By.xpath(entity_info_2)) && driver.findElement(By.xpath(entity_info_2)).getText().toString().equals(entity_Information_2))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + entity_Information_1);
			extent.log(LogStatus.INFO, "Current Results : " + entity_Information_1);
			
			extent.log(LogStatus.INFO, "Expected Results : " + entity_Information_2);
			extent.log(LogStatus.INFO, "Current Results : " + entity_Information_2);

			LOGGER.info("Successful : The Entity Information is available for "+entity_Information_1);
    		extent.log(LogStatus.PASS, "Successful : The Entity Information is available for "+entity_Information_1);
    		LOGGER.info("Successful : The Entity Information is available for "+entity_Information_2);
    		extent.log(LogStatus.PASS, "Successful : The Entity Information is available for "+entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"7");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + entity_Information_1);
			extent.log(LogStatus.INFO, "Current Results : " + entity_Information_1);
			
			extent.log(LogStatus.INFO, "Expected Results : " + entity_Information_2);
			extent.log(LogStatus.INFO, "Current Results : " + entity_Information_2);
			
			LOGGER.info("Unsuccessful : The Entity Information is not available for "+entity_Information_1);
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity Information is not available for "+entity_Information_1);
    		LOGGER.info("Unsuccessful : The Entity Information is not available for "+entity_Information_2);
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity Information is not available for "+entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"8");
		}		
	}
	
	@Test
	public void d_EntitySearch() throws InterruptedException, IOException
	{
		LOGGER.info("Single Entity Search in Sensefy - Verify the Thumbnail in Entity Information");
		extent.startTest("Multiple Entity Search in Sensefy - Verify the Thumbnail in Entity Information");
		
		String entity_info_1 = "//div[@id='se-results']/div[3]/div[1]/div/div[text()='"+searchTerm_1+"']";
		String entity_info_2 = "//div[@id='se-results']/div[3]/div[2]/div/div[text()='"+searchTerm_2+"']";
		String entity_Information_1 = driver.findElement(By.xpath(entity_info_1)).getText().toString();
		String entity_Information_2 = driver.findElement(By.xpath(entity_info_2)).getText().toString();
		
		String entity_thumb_1 = "//div[@id='se-results']/div[3]/div[1]/div/img";
		String entity_thumb_2 = "//div[@id='se-results']/div[3]/div[2]/div/img";
		
		if(Label.isElementPresent(driver, By.xpath(entity_thumb_1)) && Label.isElementPresent(driver, By.xpath(entity_thumb_2)))
		{
			LOGGER.info("Successful : The Entity Thumbnail is available in both the Entities " +entity_Information_1+ " and " +entity_Information_2);
    		extent.log(LogStatus.PASS, "Successful : The Entity Thumbnail is available for " +entity_Information_1+ " and " +entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"9");
		}
		else
		{
			LOGGER.info("Unsuccessful : The Entity Thumbnail is not available in both the Entities "+entity_Information_1+ " and " +entity_Information_2);
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity Thumbnail is not available in both the Entities "+entity_Information_1+ " and " +entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"10");
		}	
	}
	
	@Test
	public void e_EntitySearch() throws InterruptedException, IOException
	{
		LOGGER.info("Single Entity Search in Sensefy - Verify the Entity Description");
		extent.startTest("Multiple Entity Search in Sensefy - Verify the Entity Description");
		
		String entity_info_1 = "//div[@id='se-results']/div[3]/div[1]/div/div[text()='"+searchTerm_1+"']";
		String entity_info_2 = "//div[@id='se-results']/div[3]/div[2]/div/div[text()='"+searchTerm_2+"']";
		String entity_Information_1 = driver.findElement(By.xpath(entity_info_1)).getText().toString();
		String entity_Information_2 = driver.findElement(By.xpath(entity_info_2)).getText().toString();
		
		String entity_Desc_1 ="//div[@id='se-results']//div[3]//div[2]//div//div[3]//preceding::div[3]";
		String entity_Desc_2 ="//div[@id='se-results']//div[3]//div[2]//div//div[3]";
		
		String Description_1 = driver.findElement(By.xpath(entity_Desc_1)).getText().toString();
		String Description_2 = driver.findElement(By.xpath(entity_Desc_2)).getText().toString();
			
		if(Element.isElementPresent(driver, By.xpath(entity_Desc_1)) && driver.findElement(By.xpath(entity_Desc_1)).getText().toString().equals(Description_1) &&
				Element.isElementPresent(driver, By.xpath(entity_Desc_2)) && driver.findElement(By.xpath(entity_Desc_2)).getText().toString().equals(Description_2))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + Description_1);
			extent.log(LogStatus.INFO, "Current Results : " + Description_1);
			
			extent.log(LogStatus.INFO, "Expected Results : " + Description_2);
			extent.log(LogStatus.INFO, "Current Results : " + Description_2);
			
			LOGGER.info("Successful : The Entity Description is available for "+entity_Information_1);
    		extent.log(LogStatus.PASS, "Successful : The Entity Description is available for "+entity_Information_1);
    		LOGGER.info("Successful : The Entity Description is available for "+entity_Information_2);
    		extent.log(LogStatus.PASS, "Successful : The Entity Description is available for "+entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"11");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + Description_1);
			extent.log(LogStatus.INFO, "Current Results : " + Description_1);
			
			extent.log(LogStatus.INFO, "Expected Results : " + Description_2);
			extent.log(LogStatus.INFO, "Current Results : " + Description_2);
			
			LOGGER.info("Unsuccessful : The Entity Description is not available for "+entity_Information_1);
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity Description is not available for "+entity_Information_1);
    		LOGGER.info("Unsuccessful : The Entity Description is not available for "+entity_Information_2);
    		extent.log(LogStatus.FAIL, "Unsuccessful :The Entity Description is not available for "+entity_Information_2);
    		Element.takescreenshot(driver,className,screenshot_name+"12");
		}
		
		TestCaseProperties.closeDriver(driver);
	}
	
	@Test
	public void f_EntitySearch() throws InterruptedException, IOException
	{
		a_logintoSenefy();
		
		LOGGER.info("Multiple Entity Search in Sensefy - Verify Sort By Functionality");
		extent.startTest("Multiple Entity Search in Sensefy - Verify Sort By Functionality");
		
		TextField searchField = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchField.clearText();
		Thread.sleep(10000);
				
		String test_element1 ="Barack Obama";
		
	    String[] alphabets = test_element1.split("");
	    for(String alphabet : alphabets)
	    {
	        System.out.println(alphabet);
	        searchField.enterText(alphabet);
	        Thread.sleep(3000);
	    }
	    
	    Thread.sleep(5000);
	    
		String test_element_1 = "//div[@class='et-cell et-value-wrap']//following::span[text()='Barack Obama']";
		
		if(Label.isElementPresent(driver, By.xpath(test_element_1)))
		{
			LOGGER.info("Successful : The 1nd Entity is available");
    		extent.log(LogStatus.PASS, "Successful :The 1nd Entity is available.");
    		Element.takescreenshot(driver,className,screenshot_name+"13");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 1nd Entity is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 1nd Entity is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"14");
		}
		 
		//driver.findElement(By.xpath(".//*[@id='header']/div[1]/div/div/div/div[2]/ul[2]/li/div/div[2]/div[3]/div/div/div/div/span/span")).click();
		driver.findElement(By.xpath(test_element_1)).click();
		
	}

	
}
	

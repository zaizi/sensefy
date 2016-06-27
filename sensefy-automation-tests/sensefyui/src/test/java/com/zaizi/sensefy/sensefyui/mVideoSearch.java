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
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.Label;
import com.zaizi.sensefy.sensefyui.elements.Link;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.sensefypage;

/**
 * 
 * @author ljayasinghe
 *
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class mVideoSearch 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	

	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";

	/**
	 * Varialble Declaration
	 */
	public String username1;
	public String password1;
	public String videoname;
	public String sortby_Relevance;
	public String sortby_Name;
	public String sortby_Title;
	public String sortby_Created;
	public String sortby_Modified;
	public String sortby_Creator;
	public String sortby_Modifier;
	public String screenshot_name;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(mVideoSearch.class.getName());
		
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(mVideoSearch.class);
	
	/**
	 * defining class name
	 */
	public static String className = mVideoSearch.class.getSimpleName();

	
	
	public mVideoSearch(String username1, String password1, String videoname, String sortby_Relevance,
			String sortby_Name, String sortby_Title, String sortby_Created, String sortby_Modified,
			String sortby_Creator, String sortby_Modifier, String screenshot_name) 
	{
		this.username1 = username1;
		this.password1 = password1;
		this.videoname = videoname;
		this.sortby_Relevance = sortby_Relevance;
		this.sortby_Name = sortby_Name;
		this.sortby_Title = sortby_Title;
		this.sortby_Created = sortby_Created;
		this.sortby_Modified = sortby_Modified;
		this.sortby_Creator = sortby_Creator;
		this.sortby_Modifier = sortby_Modifier;
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
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "mVideoSearch");
        return TestCaseValues.documentLibraryTestValues("mVideoSearch");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Verify the Video Search Features");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Video Search in Sensefy");
    }
	
	@Test
	public void a_LogintoSensefy()
	{
		LOGGER.info("Login to Sensefy Mico");
		extent.startTest("Login to Sensefy Mico");
		
		try
		{
			LOGGER.info("Navigate to Sensefy Mico Url");
            driver = TestCaseProperties.getSensefyMico();
            
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
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
	
	@Test
	public void b_searchVideo()
	{
		LOGGER.info("Search the Video");
		extent.startTest("Search the Video");
		
		try
		{
			TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			
			searchfield.clearText();
			Thread.sleep(5000);
			
			searchfield.enterText("m");
			Thread.sleep(5000);
			searchfield.enterText("i");
			Thread.sleep(5000);
			searchfield.enterText("l");
			Thread.sleep(5000);
			searchfield.enterText("i");
			Thread.sleep(5000);
			searchfield.enterText("t");
			Thread.sleep(5000);
			searchfield.enterText("a");
			Thread.sleep(5000);
			searchfield.enterText("r");
			Thread.sleep(5000);
			searchfield.enterText("y");
						
			if(Link.isElementPresent(driver, By.xpath("//div[@class='sub-header'][text()='Concepts']")))
			{
				extent.log(LogStatus.INFO, "Expected Results : Concepts");
				extent.log(LogStatus.INFO, "Current Results : " + driver.findElement(By.xpath("//div[@class='sub-header'][text()='Concepts']")).getText().toString());
				LOGGER.info("Successful : The Entity Type 'Concepts' is visible under the suggestions");
        		extent.log(LogStatus.PASS, "Successful : The Entity Type 'Concepts' is visible under the suggestions");
        		
        		String test_element1 = "//div[@class='sub-header'][text()='Concepts']//following-sibling::div[@class='et-cell et-value-wrap']//div[1]//span[text()='military']";
        		
        		if(Link.isElementPresent(driver, By.xpath(test_element1)))
        		{
        			extent.log(LogStatus.INFO, "Expected Results : " + test_element1);
    				extent.log(LogStatus.INFO, "Current Results : " + test_element1);
        			LOGGER.info("Successful :The element under the  Entity Type 'Concepts' is visible under the suggestions");
            		extent.log(LogStatus.PASS, "Successful :The element under the  Entity Type 'Concepts' is visible under the suggestions");
            		
            		new Link(driver, By.xpath(test_element1)).Click();
            		
            		Thread.sleep(3000);
            		
            		String search_video = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a//span";
            		//*******
            		String result = driver.findElement(By.xpath(search_video)).getText().toString();
            		
            		String test_element4 = ".//*[@id='se-results']/div[3]/header";
            		String test_element5 = ".//*[@id='se-results']/div[3]";
            		
            		if(Label.isElementPresent(driver, By.xpath(test_element4)) && Label.isElementPresent(driver, By.xpath(test_element5)))
            		{
            			extent.log(LogStatus.INFO, "Expected Results : " + test_element4);
        				extent.log(LogStatus.INFO, "Current Results : " + test_element4);
        				extent.log(LogStatus.INFO, "Expected Results : " + test_element5);
        				extent.log(LogStatus.INFO, "Current Results : " + test_element5);
            			LOGGER.info("Successful : The element information is displayed.");
                		extent.log(LogStatus.PASS, "Successful : The element information is displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"3");
            		}
            		else
            		{
            			extent.log(LogStatus.INFO, "Expected Results : " + test_element4);
        				extent.log(LogStatus.INFO, "Current Results : " + test_element4);
        				extent.log(LogStatus.INFO, "Expected Results : " + test_element5);
        				extent.log(LogStatus.INFO, "Current Results : " + test_element5);
            			extent.log(LogStatus.FAIL, "Unsuccessful : The element information is not displayed.");
                		LOGGER.error("Unsuccessful : The element information is not displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"4");
            		}
            			            		
             		if(result.equals(videoname))
            		{
        				extent.log(LogStatus.INFO, "Expected Results : " + videoname);
        				extent.log(LogStatus.INFO, "Current Results : " + result);
            			LOGGER.info("Successful :The "+videoname+ " is displayed.");
                		extent.log(LogStatus.PASS, "Successful :TThe "+videoname+ " is displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"5");
              		}
            		else
            		{
        				extent.log(LogStatus.INFO, "Expected Results : " + videoname);
        				extent.log(LogStatus.INFO, "Current Results : " + result);
            			extent.log(LogStatus.FAIL, "Unsuccessful : The "+videoname+ " is not displayed.");
                		LOGGER.error("Unsuccessful : The "+videoname+ " is not displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"6");
            		}
            		
            		
        		}
        		else
        		{
    				extent.log(LogStatus.INFO, "Expected Results : " + test_element1);
    				extent.log(LogStatus.INFO, "Current Results : " + test_element1);
        			extent.log(LogStatus.FAIL, "Unsuccessful : The element under the Entity Type 'Concepts' is not visible under the suggestions");
            		LOGGER.error("Unsuccessful : The element under the Entity Type 'Concepts' is not visible under the suggestions");
            		Element.takescreenshot(driver,className,screenshot_name+"7");
        		}
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : Concepts");
				extent.log(LogStatus.INFO, "Current Results : " + driver.findElement(By.xpath("//div[@class='sub-header'][text()='Concepts']")).getText().toString());
				extent.log(LogStatus.FAIL, "Unsuccessful : The Entity Type 'Concepts' is not visible under the suggestions");
        		LOGGER.error("Unsuccessful : The Entity Type 'Concepts' is not visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"8");
			}
						
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
	
	@Test
	public void c_SortByFunction()
	{
		LOGGER.info("Sort By Functionality");
		extent.startTest("Sort By Functionality");
		
		try
		{
			String test_element1 = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[@class='ui yellow searchable floating dropdown labeled icon button sorting input-select']//i[@class='filter icon']";
			String check_sortby_Result = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a//span";
			String sortby_result = driver.findElement(By.xpath(check_sortby_Result)).getText().toString();
			
			/*String sortby_Name = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[2]";
			String sortby_Title = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[3]";
			String sortby_Created = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[4]";
			String sortby_Modified = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[5]";
			String sortby_Creator = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[6]";
			String sortby_Modifier = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[7]";*/
			
			new Button(driver, By.xpath(test_element1)).Click();
			Thread.sleep(3000);
			
			new Link(driver, By.xpath("//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[1]")).Click();
			Thread.sleep(5000);			
			
			if(sortby_Relevance.equals(sortby_result))
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Relevance);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				LOGGER.info("Successful : The results are sorted by Relevance.");
        		extent.log(LogStatus.PASS, "Successful : The results are sorted by Relevance.");
        		Element.takescreenshot(driver,className,screenshot_name+"9");
			}
			else
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Relevance);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				extent.log(LogStatus.FAIL, "Unsuccessful : The results are not sorted by Relevance.");
        		LOGGER.error("Unsuccessful : The results are not sorted by Relevance.");
        		Element.takescreenshot(driver,className,screenshot_name+"10");
			}
			
			new Button(driver, By.xpath(test_element1)).Click();
			Thread.sleep(3000);
			
			new Link(driver, By.xpath("//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[2]")).Click();
			Thread.sleep(5000);
			
			if(sortby_Title.equals(sortby_result))
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Title);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				LOGGER.info("Successful : The results are sorted by Title.");
        		extent.log(LogStatus.PASS, "Successful : The results are sorted by Title.");
        		Element.takescreenshot(driver,className,screenshot_name+"11");
			}
			else
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Title);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				extent.log(LogStatus.FAIL, "Unsuccessful : The results are not sorted by Title.");
        		LOGGER.error("Unsuccessful : The results are not sorted by Title.");
        		Element.takescreenshot(driver,className,screenshot_name+"12");
			}
			
			new Button(driver, By.xpath(test_element1)).Click();
			Thread.sleep(3000);
			
			new Link(driver, By.xpath("//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[3]")).Click();
			Thread.sleep(5000);
			
			if(sortby_Name.equals(sortby_result))
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Name);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				LOGGER.info("Successful : The results are sorted by Name.");
        		extent.log(LogStatus.PASS, "Successful : The results are sorted by Name.");
        		Element.takescreenshot(driver,className,screenshot_name+"13");
			}
			else
			{
				System.out.println(driver.findElement(By.xpath(check_sortby_Result)).getText().toString());
				extent.log(LogStatus.INFO, "Expected Results : " + sortby_Name);
				extent.log(LogStatus.INFO, "Current Results : " + sortby_result);
				extent.log(LogStatus.FAIL, "Unsuccessful : The results are not sorted by Name.");
        		LOGGER.error("Unsuccessful : The results are not sorted by Name.");
        		Element.takescreenshot(driver,className,screenshot_name+"14");
			}
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}

}

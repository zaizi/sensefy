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
import com.zaizi.sensefy.sensefyui.elements.Element;
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
public class lEntitySearch 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	
	/**
	 * 
	 */
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	
	/**
	 * Variable declaration
	 */
	public String username;
	public String password;
	public String search_term_people;
	public String search_term_places;
	public String search_term_organization;
	public String screenshot_name;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(lEntitySearch.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(lEntitySearch.class);
	
	/**
	 * defining class name
	 */
	public static String className = lEntitySearch.class.getSimpleName();

	public lEntitySearch(String username, String password, String search_term_people, String search_term_places,
			String search_term_organization, String screenshot_name) 
	{
		this.username = username;
		this.password = password;
		this.search_term_people = search_term_people;
		this.search_term_places = search_term_places;
		this.search_term_organization = search_term_organization;
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
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "lEntitySearch");
        return TestCaseValues.documentLibraryTestValues("lEntitySearch");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Entity Search Test");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Entity Search Features in Sensefy");
    }
	
	@Test
	public void a_SensefyQaLogin()
	{
		LOGGER.info("Login to Sensefy QA");
		extent.startTest("Login to Sensefy QA");
		
		try
		{
			LOGGER.info("Navigate to Sensefy QA Url");
            driver = TestCaseProperties.getSensefyQa();
            
            String currentUrl1 = driver.getCurrentUrl().toString();
            
            System.out.println(currentUrl1);
            
            sensefypage sensefypage = new sensefypage(driver);
            sensefypage.logintosensefy(username, password);
            
            String sensefyurl = "http://sensefyqa.zaizicloud.net";
            
            String currentUrl2 = driver.getCurrentUrl().toString();

        	if(!currentUrl2.equalsIgnoreCase(sensefyurl))
            {
               	LOGGER.info("Successfully Login to Sensefy QA");
        		extent.log(LogStatus.PASS, "Successfully Login to Sensefy QA");
        		
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
	public void b_EntityTypePeople()
	{
		LOGGER.info("Search Result in Entity Type : People");
		extent.startTest("Search Result in Entity Type : People");
		
		try
		{
			Thread.sleep(3000);
			TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			searchfield.enterText(search_term_people);
			
			Thread.sleep(15000);
			String test_element1 = "//div[@class='sub-header'][text()='People']";
			
			if(Link.isElementPresent(driver, By.xpath(test_element1)))
			{
				LOGGER.info("Successful : The Entity Type 'People' is visible under the suggestions");
        		extent.log(LogStatus.PASS, "Successful : The Entity Type 'People' is visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"3");
        		//Thread.sleep(3000);
        		
        		String test_element2="Mark";
        		
        		if(Link.isElementPresent(driver, By.xpath("")))
        		{
        			//div[@class='sub-header'][text()='People']//following-sibling::div[@class='et-cell et-value-wrap']//div[3]//span[text()='Mark']
        			
        			
        		}
			}
			else
			{
				extent.log(LogStatus.FAIL, "Unsuccessful : The Entity Type 'People' is not visible under the suggestions");
        		LOGGER.error("Unsuccessful : The Entity Type 'People' is not visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"4");
        		//Thread.sleep(3000);
			}
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
}

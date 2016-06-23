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
			searchfield.enterText("T");
			Thread.sleep(5000);
			searchfield.enterText("h");
			Thread.sleep(5000);
			searchfield.enterText("o");
			Thread.sleep(5000);
			searchfield.enterText("m");
			Thread.sleep(5000);
			searchfield.enterText("a");
			Thread.sleep(5000);
			searchfield.enterText("s");
						
			String test_element1 = "//div[@class='sub-header'][text()='People']";
			
			if(Link.isElementPresent(driver, By.xpath(test_element1)))
			{
				LOGGER.info("Successful : The Entity Type 'People' is visible under the suggestions");
        		extent.log(LogStatus.PASS, "Successful : The Entity Type 'People' is visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"3");
   
        		String test_element2="Thomas";
        		String test_element3 = "//div[@class='sub-header'][text()='People']//following-sibling::div[@class='et-cell et-value-wrap']//div[1]//span[text()='Thomas']";
        		
        		System.out.println("xxxxxx");
        		
        		if(Link.isElementPresent(driver, By.xpath(test_element3)))
        		{
        			//div[@class='sub-header'][text()='People']//following-sibling::div[@class='et-cell et-value-wrap']//div[3]//span[text()='Mark']
        			LOGGER.info("Successful : The element under the  Entity Type 'People' is visible under the suggestions");
        			extent.log(LogStatus.PASS, "Successful : The element under the Entity Type 'People' is visible under the suggestions");
        			Element.takescreenshot(driver,className,screenshot_name+"4");
        			
            		System.out.println("yyyyy");
            		Thread.sleep(3000);
            		
            		Link select_suggestion = new Link(driver, By.xpath(test_element3));
            		select_suggestion.Click();
            		Thread.sleep(5000);
            		
            		System.out.println("zzzzzzzz");
            		
            		String test_element4 = ".//*[@id='se-results']/div[3]/header";
            		String test_element5 = ".//*[@id='se-results']/div[3]";
            		
            		if(Label.isElementPresent(driver, By.xpath(test_element4)) && Label.isElementPresent(driver, By.xpath(test_element5)))
            		{
            			LOGGER.info("Successful : The element information is displayed.");
                		extent.log(LogStatus.PASS, "Successful : The element information is displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"5");
            		}
            		else
            		{
            			extent.log(LogStatus.FAIL, "Unsuccessful : The element information is not displayed.");
                		LOGGER.error("Unsuccessful : The element information is not displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"6");
            		}
        		}
        		else
        		{
        			extent.log(LogStatus.FAIL, "Unsuccessful : The element under the Entity Type 'People' is not visible under the suggestions");
            		LOGGER.error("Unsuccessful : The element under the Entity Type 'People' is not visible under the suggestions");
            		Element.takescreenshot(driver,className,screenshot_name+"7");
        		}
			}
			else
			{
				extent.log(LogStatus.FAIL, "Unsuccessful : The Entity Type 'People' is not visible under the suggestions");
        		LOGGER.error("Unsuccessful : The Entity Type 'People' is not visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"8");
        		//Thread.sleep(3000);
			}
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
	
	@Test
	public void c_EntitySearchPlaces()
	{
		LOGGER.info("Search Result in Entity Type : Places");
		extent.startTest("Search Result in Entity Type : Places");
		
		try
		{
			//TestCaseProperties.getSensefyQa().getCurrentUrl();
			driver.navigate().refresh();
			
			TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			//searchfield.enterText(" ");
			searchfield.clearText();
			Thread.sleep(5000);
			
			//TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			searchfield.enterText("D");
			Thread.sleep(5000);
			searchfield.enterText("a");
			Thread.sleep(5000);
			searchfield.enterText("l");
			Thread.sleep(5000);
			searchfield.enterText("l");
			Thread.sleep(5000);
			searchfield.enterText("a");
			Thread.sleep(5000);
			searchfield.enterText("s");

			String test_element1 = "//div[@class='sub-header'][text()='Places']";
			
			if(Link.isElementPresent(driver, By.xpath(test_element1)))
			{
				LOGGER.info("Successful : The Entity Type 'Places' is visible under the suggestions");
        		extent.log(LogStatus.PASS, "Successful : The Entity Type 'Places' is visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"9");
   
        		String test_element2="Dallas";
        		String test_element3 = "//div[@class='sub-header'][text()='Places']//following-sibling::div[@class='et-cell et-value-wrap']//div[1]//span[1][text()='Dallas']";
        	        		
        		System.out.println("xxxxxx");
        	
        		if(Link.isElementPresent(driver, By.xpath(test_element3)))
        		{
        			//div[@class='sub-header'][text()='People']//following-sibling::div[@class='et-cell et-value-wrap']//div[3]//span[text()='Mark']
        			LOGGER.info("Successful : The element under the  Entity Type 'Places' is visible under the suggestions");
        			extent.log(LogStatus.PASS, "Successful : The element under the Entity Type 'Places' is visible under the suggestions");
        			Element.takescreenshot(driver,className,screenshot_name+"10");
        			
            		System.out.println("yyyyy");
            		Thread.sleep(3000);
            		
            		Link select_suggestion = new Link(driver, By.xpath(test_element3));
            		select_suggestion.Click();
            		Thread.sleep(5000);
            		
            		System.out.println("zzzzzzzz");
            		
            		String test_element4 = ".//*[@id='se-results']/div[3]/header";
            		String test_element5 = ".//*[@id='se-results']/div[3]";
            		
            		if(Label.isElementPresent(driver, By.xpath(test_element4)) && Label.isElementPresent(driver, By.xpath(test_element5)))
            		{
            			LOGGER.info("Successful : The element information is displayed.");
                		extent.log(LogStatus.PASS, "Successful : The element information is displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"11");
            		}
            		else
            		{
            			extent.log(LogStatus.FAIL, "Unsuccessful : The element information is not displayed.");
                		LOGGER.error("Unsuccessful : The element information is not displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"12");
            		}
        		}
        		else
        		{
        			extent.log(LogStatus.FAIL, "Unsuccessful : The element under the Entity Type 'Places' is not visible under the suggestions");
            		LOGGER.error("Unsuccessful : The element under the Entity Type 'Places' is not visible under the suggestions");
            		Element.takescreenshot(driver,className,screenshot_name+"13");
        		}
			}
			else
			{
				extent.log(LogStatus.FAIL, "Unsuccessful : The Entity Type 'Places' is not visible under the suggestions");
        		LOGGER.error("Unsuccessful : The Entity Type 'Places' is not visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"14");	
			}
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
	
	@Test
	public void c_EntitySearchOrganization()
	{
		LOGGER.info("Search Result in Entity Type : Organization");
		extent.startTest("Search Result in Entity Type : Organization");
		
		try
		{
			//TestCaseProperties.getSensefyQa().getCurrentUrl();
			driver.navigate().refresh();
			
			TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			//searchfield.enterText(" ");
			searchfield.clearText();
			Thread.sleep(5000);
			
			//TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			searchfield.enterText("J");
			Thread.sleep(5000);
			searchfield.enterText("C");
			Thread.sleep(5000);
			searchfield.enterText(" ");
			Thread.sleep(5000);
			searchfield.enterText("P");
			Thread.sleep(5000);
			searchfield.enterText("e");
			Thread.sleep(5000);
			searchfield.enterText("n");
			Thread.sleep(5000);
			searchfield.enterText("n");
			Thread.sleep(5000);
			searchfield.enterText("e");
			Thread.sleep(5000);
			searchfield.enterText("y");
			

			String test_element1 = "//div[@class='sub-header'][text()='Organization']";
			
			if(Link.isElementPresent(driver, By.xpath(test_element1)))
			{
				LOGGER.info("Successful : The Entity Type 'Organization' is visible under the suggestions");
        		extent.log(LogStatus.PASS, "Successful : The Entity Type 'Organization' is visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"15");
   
        		//String test_element2="Dallas";
        		String test_element3 = "//div[@class='sub-header'][text()='Orgaization']//following-sibling::div[@class='et-cell et-value-wrap']//div[1]//span[1][text()='JC Penney']";
        		//Thread.sleep(10000);
        		
        		System.out.println("xxxxxx");
        	
        		if(Link.isElementPresent(driver, By.xpath(search_term_organization)))
        		{
        			//div[@class='sub-header'][text()='People']//following-sibling::div[@class='et-cell et-value-wrap']//div[3]//span[text()='Mark']
        			LOGGER.info("Successful : The element under the  Entity Type 'Organization' is visible under the suggestions");
        			extent.log(LogStatus.PASS, "Successful : The element under the Entity Type 'Organization' is visible under the suggestions");
        			Element.takescreenshot(driver,className,screenshot_name+"16");
        			
            		System.out.println("yyyyy");
            		Thread.sleep(3000);
            		
            		Link select_suggestion = new Link(driver, By.xpath(test_element3));
            		select_suggestion.Click();
            		Thread.sleep(5000);
            		
            		System.out.println("zzzzzzzz");
            		
            		String test_element4 = ".//*[@id='se-results']/div[3]/header";
            		String test_element5 = ".//*[@id='se-results']/div[3]";
            		
            		if(Label.isElementPresent(driver, By.xpath(test_element4)) && Label.isElementPresent(driver, By.xpath(test_element5)))
            		{
            			LOGGER.info("Successful : The element information is displayed.");
                		extent.log(LogStatus.PASS, "Successful : The element information is displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"17");
            		}
            		else
            		{
            			extent.log(LogStatus.FAIL, "Unsuccessful : The element information is not displayed.");
                		LOGGER.error("Unsuccessful : The element information is not displayed.");
                		Element.takescreenshot(driver,className,screenshot_name+"18");
            		}
        		}
        		else
        		{
        			extent.log(LogStatus.FAIL, "Unsuccessful : The element under the Entity Type 'Organization' is not visible under the suggestions");
            		LOGGER.error("Unsuccessful : The element under the Entity Type 'Organization' is not visible under the suggestions");
            		Element.takescreenshot(driver,className,screenshot_name+"19");
        		}
			}
			else
			{
				extent.log(LogStatus.FAIL, "Unsuccessful : The Entity Type 'Organization' is not visible under the suggestions");
        		LOGGER.error("Unsuccessful : The Entity Type 'Organization' is not visible under the suggestions");
        		Element.takescreenshot(driver,className,screenshot_name+"20");	
			}
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	}
}

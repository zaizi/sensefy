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
import org.openqa.selenium.WebElement;

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
public class lEntitySearch 
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
	public void a_SensefyQaLogin() throws InterruptedException, IOException
	{
		LOGGER.info("Login to Sensefy QA");
		extent.startTest("Login to Sensefy QA");
		
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
	
	@Test
	public void b_EntityTypePeople() throws IOException, InterruptedException
	{
		LOGGER.info("Search Result in Entity Type : People");
		extent.startTest("Search Result in Entity Type : People");

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

	
	@Test
	public void c_EntitySearchPlaces() throws IOException, InterruptedException
	{
		LOGGER.info("Search Result in Entity Type : Places");
		extent.startTest("Search Result in Entity Type : Places");

		driver.navigate().refresh();
		
		TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		
		searchfield.clearText();
		Thread.sleep(5000);
			
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

	@Test
	public void d_EntitySearchOrganization() throws IOException, InterruptedException
	{
		LOGGER.info("Search Result in Entity Type : Organizations");
		extent.startTest("Search Result in Entity Type : Organizations");

		driver.navigate().refresh();
		
		TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			
		searchfield.clearText();
		Thread.sleep(5000);
			
		searchfield.enterText("U");
		Thread.sleep(5000);
		searchfield.enterText("C");
		Thread.sleep(5000);
		searchfield.enterText("h");
		Thread.sleep(5000);
		searchfield.enterText("i");
		Thread.sleep(5000);
		searchfield.enterText("c");
		Thread.sleep(5000);
		searchfield.enterText("a");
		Thread.sleep(5000);
		searchfield.enterText("g");
		Thread.sleep(5000);
		searchfield.enterText("o");
			
		String test_element1 = "//div[@class='sub-header'][text()='Organizations']";
		
		if(Link.isElementPresent(driver, By.xpath(test_element1)))
		{
			LOGGER.info("Successful : The Entity Type 'Organizations' is visible under the suggestions");
			extent.log(LogStatus.PASS, "Successful : The Entity Type 'Organizations' is visible under the suggestions");
			Element.takescreenshot(driver,className,screenshot_name+"15");
   
			String test_element2="UChicago";
			String test_element3 = "//div[@class='sub-header'][text()='Organizations']//following-sibling::div[@class='et-cell et-value-wrap']//div[1]//span[1][text()='UChicago']";
        		
			//.//*[@id='header']/div[1]/div/div[2]/ul[2]/li/div/div[3]/div[3]/div/div    
			//String test_element3 = ".//*[@id='header']/div[1]/div/div[2]/ul[2]/li/div/div[3]/div[3]/div/div ";
			System.out.println("xxxxxx");
        	
			if(Link.isElementPresent(driver, By.xpath(test_element3)))
			{
				LOGGER.info("Successful : The element under the  Entity Type 'Organizations' is visible under the suggestions");
				extent.log(LogStatus.PASS, "Successful : The element under the Entity Type 'Organizations' is visible under the suggestions");
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

	//**********************************************************************
	@Test
	public void e_VerifyUIFeatures() throws InterruptedException, IOException
	{
		LOGGER.info("Verify the UI Feature in Entity Search");
		extent.startTest("Verify the UI Features in Entity Search");
		
		String test_element1 = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[@class='ui yellow searchable floating dropdown labeled icon button sorting input-select']//i[@class='filter icon']";
		String test_element2 = "//div[@id='ds-tabs']//a[2]//span";
		String test_element3 = "//div[@id='se-results']//div[1]//div//div//div[2]//div[1]//div//div//div//i[@class='dropdown icon']";
		String test_element4 = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[@translate='filterby']";
			
		if(Button.isElementPresent(driver, By.xpath(test_element1)))
		{
			new Button(driver, By.xpath(test_element1)).Click();
			Thread.sleep(3000);
			
			String sortby_Relevance = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[1]";
			String sortby_Name = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[2]";
			String sortby_Title = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[3]";
			String sortby_Created = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[4]";
			String sortby_Modified = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[5]";
			String sortby_Creator = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[6]";
			String sortby_Modifier = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[7]";
				
			if(Button.isElementPresent(driver, By.xpath(sortby_Relevance)))
			{
				new Link(driver, By.xpath(sortby_Relevance)).Click();
				Thread.sleep(5000);
					
				LOGGER.info("Successful : The sort by Relevance is available");
				extent.log(LogStatus.PASS, "Successful : The sort by Relevance is available");
				/*
				//****************
				String relevance_result = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a//span";
				String relevance_output = "26780803.1075855789160.JavaMail.evans@thyme.txt";
										
				System.out.println((relevance_result).toString());
					
				if(Link.isElementPresent(driver, By.xpath(relevance_result)).toString().equals(relevance_output))
				{
					LOGGER.info("Successful : Successfully Sorted by Relevance.");
		    		extent.log(LogStatus.PASS, "Successful : Successfully Sorted by Relevance.");
				}
				else
				{
					extent.log(LogStatus.FAIL, "Unsuccessful : Unsuccessfully sorted by Relevance");
		        	LOGGER.error("Unsuccessful : Unsuccessfully sorted by Relevance");
				}
				//*****************
				*/
				if(Button.isElementPresent(driver, By.xpath(sortby_Name)))
				{
					LOGGER.info("Successful : The sort by Name is available");
		    		extent.log(LogStatus.PASS, "Successful : The sort by Name is available");
		    		
					if(Button.isElementPresent(driver, By.xpath(sortby_Title)))
					{
						LOGGER.info("Successful : The sort by Title is available");
			    		extent.log(LogStatus.PASS, "Successful : The sort by Title is available");
			    		
						if(Button.isElementPresent(driver, By.xpath(sortby_Created)))
						{
							LOGGER.info("Successful : The sort by Created is available");
				   			extent.log(LogStatus.PASS, "Successful : The sort by Created is available");
				   			
							if(Button.isElementPresent(driver, By.xpath(sortby_Modified)))
							{
								LOGGER.info("Successful : The sort by Modified is available");
				    			extent.log(LogStatus.PASS, "Successful : The sort by Modified is available");
				    			
								if(Button.isElementPresent(driver, By.xpath(sortby_Creator)))
								{
									LOGGER.info("Successful : The sort by Creator is available");
					    			extent.log(LogStatus.PASS, "Successful : The sort by Creator is available");
						    			
									if(Button.isElementPresent(driver, By.xpath(sortby_Modifier)))
									{
										LOGGER.info("Successful : The sort by Modifier is available");
						    			extent.log(LogStatus.PASS, "Successful : The sort by Modifier is available");
						    			Element.takescreenshot(driver,className,screenshot_name+"21");
									}
									else
									{
										extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Modifier is not available");
						        		LOGGER.error("Unsuccessful : The sort by Modifier is not available");
									}
								}
								else
								{
									extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Creator is not available");
					        		LOGGER.error("Unsuccessful : The sort by Creator is not available");
								}
							}
							else
							{
								extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Modified is not available");
				        		LOGGER.error("Unsuccessful : The sort by Modified is not available");
							}
						}
						else
						{
							extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Created is not available");
				       		LOGGER.error("Unsuccessful : The sort by Created is not available");
						}
					}
					else
					{
						extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Title is not available");
			       		LOGGER.error("Unsuccessful : The sort by Title is not available");
					}
				}
				else
				{
					extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Name is not available");
		        	LOGGER.error("Unsuccessful : The sort by Name is not available");
				}
			}
    			
			LOGGER.info("Successful : The sort by Relevance is available");
    		extent.log(LogStatus.PASS, "Successful : The sort by Relevance is available");
    		
		}
		else
		{
			extent.log(LogStatus.FAIL, "Unsuccessful : The sort by Feature is not available");
        	LOGGER.error("Unsuccessful : The sort by Feature is not available");
        	Element.takescreenshot(driver,className,screenshot_name+"211");
		}
			
		Thread.sleep(3000);
			
		if(Button.isElementPresent(driver, By.xpath(test_element2)))
		{
			new Button(driver, By.xpath(test_element2)).Click();
			
    		LOGGER.info("Successful : The Alfresco Data source is available");
    		extent.log(LogStatus.PASS, "Successful : The Alfresco Data source is available");
    		Element.takescreenshot(driver,className,screenshot_name+"22");
		}
		else
		{
			extent.log(LogStatus.FAIL, "Unsuccessful : The Alfresco Data source is not available");
        	LOGGER.error("Unsuccessful : The Alfresco Data source is not available");
        	Element.takescreenshot(driver,className,screenshot_name+"24");
		}
		
		Thread.sleep(3000);
			
		if(Button.isElementPresent(driver, By.xpath(test_element3)))
		{
			new Button(driver, By.xpath(test_element3)).Click();
			
			Thread.sleep(3000);
			
    		LOGGER.info("Successful : The Results per page drop down icon is available");
    		extent.log(LogStatus.PASS, "Successful : The Results per page drop down icon is available");
    		Element.takescreenshot(driver,className,screenshot_name+"25");
    		
		}
		else
		{
			extent.log(LogStatus.FAIL, "Unsuccessful : The Results per page drop down icon is not available");
        	LOGGER.error("Unsuccessful : The Results per page drop down icon is not available");

        	Element.takescreenshot(driver,className,screenshot_name+"26");
		}
		
		Thread.sleep(3000);
		
		if(Button.isElementPresent(driver, By.xpath(test_element4)))
		{
			//new Button(driver, By.xpath(test_element4)).Click();
			String filter_By_Creator = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[1]//header";
    		String filter_By_DocumentType = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[2]//header";
    		String filter_By_Language = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[3]//header";
    		String filter_By_Size = "//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[4]//header";
    		
    		if(Label.isElementPresent(driver, By.xpath(filter_By_Creator)) && Label.isElementPresent(driver, By.xpath(filter_By_DocumentType)) && 
    				Label.isElementPresent(driver, By.xpath(filter_By_DocumentType)) && Label.isElementPresent(driver, By.xpath(filter_By_Size)))
    		{
    			LOGGER.info("Successful : The Fiter By facet is available");
        		extent.log(LogStatus.PASS, "Successful : The Fiter By facet is available");
        		Element.takescreenshot(driver,className,screenshot_name+"27");
    		}
    		else
    		{
    			extent.log(LogStatus.FAIL, "Unsuccessful : The Fiter By facet is not available");
           		LOGGER.error("Unsuccessful : The Fiter By facet is not available");
           		Element.takescreenshot(driver,className,screenshot_name+"28");
    		}   			
		}
		else
		{
			extent.log(LogStatus.FAIL, "Unsuccessful : The Fiter By facet is not available");
        	LOGGER.error("Unsuccessful : The Fiter By facet is not available");
        	Element.takescreenshot(driver,className,screenshot_name+"29");
		}
		

	}
}

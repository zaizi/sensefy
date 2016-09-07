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
import org.openqa.selenium.JavascriptExecutor;
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
	public String searchTerm_4;
	public String SortByRelevance;
	public String SortByName;
	public String SortByTitle;
	public String SortByCreated;
	public String SortByModified;
	public String SortByCreator;
	public String SortByModifier;
	public String FilterByCreator;
	public String FilterByDocumentType1;
	public String FilterByDocumentType2;
	public String FilterByLanguage;
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
			String searchTerm_3, String searchTerm_4, String sortByRelevance, String sortByName, String sortByTitle,
			String sortByCreated, String sortByModified, String sortByCreator, String sortByModifier, String FilterByCreator,
			String FilterByDocumentType1, String FilterByDocumentType2, String FilterByLanguage,String screenshot_name) 
	{
		this.username = username;
		this.password = password;
		this.searchTerm_1 = searchTerm_1;
		this.searchTerm_2 = searchTerm_2;
		this.searchTerm_3 = searchTerm_3;
		this.searchTerm_4 = searchTerm_4;
		SortByRelevance = sortByRelevance;
		SortByName = sortByName;
		SortByTitle = sortByTitle;
		SortByCreated = sortByCreated;
		SortByModified = sortByModified;
		SortByCreator = sortByCreator;
		SortByModifier = sortByModifier;
		this.FilterByCreator = FilterByCreator;
		this.FilterByDocumentType1 =FilterByDocumentType1;
		this.FilterByDocumentType2 = FilterByDocumentType2;
		this.FilterByLanguage = FilterByLanguage;
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
		//driver.manage().window().setSize(new Dimension(1920, 1920));
		
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
		
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 
		TextField searchField = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchField.clearText();
		Thread.sleep(10000);

		String test_element_1 = searchTerm_1;
		
	    String[] alphabets = test_element_1.split("");
	    for(String alphabet : alphabets)
	    {
	        System.out.println(alphabet);
	        searchField.enterText(alphabet);
	        Thread.sleep(3000);
	    }
	    
		String test_element1 = "//div[@class='sub-header'][text()='People']";
		String test_element2 = "//div[@class='et-table et-wrapper']//following::span[text()='Michelle']";
		
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

		String test_element_2 = searchTerm_2;
		
	    String[] alphabets_2 = test_element_2.split("");
	    for(String alphabet : alphabets_2)
	    {
	        System.out.println(alphabet);
	        searchField.enterText(alphabet);
	        Thread.sleep(3000);
	    }
		
		String test_element3 = "//div[@class='et-cell et-value-wrap']//following::span[text()='Sri']";
		
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
				
		String test_element1 = searchTerm_3;
		
	    String[] alphabets1 = test_element1.split("");
	    for(String alphabet_1 : alphabets1)
	    {
	        System.out.println(alphabet_1);
	        searchField.enterText(alphabet_1);
	        Thread.sleep(3000);
	    }
	    
	    Thread.sleep(10000);
	    
//		String test_element_1 = "//div[@id='header']//div[1]//div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div[10]//div//div//div//span//span";
		String test_element_1="//div[@id='header']//div[1]/div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div//div//div//div//span//span[2]";
		
		if(Label.isElementPresent(driver, By.xpath(test_element_1)))
		{
			LOGGER.info("Successful : The 1st Entity "+searchTerm_3+" is available");
    		extent.log(LogStatus.PASS, "Successful :The 1st Entity "+searchTerm_3+" is available.");
    		Element.takescreenshot(driver,className,screenshot_name+"13");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 1st Entity "+searchTerm_3+" is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 1st Entity "+searchTerm_3+" is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"14");
		}
		 
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("arguments[0].click();", test_element_1);
		//Thread.sleep(5000);
		//test_element_1.
		driver.findElement(By.xpath(test_element_1)).click();
		
		String test_element2= searchTerm_4;
		
		String[] alphabets2 = test_element2.split("");
		for(String alphabet_2 : alphabets2)
		{
			System.out.println(alphabet_2);
			searchField.enterText(alphabet_2);
			Thread.sleep(3000);
		}
		
		Thread.sleep(5000);
		
		String test_element_2 = "//div[@id='header']//div[1]//div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div[10]//div//div//div//span//span";
//		String test_element_2 = "//div[@id='header']//div[1]//div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div[7]//div//div//div//span//span";
		
		if(Label.isElementPresent(driver, By.xpath(test_element_2)))
		{
			LOGGER.info("Successful : The 2nd Entity "+searchTerm_4+" is available");
    		extent.log(LogStatus.PASS, "Successful :The 2nd Entity "+searchTerm_4+" is available");
    		Element.takescreenshot(driver,className,screenshot_name+"15");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 2nd Entity "+searchTerm_4+" is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 2nd Entity "+searchTerm_4+" is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"16");
		}
		
		driver.findElement(By.xpath(test_element_2)).click();
		driver.findElement(By.xpath("//div[@class='ui yellow button ser-btn']")).click();
		
		LOGGER.info("click on the Search");
		Thread.sleep(3000);
		
		String sortby_Relevance = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[1]";
		String sortby_Name = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[2]";
		String sortby_Title = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[3]";
		String sortby_Created = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[4]";
		String sortby_Modified = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[5]";
		String sortby_Creator = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[6]";
		String sortby_Modifier = "//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[7]";
		
		//Verify Sort By Relevance
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Relevance']")).Click();
		LOGGER.info("Click on Sort By Button");
		Thread.sleep(5000);

		if(Element.isElementPresent(driver, By.xpath(sortby_Relevance)))
		{
			LOGGER.info("Successful : The Sort By Relevance is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Relevance is available");
			
			driver.findElement(By.xpath(sortby_Relevance)).click();
			Thread.sleep(3000);
			
			String relevance_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(relevance_result.equals(SortByRelevance))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByRelevance);
				extent.log(LogStatus.INFO, "Current Results : " + relevance_result);
				LOGGER.info("Successful : The results are successfully sorted by Relevance.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Relevance.");
        		Element.takescreenshot(driver,className,screenshot_name+"17");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByRelevance);
				extent.log(LogStatus.INFO, "Current Results : " + relevance_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Relevance.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Relevance.");
        		Element.takescreenshot(driver,className,screenshot_name+"18");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Relevance is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Relevance not is available");	
		}
		
		//Verify Sort By Name
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Relevance']")).Click();
		LOGGER.info("Click on Sort By Button");	

		if(Element.isElementPresent(driver, By.xpath(sortby_Name)))
		{
			LOGGER.info("Successful : The Sort By Name is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Name is available");
			
			driver.findElement(By.xpath(sortby_Name)).click();
			Thread.sleep(5000);
			
			String name_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(name_result.equals(SortByName))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByName);
				extent.log(LogStatus.INFO, "Current Results : " + name_result);
				LOGGER.info("Successful : The results are successfully sorted by Name.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Name.");
        		Element.takescreenshot(driver,className,screenshot_name+"19");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByName);
				extent.log(LogStatus.INFO, "Current Results : " + name_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Name.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Name.");
        		Element.takescreenshot(driver,className,screenshot_name+"20");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Name is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Name not is available");	
		}
		
		//Verify Sort by Title
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Name']")).Click();
		LOGGER.info("Click on Sort By Button");
		
		if(Element.isElementPresent(driver, By.xpath(sortby_Title)))
		{
			LOGGER.info("Successful : The Sort By Title is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Title is available");
			
			driver.findElement(By.xpath(sortby_Title)).click();
			Thread.sleep(5000);
			
			String title_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(title_result.equals(SortByTitle))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByTitle);
				extent.log(LogStatus.INFO, "Current Results : " + title_result);
				LOGGER.info("Successful : The results are successfully sorted by Title.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Title.");
        		Element.takescreenshot(driver,className,screenshot_name+"21");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByTitle);
				extent.log(LogStatus.INFO, "Current Results : " + title_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Title.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Title.");
        		Element.takescreenshot(driver,className,screenshot_name+"22");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Title is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Title not is available");	
		}
		
		//Verify Sort by Created
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Title']")).Click();
		LOGGER.info("Click on Sort By Button");
		
		if(Element.isElementPresent(driver, By.xpath(sortby_Created)))
		{
			LOGGER.info("Successful : The Sort By Created is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Created is available");
			
			driver.findElement(By.xpath(sortby_Created)).click();
			Thread.sleep(5000);
			
			String created_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(created_result.equals(SortByCreated))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByCreated);
				extent.log(LogStatus.INFO, "Current Results : " + created_result);
				LOGGER.info("Successful : The results are successfully sorted by Created.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Created.");
        		Element.takescreenshot(driver,className,screenshot_name+"23");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByCreated);
				extent.log(LogStatus.INFO, "Current Results : " + created_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Created.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Created.");
        		Element.takescreenshot(driver,className,screenshot_name+"24");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Created is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Created not is available");	
		}
		
		//Verify the Sort by Modified
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Created']")).Click();
		LOGGER.info("Click on Sort By Button");
		
		if(Element.isElementPresent(driver, By.xpath(sortby_Modified)))
		{
			LOGGER.info("Successful : The Sort By Modified is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Modified is available");
			
			driver.findElement(By.xpath(sortby_Modified)).click();
			Thread.sleep(5000);
			
			String modified_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(modified_result.equals(SortByModified))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByModified);
				extent.log(LogStatus.INFO, "Current Results : " + modified_result);
				LOGGER.info("Successful : The results are successfully sorted by Modified.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Modified.");
        		Element.takescreenshot(driver,className,screenshot_name+"25");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByModified);
				extent.log(LogStatus.INFO, "Current Results : " + modified_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Modified.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Modified.");
        		Element.takescreenshot(driver,className,screenshot_name+"26");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Modified is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Modified not is available");	
		}
		
		//Verify Sort By Creator
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Modified']")).Click();
		LOGGER.info("Click on Sort By Button");
		
		if(Element.isElementPresent(driver, By.xpath(sortby_Creator)))
		{
			LOGGER.info("Successful : The Sort By Creator is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Creator is available");
			
			driver.findElement(By.xpath(sortby_Creator)).click();
			Thread.sleep(5000);
			
			String creator_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(creator_result.equals(SortByCreator))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByCreator);
				extent.log(LogStatus.INFO, "Current Results : " + creator_result);
				LOGGER.info("Successful : The results are successfully sorted by Creator.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Creator.");
        		Element.takescreenshot(driver,className,screenshot_name+"27");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByCreator);
				extent.log(LogStatus.INFO, "Current Results : " + creator_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Creator.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Creator.");
        		Element.takescreenshot(driver,className,screenshot_name+"28");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Creator is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Creator not is available");	
		}
		
		//Verify Sort By Modifier
		new Link(driver, By.xpath("//div[@id='se-results']//following::span[text()='Creator']")).Click();
		LOGGER.info("Click on Sort By Button");
		
		if(Element.isElementPresent(driver, By.xpath(sortby_Modifier)))
		{
			LOGGER.info("Successful : The Sort By Modifier is available");
			extent.log(LogStatus.PASS, "Successful : The Sort By Modifier is available");
			
			driver.findElement(By.xpath(sortby_Modifier)).click();
			Thread.sleep(5000);
			
			String Modifier_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(Modifier_result.equals(SortByCreator))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByModifier);
				extent.log(LogStatus.INFO, "Current Results : " + Modifier_result);
				LOGGER.info("Successful : The results are successfully sorted by Modifier.");
        		extent.log(LogStatus.PASS, "Successful : The results are successfully sorted by Modifier.");
        		Element.takescreenshot(driver,className,screenshot_name+"29");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + SortByModifier);
				extent.log(LogStatus.INFO, "Current Results : " + Modifier_result);
				LOGGER.info("Unsuccessful : The results are not successfully sorted by Modifier.");
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are not successfully sorted by Modifier.");
        		Element.takescreenshot(driver,className,screenshot_name+"30");
			}		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Sort By Modifier is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Sort By Modifier not is available");	
		}
		TestCaseProperties.closeDriver(driver);	
	}
	
	@Test
	public void g_EntitySearch() throws InterruptedException, IOException
	{	
		a_logintoSenefy();
		
		LOGGER.info("Multiple Entity Search in Sensefy - Verify Filter By Functionality");
		extent.startTest("Multiple Entity Search in Sensefy - Verify Filter By Functionality");
			
		TextField searchField = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchField.clearText();
		Thread.sleep(3000);
				
		String test_element1 = searchTerm_3;
		
	    String[] alphabets1 = test_element1.split("");
	    for(String alphabet_1 : alphabets1)
	    {
	        System.out.println(alphabet_1);
	        searchField.enterText(alphabet_1);
	        Thread.sleep(3000);
	    }
	    
	    //Thread.sleep(5000);
	    
		String test_element_1 = "//div[@id='header']//div[1]/div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div//div//div//div//span//span[2]";
		
		if(Label.isElementPresent(driver, By.xpath(test_element_1)))
		{
			LOGGER.info("Successful : The 1st Entity "+searchTerm_3+" is available");
    		extent.log(LogStatus.PASS, "Successful :The 1st Entity "+searchTerm_3+" is available.");
    		Element.takescreenshot(driver,className,screenshot_name+"31");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 1st Entity "+searchTerm_3+" is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 1st Entity "+searchTerm_3+" is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"32");
		}
		 
		driver.findElement(By.xpath(test_element_1)).click();
		
		String test_element2= searchTerm_4;
		
		String[] alphabets2 = test_element2.split("");
		for(String alphabet_2 : alphabets2)
		{
			System.out.println(alphabet_2);
			searchField.enterText(alphabet_2);
			Thread.sleep(3000);
		}
		
		//Thread.sleep(3000);
		String test_element_2 = "//div[@id='header']//div[1]//div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div[10]//div//div//div//span//span";
//		String test_element_2 = "//div[@id='header']//div[1]//div//div//div//div[2]//ul[2]//li//div//div[4]//div[3]//div[7]//div//div//div//span//span";
		
		if(Label.isElementPresent(driver, By.xpath(test_element_2)))
		{
			LOGGER.info("Successful : The 2nd Entity "+searchTerm_4+" is available");
    		extent.log(LogStatus.PASS, "Successful :The 2nd Entity "+searchTerm_4+" is available");
    		Element.takescreenshot(driver,className,screenshot_name+"33");
		}
		else
		{
			LOGGER.info("Unsuccessful : The 2nd Entity "+searchTerm_4+" is not available");
    		extent.log(LogStatus.FAIL, "Unsuccessful :The 2nd Entity "+searchTerm_4+" is not available.");
    		Element.takescreenshot(driver,className,screenshot_name+"34");
		}
		
		driver.findElement(By.xpath(test_element_2)).click();
		driver.findElement(By.xpath("//div[@class='ui yellow button ser-btn']")).click();
		
		LOGGER.info("click on the Search");
		Thread.sleep(3000);
		
		//Filter by Creator
		String filter_admin ="//div[@class='ui segment']//div//div[1]//ul//li//span//span//span[text()='admin']";
		
		if(Element.isElementPresent(driver, By.xpath(filter_admin)))
		{
			LOGGER.info("Successful : The Filter By Creator " +driver.findElement(By.xpath(filter_admin)).getText().toString()+" is available");
			extent.log(LogStatus.PASS, "Successful : The Filter By Creator " +driver.findElement(By.xpath(filter_admin)).getText().toString()+" is available");
			driver.findElement(By.xpath(filter_admin)).click();
			
			
			String filter_admin_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(filter_admin_result.equals(FilterByCreator))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByCreator);
				extent.log(LogStatus.INFO, "Current Results : " + filter_admin_result);
				LOGGER.info("Successful : The results are successfully Filtered by Creator "+driver.findElement(By.xpath(filter_admin)).getText().toString());
        		extent.log(LogStatus.PASS, "Successful : The results are successfully Filtered by Creator "+driver.findElement(By.xpath(filter_admin)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"35");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByCreator);
				extent.log(LogStatus.INFO, "Current Results : " + filter_admin_result);
				LOGGER.info("Unsuccessful : The results are not successfully Filtered by Creator "+driver.findElement(By.xpath(filter_admin)).getText().toString());
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are  notsuccessfully Filtered by Creator "+driver.findElement(By.xpath(filter_admin)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"36");
			}			
		}
		else
		{
			LOGGER.info("Unsuccessful : The Filter By Creator " +driver.findElement(By.xpath(filter_admin)).getText().toString()+" is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Filter By Creator " +driver.findElement(By.xpath(filter_admin)).getText().toString()+" is not available");	
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath(filter_admin)).click();
		//div[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[1]//ul//li//span[1]//span//li[2]
		//TestCaseProperties.closeDriver(driver);	
		System.out.println("okkkkk");
		
		Thread.sleep(3000);
		//Filter by Document type
		String fiter_WAVAudio = ".//*[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[2]/ul/li[1]/span[1]/span//span[text()='WAV Audio']";
		
		if(Element.isElementPresent(driver, By.xpath(fiter_WAVAudio)))
		{
			LOGGER.info("Successful : The Filter By Document Type " +driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString()+" is available");
			extent.log(LogStatus.PASS, "Successful : The Filter By Document Type " +driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString()+" is available");
			driver.findElement(By.xpath(fiter_WAVAudio)).click();
			
			String filter_WAVAudio_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			
			if(filter_WAVAudio_result.equals(FilterByDocumentType1))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByDocumentType1);
				extent.log(LogStatus.INFO, "Current Results : " + filter_WAVAudio_result);
				LOGGER.info("Successful : The results are successfully Filtered by Document Type "+driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString());
        		extent.log(LogStatus.PASS, "Successful : The results are successfully Filtered by Document Type "+driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"37");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByDocumentType1);
				extent.log(LogStatus.INFO, "Current Results : " + filter_WAVAudio_result);
				LOGGER.info("Unsuccessful : The results are not successfully Filtered by Document Type "+driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString());
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are  notsuccessfully Filtered by Document Type "+driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"38");
			}			
		}
		else
		{
			LOGGER.info("Unsuccessful : The Filter By Document Type " +driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString()+" is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Filter By Document Type " +driver.findElement(By.xpath(fiter_WAVAudio)).getText().toString()+" is not available");	
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath(fiter_WAVAudio)).click();
		Thread.sleep(3000);
		
		//filter by Document Type
		String filter_MPEG4 = ".//*[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[2]/ul/li[2]/span[1]/span";
		
		if(Element.isElementPresent(driver, By.xpath(filter_MPEG4)))
		{
			LOGGER.info("Successful : The Filter By Document Type " +driver.findElement(By.xpath(filter_MPEG4)).getText().toString()+" is available");
			extent.log(LogStatus.PASS, "Successful : The Filter By Document Type " +driver.findElement(By.xpath(filter_MPEG4)).getText().toString()+" is available");
			driver.findElement(By.xpath(filter_MPEG4)).click();
			Thread.sleep(3000);
			String filter_MPEG4_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			System.out.println("okkkkk");
			if(filter_MPEG4_result.equals(FilterByDocumentType2))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByDocumentType2);
				extent.log(LogStatus.INFO, "Current Results : " + filter_MPEG4_result);
				LOGGER.info("Successful : The results are successfully Filtered by Document Type "+driver.findElement(By.xpath(filter_MPEG4)).getText().toString());
        		extent.log(LogStatus.PASS, "Successful : The results are successfully Filtered by Document Type "+driver.findElement(By.xpath(filter_MPEG4)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"39");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByDocumentType2);
				extent.log(LogStatus.INFO, "Current Results : " + filter_MPEG4_result);
				LOGGER.info("Unsuccessful : The results are not successfully Filtered by Document Type "+driver.findElement(By.xpath(filter_MPEG4)).getText().toString());
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are  notsuccessfully Filtered by Document Type "+driver.findElement(By.xpath(filter_MPEG4)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"40");
			}	
			System.out.println("okkkkk");		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Filter By Document Type " +driver.findElement(By.xpath(filter_MPEG4)).getText().toString()+" is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Filter By Document Type " +driver.findElement(By.xpath(filter_MPEG4)).getText().toString()+" is not available");	
		}
		Thread.sleep(3000);
		
		driver.findElement(By.xpath(".//*[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[2]/ul/li[1]/span[1]/span")).click();
		System.out.println("okkkkk");
		Thread.sleep(3000);
		
		//Filter by Language
		String filter_English = ".//*[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[3]/ul/li/span[1]/span/span";
		
		if(Element.isElementPresent(driver, By.xpath(filter_English)))
		{
			LOGGER.info("Successful : The Filter By Language " +driver.findElement(By.xpath(filter_English)).getText().toString()+" is available");
			extent.log(LogStatus.PASS, "Successful : The Filter By Language " +driver.findElement(By.xpath(filter_English)).getText().toString()+" is available");
			driver.findElement(By.xpath(filter_English)).click();
			Thread.sleep(3000);
			
			String filter_English_result = driver.findElement(By.xpath("//div[@id='se-results']/div[4]/div[1]/div[2]/ng-switch/a/span")).getText().toString();
			System.out.println("okkkkk");
			if(filter_English_result.equals(FilterByDocumentType2))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByLanguage);
				extent.log(LogStatus.INFO, "Current Results : " + filter_English_result);
				LOGGER.info("Successful : The results are successfully Filtered by Language "+driver.findElement(By.xpath(filter_English)).getText().toString());
        		extent.log(LogStatus.PASS, "Successful : The results are successfully Filtered by Language "+driver.findElement(By.xpath(filter_English)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"41");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + FilterByLanguage);
				extent.log(LogStatus.INFO, "Current Results : " + filter_English_result);
				LOGGER.info("Unsuccessful : The results are not successfully Filtered by Language "+driver.findElement(By.xpath(filter_English)).getText().toString());
        		extent.log(LogStatus.FAIL, "Unsuccessful : The results are  notsuccessfully Filtered by Language "+driver.findElement(By.xpath(filter_English)).getText().toString());
        		Element.takescreenshot(driver,className,screenshot_name+"42");
			}	
			System.out.println("okkkkk");		
		}
		else
		{
			LOGGER.info("Unsuccessful : The Filter By Language " +driver.findElement(By.xpath(filter_English)).getText().toString()+" is not available");
			extent.log(LogStatus.FAIL, "Unsuccessful : The Filter By Language " +driver.findElement(By.xpath(filter_English)).getText().toString()+" is not available");	
		}
		Thread.sleep(3000);
		
		driver.findElement(By.xpath(".//*[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[3]/ul/li/span[1]/span")).click();
		System.out.println("okkkkk");
		Thread.sleep(3000);
		
	}
}
	

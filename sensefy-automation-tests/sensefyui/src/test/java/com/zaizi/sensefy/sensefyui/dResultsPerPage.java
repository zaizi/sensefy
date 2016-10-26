package com.zaizi.sensefy.sensefyui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;
/*
 * Test results per page
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class dResultsPerPage {
	public static final Logger LOGGER = LogManager.getLogger(dResultsPerPage.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(dResultsPerPage.class);
		
	/**
	 * defining class name
	 */
	public static String className = dResultsPerPage.class.getSimpleName();
	
	public String searchTerm="//input[@id='searchTerm']";
	
	static WebDriver driver;
	
	private String username;
    private String password;
    public String searchWord="Ephesoft Marketing";
    public String screenshot_name;
	
	public dResultsPerPage(String username, String password,String screenshot_name)
    {
        this.username = username;
        this.password = password;
        this.screenshot_name = screenshot_name;
    }
	
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
		Element.reportInitial(driver, className);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation");
        extent.config().reportHeadline("Search Page Testing");
    }
    

	@Parameters()
	public static Iterable<Object[]> data() throws IterableException
	{
		LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "ResultsPerPageTest");
	    return TestCaseValues.documentLibraryTestValues("LoginTest");
	}
	
	//Test Ten results per page
	@Test
    public void a_tenresultsperpage() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify 10 ResultsPerPage Test");
    	extent.startTest("Verify 10 ResultsPerPage Test");
    	try
    	{
    		int actualResults;
        	int expectedResults;
            driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            Thread.sleep(2000);
            SearchPage searchPage=new SearchPage(driver);
            searchPage.searchATerm(searchWord);
            Thread.sleep(10000);
            //10
            expectedResults=10;
            actualResults=searchPage.countResults();
            System.out.println("Expected: "+ expectedResults+"  |  Actual: "+actualResults);
            Assert.assertEquals(expectedResults, actualResults);
            LOGGER.info("10ResultsPerPage Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "10 ResultsPerPage Verified Successfully");
    		extent.log(LogStatus.PASS, "10 ResultsPerPage Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"1");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "10 ResultsPerPage Verification Failed");
    		LOGGER.error("10 ResultsPerPage Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"2");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	
	//Test 20 results per page
	@Test
    public void b_twentyresultsperpage() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify 20 ResultsPerPage Test");
    	extent.startTest("Verify 20 ResultsPerPage Test");
    	try
    	{
    		int actualResults;
        	int expectedResults;
            driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            Thread.sleep(2000);
            SearchPage searchPage=new SearchPage(driver);
            searchPage.searchATerm(searchWord);
            Thread.sleep(10000);
            //20
            expectedResults=20;
            searchPage.resultsPerPage(expectedResults);
            Thread.sleep(10000);
            actualResults=searchPage.countResults();
            System.out.println("Expected: "+ expectedResults+"  |  Actual: "+actualResults);
            Assert.assertEquals(expectedResults, actualResults);
            LOGGER.info("20ResultsPerPage Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "20 ResultsPerPage Verified Successfully");
    		extent.log(LogStatus.PASS, "20 ResultsPerPage Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"3");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "20 ResultsPerPage Verification Failed");
    		LOGGER.error("20 ResultsPerPage Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"4");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	
	//Test 50 results per page
	@Test
    public void c_fiftyresultsperpage() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify 50ResultsPerPage Test");
    	extent.startTest("Verify 50ResultsPerPage Test");
    	try
    	{
    		int actualResults;
        	int expectedResults;
            driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            Thread.sleep(2000);
            SearchPage searchPage=new SearchPage(driver);
            searchPage.searchATerm(searchWord);
            Thread.sleep(10000);
            //50
            expectedResults=50;
            searchPage.resultsPerPage(expectedResults);
            Thread.sleep(10000);
            actualResults=searchPage.countResults();
            System.out.println("Expected: "+ expectedResults+"  |  Actual: "+actualResults);
            Assert.assertEquals(expectedResults, actualResults);
            LOGGER.info("50ResultsPerPage Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "50 ResultsPerPage Verified Successfully");
    		extent.log(LogStatus.PASS, "50 ResultsPerPage Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"5");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "50 ResultsPerPage Verification Failed");
    		LOGGER.error("50 ResultsPerPage Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"6");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	
	//Test 100 results per page
	@Test
    public void d_hundradresultsperpage() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify 100 ResultsPerPage Test");
    	extent.startTest("Verify 100 ResultsPerPage Test");
    	try
    	{
    		int actualResults;
        	int expectedResults;
            driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            Thread.sleep(2000);
            SearchPage searchPage=new SearchPage(driver);
            searchPage.searchATerm(searchWord);
            Thread.sleep(10000);
            //100
            expectedResults=100;
            searchPage.resultsPerPage(expectedResults);
            Thread.sleep(10000);
            Thread.sleep(10000);
            actualResults=searchPage.countResults();
            System.out.println("Expected: "+ expectedResults+"  |  Actual: "+actualResults);
            Assert.assertEquals(expectedResults, actualResults);
            LOGGER.info("100ResultsPerPage Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "100 ResultsPerPage Verified Successfully");
    		extent.log(LogStatus.PASS, "100 ResultsPerPage Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"7");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "100 ResultsPerPage Verification Failed");
    		LOGGER.error("100 esultsPerPage Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"8");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    @After
    public void Teardown() {
        driver.quit();
       
    }

}

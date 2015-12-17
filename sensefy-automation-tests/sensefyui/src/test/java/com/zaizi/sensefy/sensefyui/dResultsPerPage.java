package com.zaizi.sensefy.sensefyui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class dResultsPerPage {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
	public String searchTerm="//input[@id='searchTerm']";
	
	static WebDriver driver;
	
	private String username;
    private String password;
    private String BrowserName;
    public String searchWord="Ephesoft Marketing";
	
	public dResultsPerPage(String username, String password, String BrowserName)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
    }
	
	@BeforeClass
    public static void beforeClass() {
        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
        extent.config().documentTitle("SensefyUI Report");
        extent.config().reportTitle("SensefyUI Smoke Test");
        extent.config().reportHeadline("Login Testing");
    }
    

	@Parameters()
	public static Iterable<Object[]> data() throws IterableException
	{
	    System.out.println("preparing Results per Page ..");
	    return TestCaseValues.documentLibraryTestValues("LoginTest");
	}
	
	@Test
    public void a_tenresultsperpage() throws InterruptedException
    {
    	int actualResults;
    	int expectedResults;
    	extent.startTest("Test resuts per page");
        System.out.println("Running Results per page test");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
        
    }
	
	@Test
    public void b_twentyresultsperpage() throws InterruptedException
    {
    	int actualResults;
    	int expectedResults;
    	extent.startTest("Test resuts per page");
        System.out.println("Running Results per page test");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
        
    }
	
	@Test
    public void c_fiftyresultsperpage() throws InterruptedException
    {
    	int actualResults;
    	int expectedResults;
    	extent.startTest("Test resuts per page");
        System.out.println("Running Results per page test");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
	@Test
    public void d_hundradresultsperpage() throws InterruptedException
    {
    	int actualResults;
    	int expectedResults;
    	extent.startTest("Test resuts per page");
        System.out.println("Running Results per page test");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @After
    public void Teardown() {
        driver.quit();
       
    }

}

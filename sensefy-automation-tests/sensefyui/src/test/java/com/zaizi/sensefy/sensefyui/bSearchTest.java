package com.zaizi.sensefy.sensefyui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
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
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;
/*
 * Test Search Scenarios
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class bSearchTest {
	public static final Logger LOGGER = LogManager.getLogger(bSearchTest.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(bSearchTest.class);
	
	//xpath
    public String searchTerm="//input[@id='searchTerm']";
    private String showResult="//div[@id='se-results']/div[4]/div[1]/div/ng-switch/a/span";
    private String nodoc="//div[@id='se-results']/div[4]/div/div[1]";
    private static String numberOfResults="//div[@id='se-results']/div[1]/div/div/div[1]/span";
    private static String dataSourceSecond="//div[@id='ds-tabs']/a[2]/span";
    private static String dataSourceThird="//div[@id='ds-tabs']/a[3]/span";
    private String didyou="//div[@id='se-results']/div[4]/div/div[2]/span[1]";
    private static String suggest="//div[@id='se-results']/div[4]/div/div[2]/span[2]";
    private String didYouMean="Did you mean:";
    
    private String noDocFound="No document found.";
    
    public String searchWord="backup.js.sample";
	
	private String username;
    private String password;
    private String nodocword;
    private String didumean;
    private String closeword;
    
    static WebDriver driver;
    
	public bSearchTest(String username, String password, String nodocword, String didumean, String closeword)
    {
        this.username = username;
        this.password = password;
        this.nodocword=nodocword;
        this.didumean=didumean;
        this.closeword=closeword;
    }
	
	@BeforeClass
    public static void beforeClass() {
        //extent.init("/Users/deranthika/Desktop/myreport1.html", true);
		extent.init("logs/sensefy.html", false);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation");
        extent.config().reportHeadline("Search Page Testing");
    }

	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "SearchTest");
        return TestCaseValues.documentLibraryTestValues("SearchTest");
    }
  
    //Test Exact keyword search
	@Test
	public void a_searchTerm() throws InterruptedException
    {
		LOGGER.info("Running Verify Exact Keyword search Test");
    	extent.startTest("Verify Footer Test");
    	
    	driver = TestCaseProperties.getWebDriverForSearch();
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
            
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
            
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
            
        WebElement searchResult=driver.findElement(By.xpath(showResult));
        if(searchResult.getText().equalsIgnoreCase(searchWord))
        {
           	LOGGER.info("Exact Keyword search Verified Successfully");
        	LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Exact Keyword search Verified Successfully");
        	extent.log(LogStatus.PASS, "Exact Keyword search Verified Successfully");
        }
        else
        {
           	extent.log(LogStatus.FAIL, "Exact Keyword search Verification Failed");
           	LOGGER.error("Exact Keyword search Verification Failed");
        }
    
	
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	
	//Search a document which not exist in data source
	@Test
    public void b_zeropsearch() throws InterruptedException
    {
		LOGGER.info("Running Verify not exist document search Test");
    	extent.startTest("Verify not exist document Test");

  		driver = TestCaseProperties.getWebDriverForSearch();
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
            
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
            
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(nodocword);
        Thread.sleep(5000);
           
        WebElement searchResult=driver.findElement(By.xpath(nodoc));
        if(!searchResult.getText().equals(noDocFound))
        {
          	LOGGER.info("not exist document search Verified Successfully");
       		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "not exist document search Verified Successfully");
       		extent.log(LogStatus.PASS, "not exist document search Verified Successfully");
        }
        else
        {
           	extent.log(LogStatus.FAIL, "not exist document search Verification Failed");
       		LOGGER.error("not exist document search Verification Failed");
        }
   	
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	/*
	//verify number of results between two data sources
    @Test
    public void c_datasource() throws InterruptedException
    {
    	LOGGER.info("Running Verify data source search Test");
    	extent.startTest("Verify not data source Test");

    	driver = TestCaseProperties.getWebDriverForSearch();
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        String results=driver.findElement(By.xpath(numberOfResults)).getText();
        String val[];
        int a=0,b=0,c=0;
        for(String retval:results.split(" ", 5))
        {
          	a++;
           	if(a==5)
           	{
           		val=retval.split("\\.");
           		System.out.println("Count of All Data Sources: "+ val[0]);
           		a=Integer.parseInt(val[0]);
           	}
        }
            
        driver.findElement(By.xpath(dataSourceSecond)).click();
        Thread.sleep(3000);
        results=driver.findElement(By.xpath(numberOfResults)).getText();

        for(String retval:results.split(" ", 5))
        {
           	b++;
           	if(b==5)
         	{
           		val=retval.split("\\.");
           		System.out.println("Count of 1st Data Sources: "+ val[0]);
           		b=Integer.parseInt(val[0]);
           	}
        }
        Thread.sleep(5000);
        driver.findElement(By.xpath(dataSourceThird)).click();
        Thread.sleep(5000);
        results=driver.findElement(By.xpath(numberOfResults)).getText();

        for(String retval:results.split(" ", 5))
        {
           	c++;
           	if(c==5)
          	{
          		val=retval.split("\\.");
           		System.out.println("Count of 2nd Data Sources: "+ val[0]);
           		c=Integer.parseInt(val[0]);
           	}
        }
        if((a<b)||(a<c))
        {
           	Assert.assertNotEquals(c, a);
        }
        else
        {
           	assert true;
        }
        LOGGER.info("data source search Verified Successfully");
    	
        LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "data source search Verified Successfully");
    	
        extent.log(LogStatus.PASS, "data source search Verified Successfully");
    	
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }   */
}

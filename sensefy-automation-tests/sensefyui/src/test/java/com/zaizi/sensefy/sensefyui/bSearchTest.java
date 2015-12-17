package com.zaizi.sensefy.sensefyui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class bSearchTest {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
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
    private String BrowserName;
    private String nodocword;
    private String didumean;
    private String closeword;
    
    private static final String TEST_TEST_PASSED = "Test case passed!";
    private static final String TEST_TEST_FAILED = "Test case failed!";   
    
	
    static WebDriver driver;
    
    
	public bSearchTest(String username, String password, String BrowserName, String nodocword, String didumean, String closeword)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
        this.nodocword=nodocword;
        this.didumean=didumean;
        this.closeword=closeword;
    }
	
	@BeforeClass
    public static void beforeClass() {
        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
        extent.config().documentTitle("SensefyUI Report");
        extent.config().reportTitle("SensefyUI Smoke Test");
        extent.config().reportHeadline("Search Testing");
    }
    

	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
        System.out.println("preparing SearchTest ..");
        return TestCaseValues.documentLibraryTestValues("SearchTest");
    }
  
	@Test
	public void a_searchTerm() throws InterruptedException
    {
    	extent.startTest("Search Exact Keyword Test");
        System.out.println("Running Search Exact Keyword Test");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        	System.out.println(TEST_TEST_PASSED);
        }
        else
        {
        	System.out.println(TEST_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
	@Test
    public void b_zeropsearch() throws InterruptedException
    {
    	extent.startTest("Search non existing document");
        System.out.println("Running non-existing keyword search");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(nodocword);
        Thread.sleep(5000);
        WebElement searchResult=driver.findElement(By.xpath(nodoc));
        if(searchResult.getText().equals(noDocFound))
        {
        	System.out.println(TEST_TEST_PASSED);
        }
        else
        {
        	System.out.println(TEST_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
    //@Test
    public void c_datasource() throws InterruptedException
    {
    	extent.startTest("Navigate through data sources");
        System.out.println("Running Navigate through data sources");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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

        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
	@Test
    public void d_didumean() throws InterruptedException
    {
    	extent.startTest("Search non existing document but close to existing one");
        System.out.println("Running non-existing keyword search but close to existing one");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(didumean);
        Thread.sleep(5000);
        WebElement searchResult=driver.findElement(By.xpath(nodoc));
        if(searchResult.getText().equals(noDocFound))
        {
        	searchResult=driver.findElement(By.xpath(didyou));
        	if(searchResult.getText().equals(didYouMean))
        	{
        		System.out.println(TEST_TEST_PASSED);
        	}
        	else
            {
            	System.out.println(TEST_TEST_FAILED);
            }
        }
        else
        {
        	System.out.println(TEST_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void e_didumeanClick() throws InterruptedException
    {
    	extent.startTest("Search a document which close to search term");
        System.out.println("Running Search a document which close to search term");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(closeword);
        Thread.sleep(5000);
        WebElement searchResult=driver.findElement(By.xpath(nodoc));
        if(searchResult.getText().equals(noDocFound))
        {
        	WebElement searchSuggestion=driver.findElement(By.xpath(suggest));
        	String searchSuggest=searchSuggestion.getText();
        	searchSuggestion.click();
        	Thread.sleep(3000);
        	String SearchResult=driver.findElement(By.xpath(showResult)).getText();
        	for(String retval:SearchResult.split(" "))
        	{
        		System.out.println(retval);
        		if(retval.equalsIgnoreCase(searchSuggest))
        		{
        			System.out.println(TEST_TEST_PASSED);
        			break;
        		}	
        	}    
        }
        else
        {
        	System.out.println(TEST_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    

}

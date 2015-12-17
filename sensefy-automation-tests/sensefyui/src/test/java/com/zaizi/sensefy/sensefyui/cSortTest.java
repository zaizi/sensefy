package com.zaizi.sensefy.sensefyui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
public class cSortTest {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
	private String username;
    private String password;
    private String BrowserName;
    public String searchWord;
    
    public String searchTerm="//input[@id='searchTerm']";
    
    
    
    private static final String TEST_TEST_PASSED = "Test case passed!";
    private static final String TEST_TEST_FAILED = "Test case failed!";
    
    static WebDriver driver;
    
    public cSortTest(String username, String password, String BrowserName, String searchWord)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
        this.searchWord=searchWord;
    }
    
    @BeforeClass
    public static void beforeClass() {
        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
        extent.config().documentTitle("SensefyUI Report");
        extent.config().reportTitle("SensefyUI Smoke Test");
        extent.config().reportHeadline("Sort Testing");
    }
    

	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
        System.out.println("preparing SortTest ..");
        return TestCaseValues.documentLibraryTestValues("SortTest");
    }
    
    @Test
    public void a_sortbyCreator() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Creator");
        searchPage.sortbynames("Creator");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
    
    @Test
    public void b_sortbyName() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Name");
        searchPage.sortbynames("Name");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void c_sortbyCreated() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Created");
        searchPage.sortbynames("Created");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
	
    
    @Test
    public void d_sortbyModifier() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Modifier");
        searchPage.sortbynames("Modifier");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }

    @Test
    public void e_sortbyModified() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Modified");
        searchPage.sortbynames("Modified");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void f_sortbyRelevance() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Relevance");
        searchPage.sortbynames("Relevance");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void g_sortbyTitle() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        Thread.sleep(2000);
        SearchPage searchPage=new SearchPage(driver);
        searchPage.searchATerm(searchWord);
        Thread.sleep(5000);
        searchPage.selectASort("Title");
        searchPage.sortbynames("Title");
        Thread.sleep(5000);
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();

    }
}

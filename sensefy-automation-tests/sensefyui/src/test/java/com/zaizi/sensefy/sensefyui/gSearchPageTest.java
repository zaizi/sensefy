package com.zaizi.sensefy.sensefyui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
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
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class gSearchPageTest {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
	private String username;
    private String password;
    private String BrowserName;
    

    private static final String TEST_TEST_PASSED = "Test case passed!";
    private static final String TEST_TEST_FAILED = "Test case failed!";

    static WebDriver driver;
	public gSearchPageTest(String username, String password, String BrowserName)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
    }
	
	@BeforeClass
    public static void beforeClass() {
        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation");
        extent.config().reportHeadline("Search Page Testing");

    }
    

	@Parameters()
	public static Iterable<Object[]> data() throws IterableException
	{
	    System.out.println("preparing Footer Test ..");
	    return TestCaseValues.documentLibraryTestValues("LoginTest");
	}
	
	//Test Footer note
	@Test
    public void verifyFooter() throws InterruptedException
    {
    	extent.startTest("Verify Footer Test");
        System.out.println("Running Verify Footer Test");
        try
        {
        	driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
        	SearchPage sp=new SearchPage(driver);
        	Boolean val=sp.footerNote();
        	if(val==true)
        	{
        		System.out.println(TEST_TEST_PASSED);
        		extent.log(LogStatus.PASS, "Footer Verified Successfully");
        	}
        	else
        	{
        		System.out.println(TEST_TEST_FAILED);
        		extent.log(LogStatus.FAIL, "Footer Verified Failed");
        	}
        	
        }
        catch(Exception e)
        {
        	extent.log(LogStatus.FAIL, "Footer Verified Failed");
        }
        
    	TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @After
    public void Teardown() {
        driver.quit();  
    }
}

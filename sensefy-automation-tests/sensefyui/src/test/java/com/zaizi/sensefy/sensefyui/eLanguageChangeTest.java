package com.zaizi.sensefy.sensefyui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;
/*
 * Change Language test
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class eLanguageChangeTest 
{
	public static final Logger LOGGER = LogManager.getLogger(eLanguageChangeTest.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(eLanguageChangeTest.class);
	
	/**
	 * defining class name
	 */
	public static String className = eLanguageChangeTest.class.getSimpleName();
	
	static WebDriver driver;
	
	private String username;
    private String password;
    public String screenshot_name;
        
       
    public eLanguageChangeTest(String username, String password, String screenshot_name)
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
		LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "SearchPageTest");
	    return TestCaseValues.documentLibraryTestValues("LoginTest");
	}
	
	//Change language to Spanish
	@Test
    public void a_changeLanguageToSpanish() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify changeToSpanish Test");
    	extent.startTest("Verify changeToSpanish Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            SearchPage sp=new SearchPage(driver);
            sp.changeLanguage();
            LOGGER.info("changeToSpanish Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "changeToSpanish Verified Successfully");
    		extent.log(LogStatus.PASS, "changeToSpanish Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"1");
    	}
        catch(Exception e)
    	{
        	extent.log(LogStatus.FAIL, "changeToSpanish Verification Failed");
    		LOGGER.error("changeToSpanish Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"2");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
	
	//Change langauge to Spanish and English
	@Test
    public void b_changeLanguageToEnglish() throws InterruptedException, IOException
    {
		LOGGER.info("Running Verify changeToEnglish Test");
    	extent.startTest("Verify changeToEnglish Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            SearchPage sp=new SearchPage(driver);
            sp.changeLanguage();
            sp.changeLanguage();
            LOGGER.info("changeToEnglish Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "changeToEnglish Verified Successfully");
    		extent.log(LogStatus.PASS, "changeToEnglish Verified Successfully");
    		Element.takescreenshot(driver,className,screenshot_name+"3");
    	}
        catch(Exception e)
    	{
        	extent.log(LogStatus.FAIL, "changeToEnglish Verification Failed");
    		LOGGER.error("changeToEnglish Verification Failed");
    		Element.takescreenshot(driver,className,screenshot_name+"4");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    
    @After
    public void Teardown() {
        driver.quit();  
    }

}

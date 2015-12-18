package com.zaizi.sensefy.sensefyui;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.set.SynchronizedSet;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class aLoginTest {
	public static final Logger LOGGER = LogManager.getLogger(aLoginTest.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(aLoginTest.class);
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	private String loginURL="http://sensefyqa.zaizicloud.net/auth/login";
	
	private static final String TEST_TEST_PASSED = "Test case passed!";
    private static final String TEST_TEST_FAILED = "Test case failed!";
	
	private String username;
    private String password;
        
    //xpaths
    public String searchTerm="//input[@id='searchTerm']";
    private String usernameField = "//input[@id='username']";
    private String loginErrorURL="http://sensefyqa.zaizicloud.net/auth/login?error";
    
    static WebDriver driver;
	public aLoginTest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
	
	@BeforeClass
    public static void beforeClass() {
        //extent.init("/Users/deranthika/Desktop/myreport1.html", true);
		extent.init("logs/sensefy.html", true);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation");
        extent.config().reportHeadline("Search Page Testing");
    } 
	
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "LoginTest");
        return TestCaseValues.documentLibraryTestValues("LoginTest");
    }
    
    //Verify Sensefy URL
    @Test
    public void a_sensefyURL()
    {
    	LOGGER.info("Running Verify SensefyURL Test");
    	extent.startTest("Verify SensefyURL Test");
    	try
    	{
    		extent.startTest("Verify SensefyURL");
            System.out.println("Running Test for Sensefy URL");
            driver = TestCaseProperties.getWebDriverForSearch();
            if(driver.getCurrentUrl().equals(loginURL))
            {
        		LOGGER.info("SensefyURL Verified Successfully");
        		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "SensefyURL Verified Successfully");
        		extent.log(LogStatus.PASS, "SensefyURL Verified Successfully");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "SensefyURL Verification Failed");
        		LOGGER.error("SensefyURL Verification Failed");
            }
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "SensefyURL Verification Failed");
    		LOGGER.error("SensefyURL Verification Failed");
    	}
    	
    }
    
    //Valid login scenario
    @Test
    public void b_validLog() throws InterruptedException
    {
    	LOGGER.info("Running Verify Valid logging Test");
    	extent.startTest("Verify Valid logging Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            LOGGER.info("Valid logging Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Valid logging Verified Successfully");
    		extent.log(LogStatus.PASS, "Valid logging Verified Successfully");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "Valid logging Verification Failed");
    		LOGGER.error("Valid logging Verification Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    //Trying to log without entering username and password
    @Test
    public void c_emptyLogin() throws InterruptedException
    {
    	LOGGER.info("Running Verify empty logging Test");
    	extent.startTest("Verify empty logging Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin("", "");
            Thread.sleep(2000);
            loginPage.checkElementPresent(usernameField);
            LOGGER.info("empty logging Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "empty logging Verified Successfully");
    		extent.log(LogStatus.PASS, "empty logging Verified Successfully");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "empty logging Verification Failed");
    		LOGGER.error("empty logging Verification Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    //Invalid username and password scenario
    @Test
    public void d_invalidLogin() throws InterruptedException
    {
    	LOGGER.info("Running Verify Invalid logging Test");
    	extent.startTest("Verify Invalid logging Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin("abcd", "abcd");
            Thread.sleep(2000);
            String url=driver.getCurrentUrl();
            if(url.equals(loginErrorURL))
            {
            	LOGGER.info("Invalid logging Verified Successfully");
        		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Invalid logging Verified Successfully");
        		extent.log(LogStatus.PASS, "Invalid logging Verified Successfully");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "Invalid logging Verification Failed");
        		LOGGER.error("Invalid logging Verification Failed");
            }
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "Invalid logging Verification Failed");
    		LOGGER.error("Invalid logging Verification Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    //Loging out scenario
    @Test
    public void e_loginout() throws InterruptedException
    {
    	LOGGER.info("Running Verify Logingout Test");
    	extent.startTest("Verify Logingout Test");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.logout();
            LOGGER.info("Logingout Verified Successfully");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Logingout Verified Successfully");
    		extent.log(LogStatus.PASS, "Logingout Verified Successfully");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "Logingout Verification Failed");
    		LOGGER.error("Logingout Verification Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");
    }
    
    
    @After
    public void Teardown() {
        driver.quit();
       
    }
    
    
    private static Node getProperty(String propertyName) throws ParserConfigurationException, SAXException, IOException
	 {
	     File testValues = new File(TEST_CASE_PROPERTIES_XML);
	     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	     Document doc = dBuilder.parse(testValues);
	     doc.getDocumentElement().normalize();
	     NodeList nodes = doc.getElementsByTagName(propertyName);
	     Node node = nodes.item(0);
	     NodeList testdata = node.getChildNodes();
	     return testdata.item(0);
	 }
	
	public static String getPropertyValue(String propertyName)
	 {
	     String result = null;
	     try
	     {
	         Node serverUrl = getProperty(propertyName);
	         result = serverUrl.getNodeValue();
	     }
	     catch (ParserConfigurationException e)
	     {
	         LOGGER.error("ParserConfigurationException", e);
	     }
	     catch (SAXException e)
	     {
	         LOGGER.error("SAXException", e);
	     }
	     catch (IOException e)
	     {
	         LOGGER.error("IOException", e);
	     }
	     return result;
	 }
	

}

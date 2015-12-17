package com.zaizi.sensefy.sensefyui;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.set.SynchronizedSet;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class aLoginTest {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	private String loginURL="http://sensefyqa.zaizicloud.net/auth/login";
	
	private static final String TEST_TEST_PASSED = "Test case passed!";
    private static final String TEST_TEST_FAILED = "Test case failed!";
	
	private String username;
    private String password;
    private String BrowserName;
    
    //xpaths
    public String searchTerm="//input[@id='searchTerm']";
    private String usernameField = "//input[@id='username']";
    private String loginErrorURL="http://sensefyqa.zaizicloud.net/auth/login?error";
    
    static WebDriver driver;
	public aLoginTest(String username, String password, String BrowserName)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
    }
	
	@BeforeClass
    public static void beforeClass() 
	{
        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
        extent.config().documentTitle("SensefyUI Report");
        extent.config().reportTitle("SensefyUI Smoke Test");
        extent.config().reportHeadline("Login Testing");
    }
	
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
        System.out.println("Preparing LoginTest ..");
        return TestCaseValues.documentLibraryTestValues("LoginTest");
    }
    
    @Test
    public void a_sensefyURL()
    {
    	extent.startTest("Verify SensefyURL");
        System.out.println("Running Test for Sensefy URL");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        if(driver.getCurrentUrl().equals(loginURL))
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
    public void b_validLog() throws InterruptedException
    {
    	extent.startTest("Login Test");
        System.out.println("Running valid user login");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.checkElementPresent(searchTerm);
        TestCaseProperties.closeDriver(driver);
        System.out.println(TEST_TEST_PASSED); 
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void c_emptyLogin() throws InterruptedException
    {
    	extent.startTest("Empty Login Test");
        System.out.println("Running login to without empty credentials");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin("", "");
        Thread.sleep(2000);
        loginPage.checkElementPresent(usernameField);
        TestCaseProperties.closeDriver(driver);
        System.out.println(TEST_TEST_PASSED);  
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void d_invalidLogin() throws InterruptedException
    {
    	extent.startTest("Invalid Login Test");
        System.out.println("Running login test with invalid credentials");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin("abcd", "abcd");
        Thread.sleep(2000);
        String url=driver.getCurrentUrl();
        if(url.equals(loginErrorURL))
        {
        	System.out.println(TEST_TEST_PASSED);
        }
        else
        {
        	System.err.println(TEST_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);   
        System.out.println("---------------------------");
        System.out.println();
    }
    
    @Test
    public void e_loginout() throws InterruptedException
    {
    	extent.startTest("Loginout Test");
        System.out.println("Running loginout");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        loginPage.logout();
        TestCaseProperties.closeDriver(driver);
        System.out.println(TEST_TEST_PASSED);
        System.out.println("---------------------------");
        System.out.println();
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

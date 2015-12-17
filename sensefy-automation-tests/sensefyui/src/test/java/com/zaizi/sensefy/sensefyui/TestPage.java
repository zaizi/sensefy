package com.zaizi.sensefy.sensefyui;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.thoughtworks.selenium.webdriven.commands.GetText;
import com.thoughtworks.selenium.webdriven.commands.GoBack;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;



@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestPage {
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
	private String username;
    private String password;
    private String BrowserName;
    private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
    public String searchTerm="//input[@id='searchTerm']";
    private String usernameField = "//input[@id='username']";
    private String loginErrorURL="http://sensefyqa.zaizicloud.net/auth/login?error";
    public String searchWord="Ephesoft Marketing";
    private String showResult="//div[@id='se-results']/div[4]/div[1]/div/ng-switch/a/span";
    private String nodoc="//div[@id='se-results']/div[4]/div/div[1]";
    private String noDocFound="No document found.";
    private String didyou="//div[@id='se-results']/div[4]/div/div[2]/span[1]";
    private String didYouMean="Did you mean:";
    private String loginURL="http://sensefyqa.zaizicloud.net/auth/login";
    private static final String TEXT_TEST_PASSED = "Test case passed!";
    private static final String TEXT_TEST_FAILED = "Test case failed!";
    private static String languageName="//div[@id='menu']/a/span[2]";
    private static String adminDropdown="//div[@id='menu']/div/i[2]";
    private static String languageSpanish="//div[@id='menu']/div/div/a[@translate='spanish']";
    private static String languageEnglish="//div[@id='menu']/div/div/a[@translate='english']";
    private static String nodocword="kuluk";
    private static String didumean="gobal";
    
    private static String dataSourceAll="//div[@id='ds-tabs']/a[1]/span";
    private static String dataSourceSecond="//div[@id='ds-tabs']/a[2]/span";
    private static String dataSourceThird="//div[@id='ds-tabs']/a[3]/span";
    private static String resultsSet="//div[@class='ui divided items documents']/div";
    private static String numberOfResults="//div[@id='se-results']/div[1]/div/div/div[1]/span";
    
    
    
    private static String suggest="//div[@id='se-results']/div[4]/div/div[2]/span[2]";
    
	static WebDriver driver;
	public TestPage(String username, String password, String BrowserName)
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
        System.out.println("preparing LoginTest234 ..");
        return TestCaseValues.documentLibraryTestValues("LoginTest");
    }
    
    @Test
    public void avvvv_sensefyURL()
    {
    	extent.startTest("Verify SensefyURL");
        System.out.println("Running Test for Sensefy URL");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
        if(driver.getCurrentUrl().equals(loginURL))
        {
        	System.out.println(TEXT_TEST_PASSED);
        }
        else
        {
        	System.out.println(TEXT_TEST_FAILED);
        }
        TestCaseProperties.closeDriver(driver);
    }
    
    
  
    
 
    
    
    
    

    @After
    public void Teardown() {
        driver.quit();  
    }
    
    //
    
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
	
	public void waitforDone()
	{
		String done ="//span[@class='normal-f d-block ng-binding'] [contains(text(),'done')]";
		String running="//span[@class='normal-f d-block ng-binding'] [contains(text(),'running')]";
	}
	

}

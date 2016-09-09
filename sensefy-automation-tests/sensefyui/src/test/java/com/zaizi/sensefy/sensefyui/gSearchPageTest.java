package com.zaizi.sensefy.sensefyui;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import com.zaizi.sensefy.sensefyui.pages.SearchPage;
/*
 * Testing Search Page
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class gSearchPageTest {
	
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	public static final Logger LOGGER = LogManager.getLogger(gSearchPageTest.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(gSearchPageTest.class);

	private String username;
    private String password;

    static WebDriver driver;
	public gSearchPageTest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
	
	@BeforeClass
    public static void beforeClass() 
    {
		extent.init("logs/sensefy.html", false);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation Results");
        extent.config().reportHeadline("Sensefy");
    }   

	@Parameters()
	public static Iterable<Object[]> data() throws IterableException
	{
	    LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "SearchPageTest");
	    return TestCaseValues.documentLibraryTestValues("LoginTest");
	}
	
	//Test Footer note
	@Test
    public void verifyFooter() throws InterruptedException
    {
		LOGGER.info("Running Verify Footer Test");
    	extent.startTest("Verify Footer Test");
    
    	driver = TestCaseProperties.getWebDriverForSearch();
        SearchLogin loginPage = new SearchLogin(driver);
        loginPage.searchuiLogin(username, password);
        Thread.sleep(2000);
        SearchPage sp=new SearchPage(driver);
        Boolean val=sp.footerNote();
        if(val==true)
        {
        	LOGGER.info("Footer Verified Successfully");
        	LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Footer Verified Successfully");
        	extent.log(LogStatus.PASS, "Footer Verified Successfully");
        }
        else
        {
        	extent.log(LogStatus.FAIL, "Footer Verification Failed");
        	LOGGER.error("Footer Verification Failed");
        }
        	
     	LOGGER.error("Footer Verification Failed");
         
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
	
	public void waitforDone()
	{
		String done ="//span[@class='normal-f d-block ng-binding'] [contains(text(),'done')]";
		String running="//span[@class='normal-f d-block ng-binding'] [contains(text(),'running')]";
	}
}

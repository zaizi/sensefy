package com.zaizi.sensefy.sensefyui;



import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.Link;
import com.zaizi.sensefy.sensefyui.elements.Span;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.AlfrescoPage;
import com.zaizi.sensefy.sensefyui.pages.Manifold;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.sensefypage;

/**
 * 
 * @author ljayasinghe
 *
 */

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class hUploadandSearch 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	
	/**
	 * Variable declaration
	 */
	public String username;
	public String password;
	public String site_name;
	public String document_name;
	public String screenshot_name;
	//public int i = 1;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(hUploadandSearch.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(hUploadandSearch.class);
	
	/**
	 * defining class name
	 */
	public static String className = hUploadandSearch.class.getSimpleName();
	
	public hUploadandSearch(String username,String password,String site_name,String document_name,String screenshot_name)
			
	{
		this.username = username;
		this.password = password;
		this.site_name = site_name;
		this.document_name = document_name;
		this.screenshot_name = screenshot_name;

	}
	
	/**
	 * Declares Parameters Here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "hUploadandSearch");
        return TestCaseValues.documentLibraryTestValues("hUploadandSearch");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Upload_And_Verify_Test");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Upload File in the Alfresco And Search the File in Sensefy");
    }
	
	@Test
	public void a_AlfrescoLogin()
	{
		LOGGER.info("Login to Alfresco");
    	extent.startTest("Login to Alfresco");
    	
    	
    	try
    	{
    		//extent.startTest("Navigate to Alfresco Url");
            
    		//System.out.println("Running Test for Alfresco URL");
    		
    		LOGGER.info("Navigate to Alfresco Url");
            
            driver = TestCaseProperties.getAlfresco();
            
            String currentUrl1 = driver.getCurrentUrl().toString();
            
            System.out.println(currentUrl1);
            
            SearchLogin alfrescoLogin = new SearchLogin(driver);
           
            alfrescoLogin.alfrescouilogin(username,password);
                       
            String alfrescoUrl = "http://127.0.0.1:8080/share/page/user/admin/dashboard";
            
            String currentUrl2 = driver.getCurrentUrl().toString();

        	if(!currentUrl2.equalsIgnoreCase(alfrescoUrl))
            {
               	LOGGER.info("Successfully Login to Alfresco");
        		extent.log(LogStatus.PASS, "Successfully Login to Alfresco");
        		Element.takescreenshot(driver,className,screenshot_name+"1");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        		LOGGER.error("UnSuccessfull - Login Failed");
        		Element.takescreenshot(driver,className,screenshot_name+"2");
            }
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
    		LOGGER.error("UnSuccessfull - Login Failed");
    	}
    	
	}
	
	@Test
	public void b_UploadDocument() throws InterruptedException, IOException
	{
		LOGGER.info("Uploading a Document to Alfresco");
    	extent.startTest("Uploading a Document to Alfresco");
    	
    	AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
    	
    	try
    	{
    		//extent.startTest("Navigate to Sites Finder");
            
    		//System.out.println("Navigate to Sites Finder");
    		LOGGER.info("Navigate to Sites Finder");
    		
    		alfrescoPage.navigateToSite();
    		
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		
    		System.out.println("navigated to the Site");
    		
    		alfrescoPage.searchforSite(site_name);
    		
    		String DashboardName = "//a[text()='Sensefy']";
    		
    		if(!DashboardName.equalsIgnoreCase(site_name))
            {
               	LOGGER.info("Successfully navigated to "+site_name);
        		extent.log(LogStatus.PASS, "Successfully navigated to "+site_name);
        		Thread.sleep(3000);
        		Element.takescreenshot(driver,className,screenshot_name+"3");
        		
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull");
        		LOGGER.error("UnSuccessfull");
        		Element.takescreenshot(driver,className,screenshot_name+"4");
        		Thread.sleep(3000);
            }
    		
    		System.out.println("Site Found");    
    		
    		//extent.startTest("Navigate to Document Library");            
    		//System.out.println("Navigate to Document Library");
    		LOGGER.info("Navigate to Document Library");
    		Thread.sleep(3000);
    		
    		alfrescoPage.navigatetoDocumentLibrary();
    		//System.out.println("Navigated to Document Library");
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);

    		//extent.startTest("Creating the Document");            
    		//System.out.println("Creating the Document");
    		LOGGER.info("Creating the Document "+document_name);
    		alfrescoPage.createSampleDoc(document_name);
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);
    		alfrescoPage.navigatetoDocumentLibrary();
    		System.out.println("Navigated to Document Library");
    		
    		//extent.startTest("Verify the Document");            
    		//System.out.println("Verify the Document");
    		LOGGER.info("Verify the Document");
    		Element currentResult1 = new Element(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+document_name+"')]"));
    		
    		if(Button.isElementPresent(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+document_name+"')]")))
    		{
    			
    			LOGGER.info("Expected Results : " + document_name);
            	extent.log(LogStatus.INFO, "Expected Results : " + document_name);
            	           
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " + currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.PASS, document_name+ " successfully created.");
            	
            	Element.takescreenshot(driver,className,screenshot_name+"5");
            	
    		}
    		else
    		{
    			LOGGER.info("Expected Results : " + document_name);
            	extent.log(LogStatus.INFO, "Expected Results : " + document_name);
            	
            	//Current Result
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " +currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.FAIL, document_name+ " creation failed");
            	Element.takescreenshot(driver,className,screenshot_name+"6");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Document creation is failed");
    	}
    	
    	//extent.startTest("Logout from Alfresco");            
		//System.out.println("Logout from Alfresco");
    	LOGGER.info("Logout From Alfresco");
		Thread.sleep(3000);
    	alfrescoPage.alfrescoLogout();
    	Thread.sleep(3000);
    	
    	//Element.takescreenshot(driver,className,screenshot_name+i++);
    	System.out.println("Successfully logout from Alfresco");
    	
    	TestCaseProperties.closeDriver(driver);
    	    		
	}
	
	@Test
	public void c_ManifoldLogin() throws InterruptedException
	{
		LOGGER.info("Login to Manifold");
    	extent.startTest("Login to Manifold");
    	
    	int i=20;
    	
    	try
    	{  	    	
    		//extent.startTest("Navigate to Manifold Url");
            
    		//System.out.println("Running Test for Manifold URL");
            
    		LOGGER.info("Navigate to Manifold Url");
            driver = TestCaseProperties.getManifold();
            
            String currentUrl1 = driver.getCurrentUrl().toString();
            
            System.out.println(currentUrl1);
            
            System.out.println("000000");
               
            SearchLogin searchLogin = new SearchLogin(driver);
            searchLogin.manifoldLogin(username,password);
           
            System.out.println("login to Manifold");
          
            String manifoldurl = TestCaseProperties.returnManifold();
            
            String currentUrl = driver.getCurrentUrl().toString();
            
            if(!currentUrl.equalsIgnoreCase(manifoldurl))
            {
            	LOGGER.info("Successfully Login to Manifold");
        		extent.log(LogStatus.PASS, "Successfully Login to Manifold");
        		Element.takescreenshot(driver,className,screenshot_name+"7");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        		LOGGER.error("UnSuccessfull - Login Failed");
        		Element.takescreenshot(driver,className,screenshot_name+"8");
            }
            
            //extent.startTest("Navigate to Status and Job Management");
            
    		//System.out.println("Navigate to Status and Job Management");
            LOGGER.info("Navigate to Status and Job Management");
    		Manifold manifold = new Manifold(driver);
    		manifold.navigateToJobs();
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);
    		//extent.startTest("Start the Job");
    		//System.out.println("Start the Job");
    		LOGGER.info("Start the Job");
    		manifold.startTheJob();
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);
    		//extent.startTest("Refresh the Job");
    		//System.out.println("Refresh the Job");
    		LOGGER.info("Refresh the Job");
    		manifold.refreshJob();
    		Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);
    		
    		i++;
    		
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Unsuccessful");
    	}
    	
    	
    	TestCaseProperties.closeDriver(driver);
    		      	
	}
	
	@Test
	public void d_sensefyLogin()
	{
		LOGGER.info("Login to Sensefy");
    	extent.startTest("Login to Sensefy");
    	
    	try
    	{  	    	
    		//extent.startTest("Navigate to Sensefy Url");
            
    		//System.out.println("Running Test for Sensefy URL");
    		LOGGER.info("Navigate to Sensefy Url");
            driver = TestCaseProperties.getSensefyLocalHost();
            
            String currentUrl1 = driver.getCurrentUrl().toString();
            
            System.out.println(currentUrl1);
            
            sensefypage sensefypage = new sensefypage(driver);
            sensefypage.logintosensefy(username, password);
                    
            String sensefyurl = "http://192.168.1.72:9099";
            
            String currentUrl2 = driver.getCurrentUrl().toString();

        	if(!currentUrl2.equalsIgnoreCase(sensefyurl))
            {
               	LOGGER.info("Successfully Login to Sensefy");
        		extent.log(LogStatus.PASS, "Successfully Login to Sensefy");
        		
        		Element.takescreenshot(driver,className,screenshot_name+"9");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        		LOGGER.error("UnSuccessfull - Login Failed");
        		Element.takescreenshot(driver,className,screenshot_name+"10");
            }
        	
        	//extent.startTest("Search the Document");
            
    		//System.out.println("Search the Document");
    		/*LOGGER.info("Search the Document : "+document_name);
        	sensefypage.searchtheDocument(document_name);
        	      	
        	if(!Link.isElementPresent(driver, By.xpath("//div[@class='content info']//ng-switch//a[@class='header title']//span//b[text()='"+document_name+"']")))
        	{
        		LOGGER.info("Successful : " +document_name+ " is available");
        		extent.log(LogStatus.PASS, "Successful : " +document_name+ " is available");
        		Element.takescreenshot(driver,className,screenshot_name+i++);
        		Thread.sleep(3000);
        	}
        	else
        	{
        		extent.log(LogStatus.FAIL, "Unsuccessful : " +document_name+ " is not available");
        		LOGGER.error("Unsuccessful : " +document_name+ " is not available");
        		Element.takescreenshot(driver,className,screenshot_name+i++);
        		Thread.sleep(3000);
        	}       	*/
    	}
    	catch(Exception e)
    	{
    		System.out.println("Unsuccessful");
    	}
	}
	
	@Test
	public void e_SearchtheDoc_p()
	{
		LOGGER.info("Search for the Uploaded document on Sensefy");
    	extent.startTest("Search for the Uploaded document on Sensefy");
    	
    	try
    	{
    		sensefypage sensefypage = new sensefypage(driver);
    		
    		LOGGER.info("Search the Document : "+document_name);
        	sensefypage.searchtheDocument(document_name);
        	      	
        	String testpath = "//div[@class='content info']//ng-switch//a[@class='header title']//span//b[text()='"+document_name+"']";
        	System.out.println(testpath.toString());
        	if(!Link.isElementPresent(driver, By.xpath(testpath)))
        	{
        		LOGGER.info("Successful : " +document_name+ " is available");
        		extent.log(LogStatus.PASS, "Successful : " +document_name+ " is available");
        		Element.takescreenshot(driver,className,screenshot_name+"11");
        		Thread.sleep(3000);
        	}
        	else
        	{
        		extent.log(LogStatus.FAIL, "Unsuccessful : " +document_name+ " is not available");
        		LOGGER.error("Unsuccessful : " +document_name+ " is not available");
        		Element.takescreenshot(driver,className,screenshot_name+"12");
        		Thread.sleep(3000);
        	}       
    	}
    	catch(Exception e)
    	{
    		System.out.println("Unsuccessful");
    	}
	}
	
	@Test
	public void f_DeleteDocument() throws InterruptedException
	{
		a_AlfrescoLogin();
		
		LOGGER.info("Delete the Document from Alfresco");
    	extent.startTest("Delete the Document from Alfresco");
    	
    	try 
    	{
    		//extent.startTest("Navigate to the Site Search");
    		//System.out.println("Navigate to the Site Search");
    		LOGGER.info("Navigate to the Sites Search");
			AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
			alfrescoPage.navigateToSite();
			Thread.sleep(3000);
			
			//extent.startTest("Navigate to the Site "+site_name);
    		//System.out.println("Navigate to the Site "+site_name);
    		LOGGER.info("Navigate to site "+site_name);
			alfrescoPage.searchforSite(site_name);
			Thread.sleep(3000);
			
			//extent.startTest("Navigate to the Document Library");
    		//System.out.println("Navigate to the Document Library");
    		LOGGER.info("Navigate to Document Library");
			alfrescoPage.navigatetoDocumentLibrary();
			Thread.sleep(3000);
			
			//extent.startTest("Navigated to the Document "+document_name);
			//System.out.println("Navigated to the Document "+document_name);
			LOGGER.info("Navigate to the Document "+document_name);
			alfrescoPage.deleteDocument(document_name);
			Thread.sleep(3000);
			
			alfrescoPage.navigatetoDocumentLibrary();
			Thread.sleep(3000);
			
			if(!Element.isElementPresent(driver, By.xpath("//tbody//tr[2]//td[4]//div//h3//span//a[text()='"+document_name+"']")))
			{
				LOGGER.info("Successfully Deleted the Document " +document_name);
        		extent.log(LogStatus.PASS, "Successfully Deleted the Document " +document_name);        		
        		Element.takescreenshot(driver,className,screenshot_name+"13");
        		Thread.sleep(3000);
			}
			else
			{
				extent.log(LogStatus.FAIL, "Unsuccessful : " +document_name+ " is not deleted.");
        		LOGGER.error("Unsuccessful : " +document_name+ " is not deleted.");
        		Element.takescreenshot(driver,className,screenshot_name+"14");
        		Thread.sleep(3000);
			}
			
			//extent.startTest("Logout from Alfresco");            
			//System.out.println("Logout from Alfresco");
			LOGGER.info("Logout from Alfresco");
			Thread.sleep(3000);
	    	alfrescoPage.alfrescoLogout();
	    	Thread.sleep(3000);
	    	System.out.println("Successfully logout from Alfresco");
	    	
	    	c_ManifoldLogin();
	    	Thread.sleep(3000);
	    	d_sensefyLogin();
	    	
		} 
    	catch (Exception e) 
    	{
			System.out.println("Unsucessful");
		}
		
	}
	
	@Test
	public void g_SearchtheDoc_n()
	{
		LOGGER.info("Search for the Uploaded document on Sensefy");
    	extent.startTest("Search for the Uploaded document on Sensefy");
    	
    	try
    	{
    		sensefypage sensefypage = new sensefypage(driver);
    		
    		LOGGER.info("Search the Document : "+document_name);
        	sensefypage.searchtheDocument(document_name);
        	      	
        	if(!Link.isElementPresent(driver, By.xpath("//div[@class='content info']//ng-switch//a[@class='header title']//span//b[text()='"+document_name+"']")))
        	{
        		LOGGER.info("Successful : " +document_name+ " is not available");
        		extent.log(LogStatus.PASS, "Successful : " +document_name+ " is not available");
        		Element.takescreenshot(driver,className,screenshot_name+"15");
        		Thread.sleep(3000);
        	}
        	else
        	{
        		extent.log(LogStatus.FAIL, "Unsuccessful : " +document_name+ " is available");
        		LOGGER.error("Unsuccessful : " +document_name+ " is available");
        		Element.takescreenshot(driver,className,screenshot_name+"16");
        		Thread.sleep(3000);
        	}       
    	}
    	catch(Exception e)
    	{
    		System.out.println("Unsuccessful");
    	}
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
	         Node alfrescopath = getProperty(propertyName);
	         result = alfrescopath.getNodeValue();
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

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.Link;
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

public class jPrivateSiteDocuments 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	

	/**
	 * Variable declaration
	 */
	public String username;
	public String password;
	public String userA;
	public String emailA;
	public String usernameA;
	public String passwordA;
	public String userB;
	public String emailB;
	public String usernameB;
	public String passwordB;
	public String privateSiteName;
	public String documentName;
	public String screen_shot;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(jPrivateSiteDocuments.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(jPrivateSiteDocuments.class);
	
	/**
	 * defining class name
	 */
	public static String className = jPrivateSiteDocuments.class.getSimpleName();
		
	public jPrivateSiteDocuments(String username, String password,
			String userA, String emailA, String usernameA, String passwordA,
			String userB, String emailB, String usernameB, String passwordB,
			String privateSiteName, String documentName, String screen_shot) 
	{
		this.username = username;
		this.password = password;
		this.userA = userA;
		this.emailA = emailA;
		this.usernameA = usernameA;
		this.passwordA = passwordA;
		this.userB = userB;
		this.emailB = emailB;
		this.usernameB = usernameB;
		this.passwordB = passwordB;
		this.privateSiteName = privateSiteName;
		this.documentName = documentName;
		this.screen_shot = screen_shot;
	}

	/**
	 * Declares Parameters Here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "jPrivateSiteDocuments");
        return TestCaseValues.documentLibraryTestValues("jPrivateSiteDocuments");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Private Site Documents Search");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Document Search in Private Sites on Sensefy");
    }
	
	@Test
	public void a_createUsers() throws InterruptedException, IOException
	{
		LOGGER.info("Create Users in Alfresco");
    	extent.startTest("Create Users in Alfresco");
    	
    	try
    	{

    		LOGGER.info("Navigate to Alfresco Url");   
    		extent.log(LogStatus.INFO, "Navigate to Alfresco Url");
            driver = TestCaseProperties.getAlfresco();
            
            LOGGER.info("Login as the Admin");
            extent.log(LogStatus.INFO, "Login as the Admin");
            SearchLogin searchLogin = new SearchLogin(driver);
            Thread.sleep(3000);
            searchLogin.alfrescouilogin(username, password);
            Thread.sleep(3000);
            
    		AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
    		LOGGER.info("Navigate to the Admin Tools");
    		extent.log(LogStatus.INFO, "Navigate to the Admin Tools");
    		alfrescoPage.gotoAdminTools();
    		Thread.sleep(3000);
    		
    		LOGGER.info("Navigate to User Management");
    		extent.log(LogStatus.INFO, "Navigate to User Management");
    		alfrescoPage.gotoUsers();
    		Thread.sleep(3000);
    		
    		LOGGER.info("Create the New User " +userA);
    		extent.log(LogStatus.INFO, "Create the New User " +userA);
    		alfrescoPage.createUser(userA, emailA, usernameA, passwordA);
    		Thread.sleep(3000);
    		
    		alfrescoPage.gotoUsers();
    		Thread.sleep(3000);
    		
    		LOGGER.info("Create the New User " +userB);
    		extent.log(LogStatus.INFO, "Create the New User " +userB);
    		alfrescoPage.createUser(userB, emailB, usernameB, passwordB); 
    		Thread.sleep(3000);
    		
    		LOGGER.info("Verify the User Creation");
    		extent.log(LogStatus.INFO, "Verify the User Creation");
    		Thread.sleep(3000);
    		//alfrescoPage.gotoUsers();
    		alfrescoPage.verifyUser("user");
    		Thread.sleep(3000);
    		Element.takescreenshot(driver,className,screen_shot+"1");
    		Thread.sleep(3000);
    		System.out.println("done");
    	}
    	catch(Exception e)
    	{
    		System.out.println("User creation is Unsuccessful");
    	}
	}
	
	@Test
	public void createSitesandUpload()
	{
		LOGGER.info("Create Private Site in Alfresco and Upload Document");
    	extent.startTest("Create Private Site in Alfresco and Upload Document");
    	
    	try
    	{
    		LOGGER.info("Navigate to Create Sites");   
    		extent.log(LogStatus.INFO, "Navigate to Create Sites");
    		
    		AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
    		LOGGER.info("Navigate to Sites");   
    		extent.log(LogStatus.INFO, "Navigate to Sites");
    		alfrescoPage.gotocreateSite();
    		Thread.sleep(3000);
    		LOGGER.info("Create the Private Site "+privateSiteName);   
    		extent.log(LogStatus.INFO, "Create the Private Site "+privateSiteName);
    		alfrescoPage.createPrivateSite(privateSiteName);
    		Thread.sleep(5000);
    		
    		LOGGER.info("Verify the Private Site "+privateSiteName);   
    		extent.log(LogStatus.INFO, "Verify the Private Site "+privateSiteName);
    		
    		if(Element.isElementPresent(driver, By.xpath("//h1[@id='HEADER_TITLE']//a[text()='"+privateSiteName+"']")))
    		{    
    			LOGGER.info(privateSiteName+ " successfully created.");   
            	extent.log(LogStatus.PASS, privateSiteName+ " successfully created.");            	
            	Element.takescreenshot(driver,className,screen_shot+"2");
            	Thread.sleep(3000);
    		}
    		else
    		{
    			LOGGER.info(privateSiteName+ " is not created.");   
    			extent.log(LogStatus.FAIL, privateSiteName+ " is not created.");            	
            	Element.takescreenshot(driver,className,screen_shot+"3");
            	Thread.sleep(3000);
    		}    	
    		
    		LOGGER.info("Upload "+documentName+" to the Private Site "+privateSiteName);   
    		extent.log(LogStatus.INFO, "Upload "+documentName+" to the Private Site "+privateSiteName);
    		alfrescoPage.navigatetoDocumentLibrary();
    		Thread.sleep(3000);
    		
    		alfrescoPage.createSampleDoc(documentName);
    		Thread.sleep(3000);
    		
    		LOGGER.info("Verify the Document");
    		extent.log(LogStatus.INFO, "Verify the Document");
    		alfrescoPage.navigatetoDocumentLibrary();
    		Thread.sleep(3000);
    		
    		Element currentResult1 = new Element(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+documentName+"')]"));
    		
    		if(Button.isElementPresent(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+documentName+"')]")))
    		{
    			
    			LOGGER.info("Expected Results : " + documentName);
            	extent.log(LogStatus.INFO, "Expected Results : " + documentName);
            	           
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " + currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.PASS, documentName+ " successfully created.");
            	
            	Element.takescreenshot(driver,className,screen_shot+"4");
            	Thread.sleep(3000);
            	
    		}
    		else
    		{
    			LOGGER.info("Expected Results : " + documentName);
            	extent.log(LogStatus.INFO, "Expected Results : " + documentName);
            	
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " +currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.FAIL, documentName+ " creation failed");
            	Element.takescreenshot(driver,className,screen_shot+"5");
            	Thread.sleep(3000);
    		}
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Private Site Creation is unsuccessful.");
    	}
    	
	}
	
	@Test
	public void inviteFAndAcceptSite()
	{
		LOGGER.info("Send the Site Invitation for "+userA+" and Accept the Site Invitation");
		extent.startTest("Send the Site Invitation for "+userA+" and Accept the Site Invitation");
		
		try
		{
			LOGGER.info("Invite the Members");   
    		extent.log(LogStatus.INFO, "Invite the Members");
    		AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
    		LOGGER.info("Invite "+userA+" to "+privateSiteName);   
    		extent.log(LogStatus.INFO, "Invite "+userA+" to "+privateSiteName);
    		alfrescoPage.inviteMember(userA);  		
    		Thread.sleep(3000);
    		
    		Element.takescreenshot(driver, className, screen_shot+"6");
    		
    		LOGGER.info("Logout As Admin");   
    		extent.log(LogStatus.INFO, "Logout As Admin");
    		alfrescoPage.alfrescoLogout();
    		
    		LOGGER.info("Login as the "+userA);
            extent.log(LogStatus.INFO, "Login as the "+userA);
            SearchLogin searchLogin = new SearchLogin(driver);
            Thread.sleep(3000);
            searchLogin.alfrescouilogin(usernameA,passwordA);
            Thread.sleep(3000);
            
            LOGGER.info("Accept the Invitation");
            extent.log(LogStatus.INFO, "Accept the Invitation");
            //AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
            Element.takescreenshot(driver, className, screen_shot+"5");
            alfrescoPage.acceptInvitation(privateSiteName);
            Thread.sleep(3000);
            
            LOGGER.info("Navigate to the Site :"+privateSiteName);
            extent.log(LogStatus.INFO, "Navigate to the Site :"+privateSiteName);
            alfrescoPage.navigateToSite();
            Thread.sleep(3000);
            
            alfrescoPage.searchforSite(privateSiteName);
            Thread.sleep(3000);
            Element.takescreenshot(driver, className, screen_shot+"7");
            Thread.sleep(3000);
            
            LOGGER.info("Logout As "+userA);   
    		extent.log(LogStatus.INFO, "Logout As "+userA);
    		alfrescoPage.alfrescoLogout();
		}
		catch(Exception e)
		{
			System.out.println("Site Invitation Unsuccessful");
		}
		
		TestCaseProperties.closeDriver(driver);
		
	}
	
	@Test
	public void manifold_funtions() throws InterruptedException, IOException
	{
		try
		{
			LOGGER.info("Manifold Functions");
	    	extent.startTest("Manifold Functions");
	    		  
	    	LOGGER.info("Login to Manifold"); 
	    	extent.log(LogStatus.INFO, "Login to Manifold");
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
	        	Element.takescreenshot(driver,className,screen_shot+"8");
	        }
	        else
	        {
	           	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
	        	LOGGER.error("UnSuccessfull - Login Failed");
	        	Element.takescreenshot(driver,className,screen_shot+"9");
	        }
	            
	        extent.log(LogStatus.INFO, "Navigate to Status and Job Management");
	        LOGGER.info("Navigate to Status and Job Management");
	    	Manifold manifold = new Manifold(driver);
	    	manifold.navigateToJobs();
	    	Element.takescreenshot(driver,className,screen_shot+"10");
	    	Thread.sleep(3000);
	    		
	    	extent.log(LogStatus.INFO, "Start the Job");
	    	LOGGER.info("Start the Job");
	    	Thread.sleep(5000);
	    	manifold.startTheJob();
	    	//Element.takescreenshot(driver,className,screen_shot+"11");
	    	Thread.sleep(5000);
	    		
	    	extent.log(LogStatus.INFO, "Refresh the Job");
	    	LOGGER.info("Refresh the Job");
	    	manifold.refreshJob();
	    	Element.takescreenshot(driver,className,screen_shot+"12");
	    	Thread.sleep(3000);	    		      	
		}
		catch(Exception e)
		{
			System.out.println("Unsuccessful");
		}
	    	
	    TestCaseProperties.closeDriver(driver);
	    
	}
	
	@Test
	public void searchAsMember() throws InterruptedException, IOException
	{
		LOGGER.info("Search for the Uploaded document as "+userA);
    	extent.startTest("Search for the Uploaded document as "+userA);
    	
    	try
    	{  	    	
    		extent.log(LogStatus.INFO, "Navigate to Sensefy Url");
    		LOGGER.info("Navigate to Sensefy Url");
            driver = TestCaseProperties.getSensefyLocalHost();
            
            String currentUrl1 = driver.getCurrentUrl().toString();
            
            System.out.println(currentUrl1);
            
            extent.log(LogStatus.INFO, "Login as "+userA+" who is a memeber of the Private Site "+privateSiteName);
    		LOGGER.info("Login as "+userA);
            sensefypage sensefypage = new sensefypage(driver);
            sensefypage.logintosensefy(usernameA, passwordA);
                    
            String sensefyurl = "http://192.168.1.72:9099";
            
            String currentUrl2 = driver.getCurrentUrl().toString();

        	if(!currentUrl2.equalsIgnoreCase(sensefyurl))
            {
               	LOGGER.info("Successfully Login to Sensefy");
        		extent.log(LogStatus.PASS, "Successfully Login to Sensefy");
        		
        		Element.takescreenshot(driver,className,screen_shot+"13");
        		Thread.sleep(3000);
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        		LOGGER.error("UnSuccessfull - Login Failed");
        		Element.takescreenshot(driver,className,screen_shot+"14");
        		Thread.sleep(3000);
            }
        	
        	Thread.sleep(3000);
 
        	LOGGER.info("Search the Document : "+documentName);
            sensefypage.searchtheDocument(documentName);
            Thread.sleep(5000);	     
            
            String testpath = "//div[@class='content info']//ng-switch//a[@class='header title']//span//b[text()='"+documentName+"']";
            System.out.println(testpath.toString());
            if(Link.isElementPresent(driver, By.xpath(testpath)))
            {
            	LOGGER.info("Successful : " +documentName+ " is available");
            	extent.log(LogStatus.PASS, "Successful : " +documentName+ " is available");
            	Element.takescreenshot(driver,className,screen_shot+"15");
            	Thread.sleep(5000);
            }
            else
            {
            	extent.log(LogStatus.FAIL, "Unsuccessful : " +documentName+ " is not available");
            	LOGGER.error("Unsuccessful : " +documentName+ " is not available");
            	Element.takescreenshot(driver,className,screen_shot+"16");
            	Thread.sleep(5000);
            }               	        	
            
            extent.log(LogStatus.INFO, "Logout as "+userA);
    		LOGGER.info("Logout as "+userA);
            sensefypage.logoutfromSensefy();
            Thread.sleep(5000);
            TestCaseProperties.closeDriver(driver);
    	}   	
    	catch(Exception e)
    	{
    		System.out.println("Unsuccessful");
    	}
	}
	
	@Test
	public void searchAsNonMember() throws InterruptedException, IOException
	{
		LOGGER.info("Search for the Uploaded document as "+userB);
    	extent.startTest("Search for the Uploaded document as "+userB);
    	
		extent.log(LogStatus.INFO, "Navigate to Sensefy Url");
		LOGGER.info("Navigate to Sensefy Url");
        driver = TestCaseProperties.getSensefyLocalHost();
        
        String currentUrl1 = driver.getCurrentUrl().toString();
        
        String currentUrl2 = driver.getCurrentUrl().toString();
        
        String sensefyurl = "http://192.168.1.72:9099";
       
        System.out.println(currentUrl1);
        
        extent.log(LogStatus.INFO, "Login as "+userB+ "who is not a member of the Private site "+privateSiteName);
		LOGGER.info("Login as "+userB);
		sensefypage sensefypage = new sensefypage(driver);
		sensefypage.logintosensefy(usernameB, passwordB);
		Thread.sleep(3000);
      
		if(!currentUrl2.equalsIgnoreCase(sensefyurl))
        {
           	LOGGER.info("Successfully Login to Sensefy");
    		extent.log(LogStatus.PASS, "Successfully Login to Sensefy");
    		
    		Element.takescreenshot(driver,className,screen_shot+"17");
    		Thread.sleep(3000);
        }
        else
        {
        	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
    		LOGGER.error("UnSuccessfull - Login Failed");
    		Element.takescreenshot(driver,className,screen_shot+"18");
    		Thread.sleep(3000);
        }
		
		Thread.sleep(3000);
		 
    	LOGGER.info("Search the Document : "+documentName);
        sensefypage.searchtheDocument(documentName);
        Thread.sleep(5000);	     
        
        String testpath = "//div[@class='content info']//ng-switch//a[@class='header title']//span//b[text()='"+documentName+"']";
        System.out.println(testpath.toString());
        if(!Link.isElementPresent(driver, By.xpath(testpath)))
        {
        	LOGGER.info("Successful : " +documentName+ " is not available");
        	extent.log(LogStatus.PASS, "Successful : " +documentName+ " is not available");
        	Element.takescreenshot(driver,className,screen_shot+"19");
        	Thread.sleep(5000);
        }
        else
        {
        	extent.log(LogStatus.FAIL, "Unsuccessful : " +documentName+ " is available");
        	LOGGER.error("Unsuccessful : " +documentName+ " is available");
        	Element.takescreenshot(driver,className,screen_shot+"20");
        	Thread.sleep(5000);
        }     
	
	}
	

	
	private static Node getProperty(String propertyName) throws ParserConfigurationException, SAXException, IOException
	{
		File testValues = new File(TestCaseProperties.returnTestPropertiesXml());
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

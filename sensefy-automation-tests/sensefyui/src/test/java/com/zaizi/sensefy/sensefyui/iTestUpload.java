package com.zaizi.sensefy.sensefyui;

import java.io.IOException;

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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.AlfrescoPage;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;


/**
 * 
 * @author ljayasinghe
 *
 */

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class iTestUpload 
{
	/**
	 * Define Web driver
	 */
	
	public static WebDriver driver;
	
	/**
	 * variable declaration
	 */
	public String username;
	public String password;
	public String site_name;
	public String uploadDocument;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(iTestUpload.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(hUploadandSearch.class);
	
	/**
	 * Defining the class name
	 */
	public static String className = iTestUpload.class.getSimpleName();

	public iTestUpload(String username, String password, String site_name,String uploadDocument) 
	{
		this.username = username;
		this.password = password;
		this.site_name = site_name;
		this.uploadDocument = uploadDocument;
	}

	/**
	 * Declares Parameters Here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "iTestUpload");
        return TestCaseValues.documentLibraryTestValues("iTestUpload");
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
        extent.config().reportHeadline("Upload File in the Alfresco");
    }
	
	@Test
	public void a_AlfrescoLogin()
	{
		LOGGER.info("Login to Alfresco");
    	extent.startTest("Login to Alfresco");
    	
    	
    	try
    	{    		
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
        		//Element.takescreenshot(driver,className,screenshot_name+"1");
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
        		LOGGER.error("UnSuccessfull - Login Failed");
        		//Element.takescreenshot(driver,className,screenshot_name+"2");
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
        		//Element.takescreenshot(driver,className,screenshot_name+"3");
        		
            }
            else
            {
            	extent.log(LogStatus.FAIL, "UnSuccessfull");
        		LOGGER.error("UnSuccessfull");
        		//Element.takescreenshot(driver,className,screenshot_name+"4");
        		Thread.sleep(3000);
            }
    		
    		System.out.println("Site Found");    
    		
    		//extent.startTest("Navigate to Document Library");            
    		//System.out.println("Navigate to Document Library");
    		LOGGER.info("Navigate to Document Library");
    		Thread.sleep(3000);
    		
    		alfrescoPage.navigatetoDocumentLibrary();
    		System.out.println("Navigated to Document Library");
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);

    		//extent.startTest("Creating the Document");            
    		//System.out.println("Creating the Document");
    		LOGGER.info("Uploading the Document "+uploadDocument);
    		alfrescoPage.uploadDocument(uploadDocument);
    		            
    		//Element.takescreenshot(driver,className,screenshot_name+i++);
    		Thread.sleep(3000);
    		alfrescoPage.navigatetoDocumentLibrary();
    		System.out.println("Navigated to Document Library");
    		
    		//extent.startTest("Verify the Document");            
    		System.out.println("Verify the Document");
    		LOGGER.info("Verify the Document");
    		Element currentResult1 = new Element(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+uploadDocument+"')]"));
    		
    		if(Button.isElementPresent(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+uploadDocument+"')]")))
    		{
    			
    			LOGGER.info("Expected Results : " + uploadDocument);
            	extent.log(LogStatus.INFO, "Expected Results : " + uploadDocument);
            	           
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " + currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.PASS, uploadDocument+ " successfully created.");
            	
            	//Element.takescreenshot(driver,className,screenshot_name+"5");
            	
    		}
    		else
    		{
    			LOGGER.info("Expected Results : " + uploadDocument);
            	extent.log(LogStatus.INFO, "Expected Results : " + uploadDocument);
            	
            	//Current Result
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " +currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.FAIL, uploadDocument+ " creation failed");
            	//Element.takescreenshot(driver,className,screenshot_name+"6");
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
	

}

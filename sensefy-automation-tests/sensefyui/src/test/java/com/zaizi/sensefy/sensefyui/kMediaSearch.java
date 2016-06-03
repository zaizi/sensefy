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
import com.zaizi.sensefy.sensefyui.info.UrlFinder;
import com.zaizi.sensefy.sensefyui.pages.AlfrescoPage;
import com.zaizi.sensefy.sensefyui.pages.Manifold;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;

/**
 * 
 * @author ljayasinghe
 *
 */

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class kMediaSearch 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	

	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	
	/**
	 * Varialble Declaration
	 */
	public String username1;
	public String password1;
	public String username2;
	public String password2;
	public String sitename;
	public String videoname;
	public String screenshot_name;
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(kMediaSearch.class.getName());
		
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(kMediaSearch.class);
	
	/**
	 * defining class name
	 */
	public static String className = kMediaSearch.class.getSimpleName();
	
	
	/**
	 * 
	 * @param username1
	 * @param password1
	 * @param username2
	 * @param password2
	 * @param sitename
	 * @param videoname
	 * @param screenshot_name
	 */
	public kMediaSearch(String username1, String password1, String username2, String password2, String sitename,
			String videoname, String screenshot_name) 
	{
		this.username1 = username1;
		this.password1 = password1;
		this.username2 = username2;
		this.password2 = password2;
		this.sitename = sitename;
		this.videoname = videoname;
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
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "kMediaSearch");
        return TestCaseValues.documentLibraryTestValues("kMediaSearch");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Upload_Video_And_Verify_Test");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Upload a Video file in Alfresco And Search the File in Sensefy");
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
           
            alfrescoLogin.alfrescouilogin(username1,password1);
                       
            String alfrescoUrl = " http://sensefyqa.zaizicloud.net/share/page/";
           
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
	public void b_UploadVideo() throws InterruptedException, IOException
	{
		LOGGER.info("Uploading a Video to Alfresco");
    	extent.startTest("Uploading a Video to Alfresco");
    	
    	AlfrescoPage alfrescoPage = new AlfrescoPage(driver);
    	
    	try
    	{
    		LOGGER.info("Navigate to Sites Finder");
    		
    		//alfrescoPage.navigateToSite();
    		alfrescoPage.navigateToFavoriteQA();
    	    		
    		//System.out.println("navigated to the Site");
    		
    		//alfrescoPage.searchforSite(sitename);
    		
    		String DashboardName = "//a[text()='QASite']";
    		
    		if(!DashboardName.equalsIgnoreCase(sitename))
            {
               	LOGGER.info("Successfully navigated to "+sitename);
        		extent.log(LogStatus.PASS, "Successfully navigated to "+sitename);
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
    		
    	 	LOGGER.info("Navigate to Document Library");
    		Thread.sleep(3000);
    		
    		alfrescoPage.navigatetoDocumentLibrary();
    		System.out.println("Navigated to Document Library");
    	
    		Thread.sleep(3000);
    		
    	
    		LOGGER.info("Uploading the Video File "+videoname);
    		alfrescoPage.uploadVideo(videoname);
    		            
    		Thread.sleep(3000);
    		alfrescoPage.navigatetoDocumentLibrary();
    		System.out.println("Navigated to Document Library");
    		       
    		System.out.println("Verify the Document");
    		LOGGER.info("Verify the Document");
    		Element currentResult1 = new Element(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+videoname+"')]"));
    		
    		if(Button.isElementPresent(driver, By.xpath("//h3[@class='filename']//span//a[contains(text(),'"+videoname+"')]")))
    		{
    			
    			LOGGER.info("Expected Results : " + videoname);
            	extent.log(LogStatus.INFO, "Expected Results : " + videoname);
            	           
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " + currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.PASS, videoname+ " successfully created.");
            	
            	Element.takescreenshot(driver,className,screenshot_name+"5");
            	
    		}
    		else
    		{
    			LOGGER.info("Expected Results : " + videoname);
            	extent.log(LogStatus.INFO, "Expected Results : " + videoname);
            	
            	//Current Result
            	LOGGER.info("Current Test Results : " +currentResult1.getElement().getText());
            	extent.log(LogStatus.INFO, "Current Test Results : " +currentResult1.getElement().getText());
            	
            	extent.log(LogStatus.FAIL, videoname+ " creation failed");
            	Element.takescreenshot(driver,className,screenshot_name+"6");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Document creation is failed");
    	}
    	
    	         
		//System.out.println("Logout from Alfresco");
    	LOGGER.info("Logout From Alfresco");
		Thread.sleep(3000);
    	alfrescoPage.alfrescoLogout();
    	Thread.sleep(3000);
    	
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
            //*****check this
            //searchLogin.manifoldLogin(username,password);
           
            System.out.println("login to Manifold");
          
            UrlFinder urlFinder = new UrlFinder();
            
            String manifoldurl = urlFinder.returnManifold();
            
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
            //************************
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
	
	

}

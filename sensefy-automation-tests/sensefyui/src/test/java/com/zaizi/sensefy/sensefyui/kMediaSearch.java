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
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
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
                       
            //String alfrescoUrl = "http://127.0.0.1:8080/share/page/user/admin/dashboard";
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
	
	
	

}

package com.zaizi.sensefy.sensefyui;

import java.io.IOException;
import java.util.regex.Pattern;

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

import com.gargoylesoftware.htmlunit.javascript.host.Screen;
import com.relevantcodes.extentreports.ExtentReports;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;

/**
 * 
 * @author ljayasinghe
 *
 */

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class testSikulli 
{
	/**
	 * Define Webdriver
	 */
	public static WebDriver driver;
	
	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	
	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(testSikulli.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(testSikulli.class);
	
	/**
	 * defining class name
	 */
	public static String className = testSikulli.class.getSimpleName();
	
	/**
	 * constructor
	 */
	public testSikulli()
	{
		
	}
	
	/**
	 * Declares Parameters Here
	 * @return
	 * @throws IterableException
	 */
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "testSikulli");
        return TestCaseValues.documentLibraryTestValues("testSikulli");
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
        extent.config().reportHeadline("Sikulli Test");
    }
	
	@Test
	public void a_sikuliLogin()
	{
		Screen screen = new Screen();
		
		
	}
	
}

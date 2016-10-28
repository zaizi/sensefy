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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.sensefypage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class oAudioSearch 
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
	public String search_term;
	public String result;
	public String size1;
	public String size2;
	public String size3;
	public String lang1;
	public String lang2;
	public String lang3;
	public String screenshot_name;

	/**
	 * log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(oAudioSearch.class.getName());
	
	/**
	 * Extent Reports
	 */
	public static final ExtentReports extent = ExtentReports.get(oAudioSearch.class);
	
	/**
	 * defining class name
	 */
	public static String className = oAudioSearch.class.getSimpleName();

	public oAudioSearch(String username, String password, String search_term,String result,String size1, String size2, String size3,String lang1,
			String lang2, String lang3, String screenshot_name) 
	{
		this.username = username;
		this.password = password;
		this.search_term = search_term;
		this.result = result;
		this.size1 = size1;
		this.size2 = size2;
		this.size3 = size3;
		this.lang1 = lang1;
		this.lang2 = lang2;
		this.lang3 = lang3;
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
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "oAudioSearch");
        return TestCaseValues.documentLibraryTestValues("oAudioSearch");
    }
    
    /**
     * Extent Reports Configurations
     * @throws IOException 
     */
	@BeforeClass
    public static void beforeClass() throws IOException 
	{
    	Element.reportInitial(driver, className);
    	extent.config().documentTitle("Audio Search in Sensefy");
        extent.config().reportTitle("Regression");
        extent.config().reportHeadline("Audio Search in Sensefy");
    }
	
	@Test
	public void a_LogintoSensefy() throws InterruptedException, IOException
	{	
		LOGGER.info("Login to Sensefy Mico");
		extent.startTest("Login to Sensefy Mico");
		
		LOGGER.info("Navigate to Sensefy Mico Url");
        //driver = TestCaseProperties.getSensefyMico();
        driver = TestCaseProperties.getSensefyQa();
        driver.manage().window().setSize(new Dimension(1920, 1920)); 
        String currentUrl1 = driver.getCurrentUrl().toString();
            
        System.out.println(currentUrl1);
            
        sensefypage sensefypage = new sensefypage(driver);
        sensefypage.logintosensefy(username, password);
            
        String sensefyurl = "http://mico.zaizicloud.net";
           
        String currentUrl2 = driver.getCurrentUrl().toString();

      	if(!currentUrl2.equalsIgnoreCase(sensefyurl))
        {
           	LOGGER.info("Successfully Login to Sensefy Mico");
       		extent.log(LogStatus.PASS, "Successfully Login to Sensefy Mico");
        		
       		Element.takescreenshot(driver,className,screenshot_name+"1");
         }
         else
         {
           	extent.log(LogStatus.FAIL, "UnSuccessfull - Login Failed");
       		LOGGER.error("UnSuccessfull - Login Failed");
       		Element.takescreenshot(driver,className,screenshot_name+"2");
         }
 	}
	
	@Test
	public void b_searchAudio() throws InterruptedException, IOException
	{
		LOGGER.info("Search the Audio file");
		extent.startTest("Search the Audio file");
		

		TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
			
		searchfield.clearText();
		Thread.sleep(5000);
		
		searchfield.enterText(search_term);
		driver.findElement(By.xpath("//div[@id='header']/div[1]/div/div[1]/div[2][text()='Search']")).click();
		Thread.sleep(3000);
		
		//Plus button
		driver.findElement(By.xpath("//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[1]//div//i[2]")).click();
		Thread.sleep(3000);
		
		//select type
		driver.findElement(By.xpath("//body[@id='sensefy']//div[1]//div[3]//div[2]//div[4]//div[3]//div[1]//ul//li[5]//span[1]//span//span[text()='WAV Audio']")).click();
		Thread.sleep(3000);
		
		String result1 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final1 = driver.findElement(By.xpath(result1)).getText().toString();
		System.out.println(final1);
								
		if(result.equals(final1))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + result);
			extent.log(LogStatus.INFO, "Current Results : " + final1);
			LOGGER.info("Successful : The Audio Files are filtered by the Document Type .Wav File.");
       		extent.log(LogStatus.PASS, "Successful : The Audio Files are filtered by the Document Type .Wav File.");
       		Element.takescreenshot(driver,className,screenshot_name+"3");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + result);
			extent.log(LogStatus.INFO, "Current Results : " + final1);
			extent.log(LogStatus.FAIL, "Unsuccessful : TThe Audio Files are not filtered by the Document Type .Wav File.");
       		LOGGER.error("Unsuccessful : The Audio Files are not filtered by the Document Type .Wav File.");
       		Element.takescreenshot(driver,className,screenshot_name+"4");
		}

	}
	

	@Test
	public void c_FilterbySize() throws InterruptedException, IOException
	{
		LOGGER.info("Filter Audio file by Size");
		extent.startTest("Filter Audio file by Size");
		
 		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[1]")).click();
		Thread.sleep(3000);
		
		String audio_result1 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final_result1 = driver.findElement(By.xpath(audio_result1)).getText().toString();
		
		if(size1.equals(final_result1))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size1);
			extent.log(LogStatus.INFO, "Current Results : " + final_result1);
			LOGGER.info("Successful : .The Audio files are sorted by Size 0.00 B -  10.00 MB");
       		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Size 0.00 B -  10.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"5");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size1);
			extent.log(LogStatus.INFO, "Current Results : " + final_result1);
			extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Size 0.00 B -  10.00 MB.");
       		LOGGER.error("Unsuccessful : The Audio files are not sorted by Size 0.00 B -  10.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"6");
		}
		
		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[1]")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[2]")).click();
		Thread.sleep(3000);
		
		String audio_result2 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final_result2 = driver.findElement(By.xpath(audio_result2)).getText().toString();
		
		if(size2.equals(final_result2))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size2);
			extent.log(LogStatus.INFO, "Current Results : " + final_result2);
			LOGGER.info("Successful : .The Audio files are sorted by Size 0.00 B -  10.00 MB");
       		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Size 10.00 B -  20.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"7");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size2);
			extent.log(LogStatus.INFO, "Current Results : " + final_result2);
			extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Size 10.00 B -  20.00 MB.");
       		LOGGER.error("Unsuccessful : The Audio files are not sorted by Size 10.00 B -  20.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"8");
		}
		
		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[1]")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[3]")).click();
		Thread.sleep(3000);
		
		String audio_result3 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
		String final_result3 = driver.findElement(By.xpath(audio_result3)).getText().toString();
		
		if(size3.equals(final_result3))
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size3);
			extent.log(LogStatus.INFO, "Current Results : " + final_result3);
			LOGGER.info("Successful : .The Audio files are sorted by Size 20.00 B -  30.00 MB");
       		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Size 20.00 B -  30.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"7");
		}
		else
		{
			extent.log(LogStatus.INFO, "Expected Results : " + size3);
			extent.log(LogStatus.INFO, "Current Results : " + final_result3);
			extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Size 20.00 B -  30.00 MB.");
       		LOGGER.error("Unsuccessful : The Audio files are not sorted by Size 20.00 B -  30.00 MB.");
       		Element.takescreenshot(driver,className,screenshot_name+"8");
		}
		driver.findElement(By.xpath("//div[@class='ui segment']//div[3]//div[3]//ul//li[1]")).click();
		Thread.sleep(3000);
	}
	
	@Test
	public void d_filterByLanguage() throws InterruptedException, IOException
	{
		LOGGER.info("Filter Audio file by Language");
		extent.startTest("Filter Audio file by Language");
		
		
			//driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[1]")).click();
			//Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[1]//span//span[text()='English']")).click();
			Thread.sleep(3000);
			
			String audio_result4 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
			String final_result4 = driver.findElement(By.xpath(audio_result4)).getText().toString();
			
			if(lang1.equals(final_result4))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang1);
				extent.log(LogStatus.INFO, "Current Results : " + final_result4);
				LOGGER.info("Successful : .The Audio files are sorted by Language English.");
        		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Language English.");
        		Element.takescreenshot(driver,className,screenshot_name+"9");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang1);
				extent.log(LogStatus.INFO, "Current Results : " + final_result4);
				extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Language English.");
        		LOGGER.error("Unsuccessful : The Audio files are not sorted by Language English.");
        		Element.takescreenshot(driver,className,screenshot_name+"10");
			}
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[1]//span//span[text()='English']")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[2]//span//span[text()='Estonian']")).click();
			Thread.sleep(3000);
			
			String audio_result5 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
			String final_result5 = driver.findElement(By.xpath(audio_result5)).getText().toString();
			
			if(lang2.equals(final_result5))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang2);
				extent.log(LogStatus.INFO, "Current Results : " + final_result5);
				LOGGER.info("Successful : .The Audio files are sorted by Language Estonian.");
        		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Language Estonian.");
        		Element.takescreenshot(driver,className,screenshot_name+"9");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang2);
				extent.log(LogStatus.INFO, "Current Results : " + final_result4);
				extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Language Estonian.");
        		LOGGER.error("Unsuccessful : The Audio files are not sorted by Language Estonian.");
        		Element.takescreenshot(driver,className,screenshot_name+"10");
			}
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[1]//span//span[text()='Estonian']")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[3]//span//span[text()='French']")).click();
			Thread.sleep(3000);
			
			String audio_result6 = "//div[@id='se-results']//div[4]//div[1]//div[2]//ng-switch//a[@class='header title']//span";
			String final_result6 = driver.findElement(By.xpath(audio_result6)).getText().toString();
			
			if(lang1.equals(final_result4))
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang3);
				extent.log(LogStatus.INFO, "Current Results : " + final_result6);
				LOGGER.info("Successful : .The Audio files are sorted by Language French.");
        		extent.log(LogStatus.PASS, "Successful : The Audio files are sorted by Language French.");
        		Element.takescreenshot(driver,className,screenshot_name+"9");
			}
			else
			{
				extent.log(LogStatus.INFO, "Expected Results : " + lang3);
				extent.log(LogStatus.INFO, "Current Results : " + final_result6);
				extent.log(LogStatus.FAIL, "Unsuccessful : The Audio files are not sorted by Language French.");
        		LOGGER.error("Unsuccessful : The Audio files are not sorted by Language French.");
        		Element.takescreenshot(driver,className,screenshot_name+"10");
			}
			driver.findElement(By.xpath("//div[@class='ui segment']//div//div[2]//ul//li[1]//span//span[text()='French']")).click();
			Thread.sleep(3000);						
	}
	

}

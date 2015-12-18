package com.zaizi.sensefy.sensefyui;

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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;

/*
 * Testing Facet test
 */
@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class fFacetsTest {
	
	public static final Logger LOGGER = LogManager.getLogger(fFacetsTest.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(fFacetsTest.class);
	
	private String username;
    private String password;
    public String searchWord;
    private String creator;
    private String doctype;
    private String language;
    private String size;
    private String sharpness;
    private String modifiedDate;
    private String creationDate;
    private String topic;
    public String searchTerm="//input[@id='searchTerm']";
    
    static WebDriver driver;
	public fFacetsTest(String username, String password, String searchWord, String creator,
			String doctype, String language, String size, String sharpness, String modifiedDate, String creationDate,
			String topic)
    {
        this.username = username;
        this.password = password;
        this.searchWord=searchWord;
        this.creator=creator;
        this.doctype=doctype;
        this.language=language;
        this.size=size;
        this.sharpness=sharpness;
        this.modifiedDate=modifiedDate;
        this.creationDate=creationDate;
        this.topic=topic;
    }
	
	@BeforeClass
    public static void beforeClass() {
        //extent.init("/Users/deranthika/Desktop/myreport1.html", true);
		extent.init("logs/sensefy.html", true);
        extent.config().documentTitle("SensefyUI Automation Test Report");
        extent.config().reportTitle("SensefyUI Automation");
        extent.config().reportHeadline("Search Page Testing");
    } 	    
	
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_PREPARING, "Facet Testing");
        return TestCaseValues.documentLibraryTestValues("FacetTest");
    }

    //Test Creator Facet
    @Test
    public void a_filterybyCreator() throws InterruptedException
    {
    	LOGGER.info("Running Filter by Cretor Facets");
    	extent.startTest("Verify Creator Facets");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
            SearchLogin loginPage = new SearchLogin(driver);
            loginPage.searchuiLogin(username, password);
            Thread.sleep(2000);
            loginPage.checkElementPresent(searchTerm);
            Thread.sleep(2000);
            SearchPage searchPage=new SearchPage(driver);
            searchPage.searchATerm(searchWord);
            Thread.sleep(5000);
            Thread.sleep(5000);
            searchPage.countResults();
            searchPage.selectCreatorFacet(creator);
            Thread.sleep(5000);
            searchPage.countResults();
            LOGGER.info("Verifying Creator Facets Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Creator Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "Creator Facet Verification Success");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "Creator Facet Verification Failed");
        	LOGGER.error("Verifying Creator Facets Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");   
    }
  
    //Test Document Type Facet
    @Test
    public void b_filterybyDocumentType() throws InterruptedException
    {
    	LOGGER.info("Running Filter by DocumentType Facets");
    	extent.startTest("Verify DocumentType Facets");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
        	SearchLogin loginPage = new SearchLogin(driver);
        	loginPage.searchuiLogin(username, password);
        	Thread.sleep(2000);
    	    loginPage.checkElementPresent(searchTerm);
    	    Thread.sleep(2000);
    	    SearchPage searchPage=new SearchPage(driver);
    	    searchPage.searchATerm(searchWord);
    	    Thread.sleep(5000);
    	    Thread.sleep(5000);
    	    searchPage.countResults();
    	    searchPage.selectDocTypeFacet(doctype);
    	    Thread.sleep(5000);
    	    searchPage.countResults();
    	    LOGGER.info("Verifying DocumentType Facets Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "DocumentType Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "DocumentType Facet Verification Success");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "DocumentType Facet Verification Failed");
        	LOGGER.error("Verifying DocumentType Facets Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");    
    }
  
    //Test Language Facet
	@Test
	public void c_filterybyLanguage() throws InterruptedException
	{
		LOGGER.info("Running Filter by Language Facets");
    	extent.startTest("Verify Language Facets");
    	try
    	{
    		driver = TestCaseProperties.getWebDriverForSearch();
    	    SearchLogin loginPage = new SearchLogin(driver);
    	    loginPage.searchuiLogin(username, password);
    	    Thread.sleep(2000);
    	    loginPage.checkElementPresent(searchTerm);
    	    Thread.sleep(2000);
    	    SearchPage searchPage=new SearchPage(driver);
    	    searchPage.searchATerm(searchWord);
    	    Thread.sleep(5000);
    	    Thread.sleep(5000);
    	    searchPage.countResults();
    	    searchPage.selectLanguageFacet(language);
    	    Thread.sleep(5000);
    	    searchPage.countResults();
    	    LOGGER.info("Verifying Language Facets Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Language Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "Language Facet Verification Success");
    	}
    	catch(Exception e)
    	{
    		extent.log(LogStatus.FAIL, "Language Facet Verification Failed");
        	LOGGER.error("Verifying Language Facets Failed");
    	}
    	TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");       
	 }
	 
	 //Test Size Facet
	 @Test
	 public void d_filterybySize() throws InterruptedException
	 {
		LOGGER.info("Running Filter by Size Facets");
	    extent.startTest("Verify Size Facets");
	    try
	    {
	    	driver = TestCaseProperties.getWebDriverForSearch();
		    SearchLogin loginPage = new SearchLogin(driver);
		    loginPage.searchuiLogin(username, password);
		    Thread.sleep(2000);
		    loginPage.checkElementPresent(searchTerm);
		    Thread.sleep(2000);
		    SearchPage searchPage=new SearchPage(driver);
		    searchPage.searchATerm(searchWord);
		    Thread.sleep(5000);
		    Thread.sleep(5000);
		    searchPage.countResults();
		    searchPage.selectSizeFacet(size);
		    Thread.sleep(5000);
		    searchPage.countResults();
		    LOGGER.info("Verifying Size Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Size Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "Size Facet Verification Success");
	    }
	    catch(Exception e)
	    {
	    	extent.log(LogStatus.FAIL, "Size Facet Verification Failed");
        	LOGGER.error("Verifying Size Facets Failed");
	    }
	    TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");    
	  }

	 //Test Sharpness Facet
	 @Test
	 public void e_filterybySharpeness() throws InterruptedException
	 {
		LOGGER.info("Running Filter by Sharpness Facets");
		extent.startTest("Verify Sharpness Facets");
		try
		{
			driver = TestCaseProperties.getWebDriverForSearch();
			SearchLogin loginPage = new SearchLogin(driver);
			loginPage.searchuiLogin(username, password);
			Thread.sleep(2000);
			loginPage.checkElementPresent(searchTerm);
			Thread.sleep(2000);
			SearchPage searchPage=new SearchPage(driver);
			searchPage.searchATerm(searchWord);
			Thread.sleep(5000);
			Thread.sleep(5000);
			searchPage.countResults();
			searchPage.selectSharpnessFacet(sharpness);
			Thread.sleep(5000);
			searchPage.countResults();
			LOGGER.info("Verifying Sharpness Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Sharpness Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "Sharpness Facet Verification Success");
		}
		catch(Exception e)
		{
			extent.log(LogStatus.FAIL, "Sharpness Facet Verification Failed");
        	LOGGER.error("Verifying Sharpness Facets Failed");
		}
		TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");     
	 }
  
	 //Test Last Modified Date Facet
	 @Test
	 public void f_filterybyLastModifiedDate() throws InterruptedException
	 {
		LOGGER.info("Running Filter by LastModifiedDate Facets");
		extent.startTest("Verify LastModifiedDate Facets");
		try
		{
			driver = TestCaseProperties.getWebDriverForSearch();
			SearchLogin loginPage = new SearchLogin(driver);
			loginPage.searchuiLogin(username, password);
			Thread.sleep(2000);
			loginPage.checkElementPresent(searchTerm);
			Thread.sleep(2000);
			SearchPage searchPage=new SearchPage(driver);
			searchPage.searchATerm(searchWord);
			Thread.sleep(5000);
			Thread.sleep(5000);
			searchPage.countResults();
			searchPage.selectLastModifiedDateFacet(modifiedDate);
			Thread.sleep(5000);
			searchPage.countResults();
			LOGGER.info("Verifying LastModifiedDate Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "LastModifiedDate Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "LastModifiedDate Facet Verification Success");
		}
		catch(Exception e)
		{
			extent.log(LogStatus.FAIL, "LastModifiedDate Facet Verification Failed");
        	LOGGER.error("Verifying LastModifiedDate Facets Failed");
		}
		TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");     
	 }
  
	 //Test Creation Date Facet
	 @Test
	 public void g_filterybyCreationDate() throws InterruptedException
	 {
		LOGGER.info("Running Filter by CreationDate Facets");
		extent.startTest("Verify CreationDate Facets");
		try
		{
			driver = TestCaseProperties.getWebDriverForSearch();
			SearchLogin loginPage = new SearchLogin(driver);
			loginPage.searchuiLogin(username, password);
			Thread.sleep(2000);
			loginPage.checkElementPresent(searchTerm);
			Thread.sleep(2000);
			SearchPage searchPage=new SearchPage(driver);
			searchPage.searchATerm(searchWord);
			Thread.sleep(5000);
			Thread.sleep(5000);
			searchPage.countResults();
			searchPage.selectCreationDateFacet(creationDate);
			Thread.sleep(5000);
			searchPage.countResults();
			LOGGER.info("Verifying CreationDate Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "CreationDate Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "CreationDate Facet Verification Success");
		}
		catch(Exception e)
		{
			extent.log(LogStatus.FAIL, "CreationDate Facet Verification Failed");
        	LOGGER.error("Verifying CreationDate Facets Failed");
		}
		TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");    
	 }
  
	 //Test Topic Facet
	 @Test
	 public void h_filterybyTopic() throws InterruptedException
	 {
		LOGGER.info("Running Filter by Topic Facets");
		extent.startTest("Verify Topic Facets");
		try
		{
			driver = TestCaseProperties.getWebDriverForSearch();
			SearchLogin loginPage = new SearchLogin(driver);
			loginPage.searchuiLogin(username, password);
			Thread.sleep(2000);
			loginPage.checkElementPresent(searchTerm);
			Thread.sleep(2000);
			SearchPage searchPage=new SearchPage(driver);
			searchPage.searchATerm(searchWord);
			Thread.sleep(5000);
			Thread.sleep(5000);
			searchPage.countResults();
			searchPage.selectTopicFacet(topic);
			Thread.sleep(5000);
			searchPage.countResults();
			LOGGER.info("Verifying Topic Success");
    		LOGGER.info(TestCaseProperties.TEXT_TEST_PASS, "Topic Facets Verification Successfully");
    		extent.log(LogStatus.PASS, "Topic Facet Verification Success");
		}
		catch(Exception e)
		{
			extent.log(LogStatus.FAIL, "Topic Facet Verification Failed");
        	LOGGER.error("Verifying Topic Facets Failed");
		}
		TestCaseProperties.closeDriver(driver);
    	LOGGER.info("---------------------------");      
	 }
  
    
	 @After
	 public void Teardown() {
        driver.quit();  
	 }
}

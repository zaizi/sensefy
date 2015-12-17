package com.zaizi.sensefy.sensefyui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.zaizi.sensefy.sensefyui.exceptions.IterableException;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;
import com.zaizi.sensefy.sensefyui.info.TestCaseValues;
import com.zaizi.sensefy.sensefyui.pages.SearchLogin;
import com.zaizi.sensefy.sensefyui.pages.SearchPage;

@RunWith(value = Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class fFacetsTest {
	
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());
	
	public static final ExtentReports extent = ExtentReports.get(TestPage.class);
	
	private String username;
    private String password;
    private String BrowserName;
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
	public fFacetsTest(String username, String password, String BrowserName, String searchWord, String creator,
			String doctype, String language, String size, String sharpness, String modifiedDate, String creationDate,
			String topic)
    {
        this.username = username;
        this.password = password;
        this.BrowserName= BrowserName;
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
	        extent.init("/Users/deranthika/Desktop/myreport1.html", true);
	        extent.config().documentTitle("SensefyUI Report");
	        extent.config().reportTitle("SensefyUI Smoke Test");
	        extent.config().reportHeadline("Facet Testing");
	    }
	    
	
	@Parameters()
    public static Iterable<Object[]> data() throws IterableException
    {
        System.out.println("preparing Facet Testing ..");
        return TestCaseValues.documentLibraryTestValues("FacetTest");
    }

    //Test Creator Facet
    @Test
    public void a_filterybyCreator() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
        System.out.println("Running sort By Functions");
        driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
        TestCaseProperties.closeDriver(driver);
        System.out.println("---------------------------");
        System.out.println();    
    }
  
    //Test Document Type Facet
    @Test
    public void b_filterybyDocumentType() throws InterruptedException
    {
    	extent.startTest("Test Sort By Functions");
    	System.out.println("Running sort By Functions");
    	driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
	    TestCaseProperties.closeDriver(driver);
	    System.out.println("---------------------------");
	    System.out.println();    
    }
  
    //Test Language Facet
	@Test
	public void c_filterybyLanguage() throws InterruptedException
	{
	  	extent.startTest("Test Sort By Functions");
	    System.out.println("Running sort By Functions");
	    driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
	    TestCaseProperties.closeDriver(driver);
	    System.out.println("---------------------------");
	    System.out.println();    
	 }
	 
	 //Test Size Facet
	 @Test
	 public void d_filterybySize() throws InterruptedException
	 {
	  	extent.startTest("Test Sort By Functions");
	    System.out.println("Running sort By Functions");
	    driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
	    TestCaseProperties.closeDriver(driver);
	    System.out.println("---------------------------");
	    System.out.println();   
	  }

	 //Test Sharpness Facet
	 @Test
	 public void e_filterybySharpeness() throws InterruptedException
	 {
		extent.startTest("Test Sort By Functions");
		System.out.println("Running sort By Functions");
		driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
		TestCaseProperties.closeDriver(driver);
		System.out.println("---------------------------");
		System.out.println();     
	 }
  
	 //Test Last Modified Date Facet
	 @Test
	 public void f_filterybyLastModifiedDate() throws InterruptedException
	 {
		extent.startTest("Test Sort By Functions");
		System.out.println("Running sort By Functions");
		driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
		TestCaseProperties.closeDriver(driver);
		System.out.println("---------------------------");
		System.out.println();      
	 }
  
	 //Test Creation Date Facet
	 @Test
	 public void g_filterybyCreationDate() throws InterruptedException
	 {
		extent.startTest("Test Sort By Functions");
		System.out.println("Running sort By Functions");
		driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
		TestCaseProperties.closeDriver(driver);
		System.out.println("---------------------------");
		System.out.println();    
	 }
  
	 //Test Topic Facet
	 @Test
	 public void h_filterybyTopic() throws InterruptedException
	 {
		extent.startTest("Test Sort By Functions");
		System.out.println("Running sort By Functions");
		driver = TestCaseProperties.getWebDriverForSearch(BrowserName);
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
		TestCaseProperties.closeDriver(driver);
		System.out.println("---------------------------");
		System.out.println();      
	 }
  
    
	 @After
	 public void Teardown() {
        driver.quit();  
	 }
}

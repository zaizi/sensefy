package com.zaizi.sensefy.sensefyui.pages;

import java.util.Date;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.info.UrlFinder;

public class SearchPage {

private static final Logger LOGGER = LogManager.getLogger(SearchLogin.class);
	
	//xpath initiation for searchui login page
	private static final String username = "//input[@id='username']";
    private static final String password="//input[@id='password']";
    private static final String loginbtn="//input[@id='loginButton']";
    private static final String searchBar="//input[@id='searchTerm']";
    private static final String searchButton="//div[@class='ui yellow button ser-btn']";
    private static final String TEXT_TEST_PASSED = "Test case passed!";
    private static final String TEXT_TEST_FAILED = "Test case failed!";
    
    private static String languageName="//div[@id='menu']/a/span[2]";
    private static String adminDropdown="//div[@id='menu']/div/i[2]";
    private static String languageSpanish="//div[@id='menu']/div/div/a[@translate='spanish']";
    private static String languageEnglish="//div[@id='menu']/div/div/a[@translate='english']";
    
    private static String footerNote="//body[@id='sensefy']/div[2]/footer/p";
    
    //sort by
    private static String sortIcon="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/i";
    private static String sortby="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[2]/div/div[contains(text(),'";
    private static String sortby2="')]";
    
    Date date=new Date();

	private WebDriver driver;

    /**
     * @param driver
     */
    public SearchPage(WebDriver driver)
    {
        this.driver = driver;
    }

    /**
     * @param driver
     */
    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    /**
     * method prepareElements
     */
    public void prepareElements()
    {
    	UrlFinder unf=new UrlFinder();
        driver.get(unf.urlcaterher());
    }
    
    //attemp to login to searchui
    public void searchuiLogin(String user, String pass) throws InterruptedException
    {
    	TextField usernameField=new TextField(driver, By.xpath(username));
    	TextField passwordField=new TextField(driver, By.xpath(password));
    	Button loginButton=new Button(driver, By.xpath(loginbtn));
    	usernameField.enterText(user);
    	passwordField.enterText(pass);
    	loginButton.Click();
    	
    }
    
    public void checkElementPresent(String elementName)
    {
    	if(driver.findElement(By.xpath(elementName)).isEnabled())
    	{
    		System.out.println("Element Found");
    	}
    	else
    	{
    		System.out.println("No Element Can Found");
    	}
    }
    
    public void searchATerm(String Term)
    {
    	driver.findElement(By.xpath(searchBar)).sendKeys(Term);
    	driver.findElement(By.xpath(searchButton)).click();
    }
    
    public void logout()
    {
    	driver.findElement(By.xpath("//div[@id='menu']/div")).click();
    	driver.findElement(By.xpath("//div[@id='menu']/div/div/a[4]")).click();
    }
    
    public void changeLanguage() throws InterruptedException
    {
    	String currentLanguage=driver.findElement(By.xpath(languageName)).getText();
    	if(currentLanguage.equals(": English"))
    	{
    		driver.findElement(By.xpath(adminDropdown)).click();
    		Thread.sleep(1000);
    		driver.findElement(By.xpath(languageSpanish)).click();
    		System.out.println("Changed language to Spanish");
    		Thread.sleep(1000);
    	}
    	else if(currentLanguage.equals(": Spanish"))
    	{
    		driver.findElement(By.xpath(adminDropdown)).click();
    		Thread.sleep(1000);
    		driver.findElement(By.xpath(languageEnglish)).click();
    		System.out.println("Changed language to English");
    		Thread.sleep(1000);
    	}
    }
    
    //Verifying the Footer 
    public boolean footerNote()
    {
    	Boolean match=null;
    	String footNote="© 2007 - 2015 Zaizi Limited. All rights reserved.";
    	String note=driver.findElement(By.xpath(footerNote)).getText();
    	if(note.equals(footNote))
    	{
    		match=true;
    	}
    	else
    	{
    		match=false;
    	}
    	return match;
    }
    
    public void resultsPerPage(int number) throws InterruptedException
    {
    	String dropdown="//div[@id='se-results']/div[1]/div/div/div[2]/div[1]/div/div/div/i";
    	String dropdownValue="//div[@data-value='";
    	String xpath=dropdownValue+number+"']";
    	driver.findElement(By.xpath(dropdown)).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath(xpath)).click();
    }
    
    public int countResults()
    {
    	String a="//div[@class='ui divided items documents']/div[";
        String pathA;
        int numberOfResults=0;
        for(int v=1;v<=101;v++)
        {
        	pathA=a+v+"]";
        	if(driver.findElement(By.xpath(pathA)).isDisplayed()==false)
        	{
        		numberOfResults=v-1;
        		break;
        	}
        	else
        	{
        		
        	}
        }
        System.out.println("return"+numberOfResults);
        return numberOfResults;   
    }
    
    public void sortbynames(String sort)
    {
    	String Relevance="";
    	String Name="";
    	String Title="";
    	String Created="";
    	String Modified="";
    	String Creator="";
    	String Modifier="";
    	if(sort.equals("Relevance"))
    	{
    		Relevance=sort;
    	}
    	if(sort.equals("Name"))
    	{
    		Name=sort;
    	}
    	if(sort.equals("Title"))
    	{
    		Title=sort;
    	}
    	if(sort.equals("Created"))
    	{
    		Created=sort;
    	}
    	if(sort.equals("Modified"))
    	{
    		Modified=sort;
    	}
    	if(sort.equals("Creator"))
    	{
    		Creator=sort;
    	}
    	if(sort.equals("Modifier"))
    	{
    		Modifier=sort;
    	}
    	
    }
    
    public void selectASort(String sorter) throws InterruptedException
    {
    	driver.findElement(By.xpath(sortIcon)).click();
    	Thread.sleep(1000);
    	String xpath=sortby+sorter+sortby2;
    	driver.findElement(By.xpath(xpath)).click();
    	Thread.sleep(1000);
    }
    
    //Select Creator Facet
    public void selectCreatorFacet(String creator) throws InterruptedException
    {
    	String creatorName1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[1]/ul/li/span/span[@translate='";
    	String creatorName2="']";
    	String creatorName=creatorName1+creator+creatorName2;
    	String expaned="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[1]/div/i[@class='add circle icon']";
    	
    	driver.findElement(By.xpath(expaned)).click();
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(creatorName)).click();
    }
    
    //Select Document Type Facet
    public void selectDocTypeFacet(String doctype) throws InterruptedException
    {
    	String doctype1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[2]/ul/li/span/span[contains(.,'";
    	String doctype2="')]";
    	String documentType=doctype1+doctype+doctype2;
    	String expaned="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[2]/div/i[@class='add circle icon']";
    	
    	driver.findElement(By.xpath(expaned)).click();
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(documentType)).click();
    }
    
    //Selecting Language Facet
    public void selectLanguageFacet(String language) throws InterruptedException
    {
    	String language1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[3]/ul/li/span/span[contains(.,'";
    	String language2="')]";
    	String languageType=language1+language+language2;
    	String expaned="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[3]/div/i[@class='add circle icon']";
    	
    	driver.findElement(By.xpath(expaned)).click();
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(languageType)).click();
    }
    
    //Selecting Size Facet
    public void selectSizeFacet(String size) throws InterruptedException
    {
    	String size1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[4]/ul/li/span/span[contains(.,'";
    	String size2="')]";
    	String sizeType=size1+size+size2;
    	
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(sizeType)).click();
    }
    
    //Selecting Sharpness Facet
    public void selectSharpnessFacet(String sharpness) throws InterruptedException
    {
    	String sharpness1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[5]/ul/li/span/span[contains(.,'";
    	String sharpness2="')]";
    	String sharpnessType=sharpness1+sharpness+sharpness2;
    	
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(sharpnessType)).click();
    }
    
    //Selecting Modified Date Facet
    public void selectLastModifiedDateFacet(String modifiedDate) throws InterruptedException
    {
    	String modified1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[6]/ul/li/span/span[contains(.,'";
    	String modified2="')]";
    	String modifiedType=modified1+modifiedDate+modified2;
    	
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(modifiedType)).click();
    }
    
    //Selecting Creation Date Facet
    public void selectCreationDateFacet(String creationDate) throws InterruptedException
    {
    	String creation1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[7]/ul/li/span/span[contains(.,'";
    	String creation2="')]";
    	String creationType=creation1+creationDate+creation2;
    	
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(creationType)).click();
    }
    
    //Selecting Topic Facet
    public void selectTopicFacet(String topic) throws InterruptedException
    {
    	String topic1 ="//body[@id='sensefy']/div[1]/div[3]/div[2]/div[4]/div[3]/div[8]/ul/li/span/span[contains(.,'";
    	String topic2="')]";
    	String topicType=topic1+topic+topic2;
    	
    	Thread.sleep(5000);
    	driver.findElement(By.xpath(topicType)).click();
    }
    
    //---------
    public void searchforSite(String siteName)
	{
		
		Button sites = new Button(driver, By.xpath("//span[@id='HEADER_SITES_MENU_text']"));
		sites.Click();
		Button siteFinder = new Button(driver, By.xpath("//td[@id='HEADER_SITES_MENU_SITE_FINDER_text']//a"));
		siteFinder.Click();
		TextField sitename = new TextField(driver, By.xpath("//input[@id='template_x002e_site-finder_x002e_site-finder_x0023_default-term']"));
		sitename.enterText(siteName);
		Button sitesearchbutton = new Button(driver, By.xpath("//button[@id='template_x002e_site-finder_x002e_site-finder_x0023_default-button-button']"));
		sitesearchbutton.Click();
		Button foundsite = new Button(driver, By.xpath("//h3//a[contains(.,'"+siteName+"')]"));
		foundsite.Click();
		
	}
    
}

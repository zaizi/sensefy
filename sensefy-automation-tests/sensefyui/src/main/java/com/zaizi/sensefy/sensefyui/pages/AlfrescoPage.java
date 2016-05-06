package com.zaizi.sensefy.sensefyui.pages;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;

import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.Element;
import com.zaizi.sensefy.sensefyui.elements.Link;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.info.TestCaseProperties;

public class AlfrescoPage 
{
	private static final Logger LOGGER = LogManager.getLogger(AlfrescoPage.class);
	
	public WebDriver driver;
	
	public AlfrescoPage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }
	
	public void navigateToSite()
	{
		Button sites = new Button(driver, By.xpath("//span[@id='HEADER_SITES_MENU_text']"));
		sites.Click();
		Button siteFinder = new Button(driver, By.xpath("//td[@id='HEADER_SITES_MENU_SITE_FINDER_text']//a"));
		siteFinder.Click();
		System.out.println("Navigated to the Sites Finder >>>>>>>>>>>>>>>>>>");
	}
	
	public void searchforSite(String siteName) throws InterruptedException
	{
		TextField sitename = new TextField(driver, By.xpath("//input[@id='template_x002e_site-finder_x002e_site-finder_x0023_default-term']"));
		sitename.enterText(siteName);
		Button sitesearchbutton = new Button(driver, By.xpath("//button[@id='template_x002e_site-finder_x002e_site-finder_x0023_default-button-button']"));
		sitesearchbutton.Click();
		System.out.println("01");
		Thread.sleep(3000);
		Link foundsite = new Link(driver, By.xpath("//a[text()='"+siteName+"']"));
		foundsite.Click();
		System.out.println("02");		
	}
	
	public void navigatetoDocumentLibrary()
	{
		Button documentLibrary = new Button(driver, By.xpath("//span[@id='HEADER_SITE_DOCUMENTLIBRARY_text']//a"));
		documentLibrary.Click();
		//Button uploadDocument = new Button(driver, By.xpath("//button[@id='template_x002e_documentlist_v2_x002e_documentlibrary_x0023_default-fileUpload-button-button']"));
		//uploadDocument.Click();		
	}
	
	//------------------------------------------------------------------------
	public void uploadDocument(String documentName) throws InterruptedException, URISyntaxException
	{
		try
		{
			Button selectfile = new Button(driver, By.xpath("//span[@id='template_x002e_dnd-upload_x002e_documentlibrary_x0023_default-file-selection-button-overlay']//span"));
			selectfile.Click();
			Thread.sleep(3000);
			
			System.out.println("1111");
        
			WebElement El1 = driver.findElement(By.xpath("//span//input[@class='dnd-file-selection-button']"));
			System.out.println("2222");
			Thread.sleep(3000);
			((RemoteWebElement) El1 ).setFileDetector(new LocalFileDetector()); 
			Thread.sleep(3000);
			System.out.println("3333");
			El1.sendKeys("/Users/ljayasinghe/Documents/FileA.rtf");
			System.out.println("4444");
			//TestCaseProperties.closeDriver(driver);
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
		
	}
		
	//----------------------------------------------------------------------------------
	public void createSampleDoc(String docname) throws InterruptedException
	{
		Button createButton = new Button(driver, By.xpath("//button[@id='template_x002e_documentlist_v2_x002e_documentlibrary_x0023_default-createContent-button-button']"));
		createButton.Click();
		Thread.sleep(3000);
		Button plaintextbutton = new Button(driver, By.xpath("//div//ul//li[2]//a//span[@class='text-file']"));
		plaintextbutton.Click();
		Thread.sleep(3000);
		TextField filename = new TextField(driver, By.xpath("//input[@id='template_x002e_create-content_x002e_create-content_x0023_default_prop_cm_name']"));
		filename.enterText(docname);
		Thread.sleep(3000);
		Button createfile = new Button(driver, By.xpath("//button[@id='template_x002e_create-content_x002e_create-content_x0023_default-form-submit-button']"));
		createfile.Click();
		
		System.out.println("successfully created");
	}
	
	public void alfrescoLogout()
	{
		Button btnUser = new Button(driver, By.xpath("//div//span[@id='HEADER_USER_MENU_POPUP_text']"));
	    btnUser.Click();

	    Link logoutLink = new Link(driver, By.xpath("//td[@id='HEADER_USER_MENU_LOGOUT_text']"));
        logoutLink.Click();
	}
	
	public void navigateToJobs() throws InterruptedException
	{
		Thread.sleep(3000);
		System.out.println("xxxx");
		Link jobs = new Link(driver, By.xpath("//a[text()='Status and Job Management']"));
		jobs.Click();		
	}
	
	public void deleteDocument(String documentName) throws InterruptedException
	{
		Button delName = new Button(driver, By.xpath("//tbody//tr[2]//td[4]//div//h3//span//a[text()='"+documentName+"']"));
		delName.Click();
		Thread.sleep(3000);
		
		System.out.println("Navigated to the Document "+documentName);
		
		Button deleteButton = new Button(driver, By.xpath("//div[@id='onActionDelete']//a//span"));
		deleteButton.Click();
		 
		Thread.sleep(3000);
		
		Button confirmDelete = new Button(driver, By.xpath("//span//button[text()='Delete']"));
		confirmDelete.Click();
		
		System.out.println("Deleted the Document "+documentName);
	}
	
	public void gotoAdminTools()
	{
		Link admintools = new Link(driver, By.xpath("//span[@id='HEADER_ADMIN_CONSOLE_text']//a"));
		admintools.Click();		
	}
	
	public void gotoUsers()
	{
		Link gotoUsers = new Link(driver, By.xpath("//div[@id='page_x002e_tools_x002e_admin-console_x0023_default-body']//ul[3]//li[2]//span//a[@title='User Management']"));
		gotoUsers.Click();
	}
	
	public void createUser(String name,String email,String username,String password) throws InterruptedException 
	{
		Button newUser = new Button(driver, By.xpath("//button[@id='page_x002e_ctool_x002e_admin-console_x0023_default-newuser-button-button']"));
		newUser.Click();
		Thread.sleep(3000);
		System.out.println("Clicked on New User");
		
		TextField fname = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-create-firstname']"));
		fname.enterText(name);
		Thread.sleep(3000);
		System.out.println("Entered the name "+name);
		
		TextField femail = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-create-email']"));
		femail.enterText(email);
		Thread.sleep(3000);
		System.out.println("Entered the email "+email);
		
		TextField fusername = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-create-username']"));
		fusername.enterText(username);
		Thread.sleep(3000);
		System.out.println("Entered the username "+username);
		
		TextField fpassword = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-create-password']"));
		fpassword.enterText(password);
		Thread.sleep(3000);
		System.out.println("Entered the password :"+password);
		
		TextField fconfirmpassword = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-create-verifypassword']"));
		fconfirmpassword.enterText(password);
		Thread.sleep(3000);
		System.out.println("Entered the Confirm password :"+password);
		
		Button createUser = new Button(driver, By.xpath("//button[@id='page_x002e_ctool_x002e_admin-console_x0023_default-createuser-ok-button-button']"));
		createUser.Click();
		
		System.out.println("Successfully created the User : "+name);
	}
	
	public void verifyUser(String name) throws InterruptedException
	{
		TextField searchName = new TextField(driver, By.xpath("//input[@id='page_x002e_ctool_x002e_admin-console_x0023_default-search-text']"));
		searchName.enterText(name);
		Thread.sleep(3000);
		
		Button searchButton = new Button(driver, By.xpath("//button[@id='page_x002e_ctool_x002e_admin-console_x0023_default-search-button-button']"));
		searchButton.Click();
	}
	
	public void gotocreateSite()
	{
		Button sites = new Button(driver, By.xpath("//span[@id='HEADER_SITES_MENU_text']"));
		sites.Click();
		
		Link createSiteButton = new Link(driver, By.xpath("//td[@id='HEADER_SITES_MENU_CREATE_SITE_text']"));
		createSiteButton.Click();
	}
	
	public void createPrivateSite(String siteName) throws InterruptedException
	{
		TextField sitename = new TextField(driver, By.xpath("//input[@id='alfresco-createSite-instance-title']"));
		sitename.enterText(siteName);
		//System.out.println("site name entered.");
		
		Button privateButton = new Button(driver, By.xpath("//input[@id='alfresco-createSite-instance-isPrivate']"));
		privateButton.Click();
		
		Button okButton = new Button(driver, By.xpath("//button[@id='alfresco-createSite-instance-ok-button-button']"));
		okButton.Click();
		
		System.out.println(siteName+" created successful");
		
		Thread.sleep(3000);
	}

	public void inviteMember(String name) throws InterruptedException
	{
		Button inviteImg = new Button(driver, By.xpath("//div[@id='HEADER_SITE_INVITE']//img[@title='Invite users']"));
		inviteImg.Click();
		Thread.sleep(3000);
		
		TextField inviteName = new TextField(driver, By.xpath("//input[@id='template_x002e_people-finder_x002e_invite_x0023_default-search-text']"));
		inviteName.enterText(name);
		Thread.sleep(3000);
		
		Button inviteSearch = new Button(driver,By.xpath("//button[@id='template_x002e_people-finder_x002e_invite_x0023_default-search-button-button']"));
		inviteSearch.Click();
		Thread.sleep(3000);
		
		Button addUser = new Button(driver, By.xpath("//td[3]//div//span[@class='yui-button yui-button-button']//span//button"));
		addUser.Click();
		Thread.sleep(3000);
		
		Button selectRole = new Button(driver, By.xpath("//td[2]//div//div//span//button[contains(.,'Select Role')]"));
		selectRole.Click();
		Thread.sleep(3000);
		
		Link selecttoInvite = new Link(driver, By.xpath("//td//div//ul//li[1]//a[text()='Manager']"));
		selecttoInvite.Click();
		
		Button invite = new Button(driver, By.xpath("//button[@id='template_x002e_invitationlist_x002e_invite_x0023_default-invite-button-button']"));
		invite.Click();
		
		System.out.println("Succefully sent the invitation to "+name);
	}
	
	public void acceptInvitation(String sname) throws InterruptedException
	{
		Button activeTasks = new Button(driver,By.xpath("//div[2]//span//span[1]//a[text()='Active Tasks']"));
		activeTasks.Click();
		Thread.sleep(3000);
		
		Link specificInvitaition = new Link(driver, By.xpath("//tr//td[2]//h3//a[contains(.,'"+sname+"')]"));
		specificInvitaition.Click();
		Thread.sleep(3000);
		
		Button acceptButton = new Button(driver, By.xpath("//button[@id='page_x002e_data-form_x002e_task-edit_x0023_default_prop_inwf_inviteOutcome-accept-button']"));
		acceptButton.Click();
		
		System.out.println("Successfully accepted the Invitation");		
	}
}

package com.zaizi.sensefy.sensefyui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.TextField;

public class sensefypage 
{
	private static final Logger LOGGER = LogManager.getLogger(sensefypage.class);
	
	public WebDriver driver;
	
	public sensefypage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }
	
	public void logintosensefy(String username, String password) throws InterruptedException
	{
		System.out.println(username);
		System.out.println(password);
		
		TextField sensefyusername = new TextField(driver, By.xpath("//input[@id='username']"));
		sensefyusername.enterText(username);
		
		TextField sensefypassword = new TextField(driver, By.xpath("//input[@id='password']"));
		sensefypassword.enterText(password);
		
		Thread.sleep(3000);
		
		Button loginbutton = new Button(driver, By.xpath("//div//form//input[@id='loginButton']"));
		loginbutton.Click();
		
		 System.out.println("Login successful");
	}
	
	public void searchtheDocument(String searchDocName) throws InterruptedException
	{
		TextField searchfield = new TextField(driver, By.xpath("//input[@id='searchTerm']"));
		searchfield.enterText(searchDocName);
		
		Thread.sleep(3000);
		
		Button searchButton = new Button(driver, By.xpath("//div[@class='ui yellow button ser-btn']"));
		searchButton.Click();
		
		Thread.sleep(3000);
		System.out.println("Search Done");
		
	}
	
	public void logoutfromSensefy() throws InterruptedException
	{
		Button adminButton = new Button(driver, By.xpath("//div[@id='menu']//div//i[@class='user icon']"));
		adminButton.Click();
		Thread.sleep(3000);
		
		Button logoutButton = new Button(driver, By.xpath("//div[@id='menu']//div//div//a[text()='Logout']"));
		logoutButton.Click();
		
		System.out.println("Successfully Logout from Sensefy");
	}
}

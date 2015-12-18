package com.zaizi.sensefy.sensefyui.pages;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zaizi.sensefy.sensefyui.elements.Button;
import com.zaizi.sensefy.sensefyui.elements.TextField;
import com.zaizi.sensefy.sensefyui.info.UrlFinder;

public class SearchLogin {
	private static final Logger LOGGER = LogManager.getLogger(SearchLogin.class);
	
	//xpath initiation for searchui login page
	private static final String username = "//input[@id='username']";
    private static final String password="//input[@id='password']";
    private static final String loginbtn="//input[@id='loginButton']";
    private static final String TEXT_TEST_PASSED = "Test case passed!";
    private static final String TEXT_TEST_FAILED = "Test case failed!";
    
    Date date=new Date();

	private WebDriver driver;

    /**
     * @param driver
     */
    public SearchLogin(WebDriver driver)
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
    
    public void logout()
    {
    	driver.findElement(By.xpath("//div[@id='menu']/div")).click();
    	driver.findElement(By.xpath("//div[@id='menu']/div/div/a[4]")).click();
    }
    
    
    

}

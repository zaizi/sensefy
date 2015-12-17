package com.zaizi.sensefy.sensefyui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element {
	//Define WebDriver
	private WebDriver driver;
	//Define WebElement
	private WebElement element;
	
	public Element(WebDriver driver, By elementIdentifier)
	{
		//WebDriver parameterizing
		this.driver=driver;
		//WebElement parameterizing
		element=this.driver.findElement(elementIdentifier);
	}
	
	//Returning WebElement
	public WebElement getElement()
	{
		return this.element;
	}
	
	//Returning WebDrive
	public WebDriver getDriver()
	{
		return this.driver;
	}
	
	//Returning elements' attribute
	public String getElementAttribute(String attribute)
    {
        return element.getAttribute(attribute);
    }
	

}

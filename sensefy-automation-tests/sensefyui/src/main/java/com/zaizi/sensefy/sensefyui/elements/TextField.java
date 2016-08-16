package com.zaizi.sensefy.sensefyui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextField extends Element{
	
	public TextField(WebDriver driver,By elementIdentifier)
    {
        super(driver,elementIdentifier);
    }
	
	//Enter values for a text field
	public void enterText(String text)
	{
		getElement().sendKeys(text);
	}
	
	//Clear text field
	public void clearText()
	{
		getElement().clear();
	}
	
	//Get text fields' sttributes
	public String getText()
	{
		return getElementAttribute("value");
	}



}

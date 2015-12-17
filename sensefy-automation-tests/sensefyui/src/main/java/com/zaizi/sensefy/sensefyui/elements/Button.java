package com.zaizi.sensefy.sensefyui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Button extends Element{
	
	public Button(WebDriver driver,By elementIdentifier)
	{
		super(driver, elementIdentifier);
	}
	
	//Button click
	public void Click()
	{
		getElement().click();
	}

}

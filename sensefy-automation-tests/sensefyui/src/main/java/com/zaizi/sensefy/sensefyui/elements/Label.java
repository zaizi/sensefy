package com.zaizi.sensefy.sensefyui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Label extends Element {

	public Label(WebDriver driver, By elementIdentifier) {
		super(driver, elementIdentifier);
		// TODO Auto-generated constructor stub
	}
	
	//get leabel's attributes
	public String getText()
	{
		return getElementAttribute("value");
	}

}

package com.zaizi.sensefy.sensefyui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Dropdown extends Element{
	
	Select dropdown;

	public Dropdown(WebDriver driver, By elementIdentifier) {
		super(driver, elementIdentifier);
		// TODO Auto-generated constructor stub
		dropdown=(Select) getElement();
	}
	
	//select dropdown by it's visible text
	public void selectByVisibleText(String text)
	{
		dropdown.selectByVisibleText(text);
	}
	
	//select dropdown by it's ID
	public void selectByIndex(int id)
	{
		dropdown.selectByIndex(id);
	}
	
	//select dropdown by it's value
	public void selectByValue(String value)
	{
		dropdown.selectByValue(value);
	}
	
	//Click the dropdown value
	public void Click()
	{
		getElement().click();
	}

}

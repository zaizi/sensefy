package com.zaizi.sensefy.sensefyui.drivers;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class IEDriverStore 
{
	private InternetExplorerDriver driver;

    /**
     * @return
     */
    public InternetExplorerDriver createWebDriver()
    {
        driver = new InternetExplorerDriver();
        return driver;
    }

}

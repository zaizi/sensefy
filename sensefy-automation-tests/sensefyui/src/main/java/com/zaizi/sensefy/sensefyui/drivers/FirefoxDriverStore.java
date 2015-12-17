package com.zaizi.sensefy.sensefyui.drivers;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverStore {
	private FirefoxDriver driver;

    /**
     * @return
     */
    public FirefoxDriver createWebDriver()
    {
        driver = new FirefoxDriver();
        return driver;
    }

}

package com.zaizi.sensefy.sensefyui.drivers;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverStore {

	private ChromeDriver driver;
	/**
     * Defining driverPath
     */
    private String driverPath;

    /**
     * @param driverPath
     */
    public ChromeDriverStore(String driverPath)
    {
        this.driverPath = driverPath;
        System.setProperty("webdriver.chrome.driver", this.driverPath);
    }

	/**
     * @return
     */
    public ChromeDriver createWebDriver()
    {
        this.driver = new ChromeDriver();
        return driver;
    }
}

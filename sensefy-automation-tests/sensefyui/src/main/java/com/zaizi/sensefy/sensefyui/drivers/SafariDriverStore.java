package com.zaizi.sensefy.sensefyui.drivers;

import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverStore {

	private SafariDriver driver;

    /**
     * @return
     */
    public SafariDriver createWebDriver()
    {
        driver = new SafariDriver();
        return driver;
    }
}

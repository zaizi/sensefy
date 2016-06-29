package com.zaizi.sensefy.sensefyui.info;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.zaizi.sensefy.sensefyui.drivers.ChromeDriverStore;
import com.zaizi.sensefy.sensefyui.drivers.FirefoxDriverStore;
import com.zaizi.sensefy.sensefyui.drivers.SafariDriverStore;


public class TestCaseProperties 
{
	/**
	 * Defining log4j
	 */
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName()); 
	   
	   public static final String TEXT_TEST_PREPARING = "Preparing For {} ...";
	   public static final String TEXT_TEST_EXECUTING = "Executing Test :  {} ...";
	   public static final String TEXT_TEST_PASS = "Test Case : {} PASSED!";
	   public static final String TEXT_TEST_FAIL = "Test Case : {} FAILED!";
	   
	   /**
	    * Defining Browser (Firefox or Safari or Chrome)
	    */
	   public static final String BROWSER = getPropertyValue("Browser");
	   //public static final String BROWSER = System.getProperty("Browser");
	   
	   /**
	    * Defining Chrome driver path
	    */
	   public static final String CHROME_DRIVER_PATH = "src/test/resources/chromedriver";
	   
	   /**
	    * Defining zero result string for user search
	    */
	   public static final String SEARCH_STRING = getPropertyValue("FoundZeroResultsString");

	   /**
	    * Defining zero result string for document search
	    */
	   public static final String TEXT = getPropertyValue("FoundZeroSitesString");

	   	/**
	   	 * Defining test case properties xml path
	   	 */
	   private static final String TEST_CASE_PROPERTIES_XML = "src/test/resources/TestProperties.xml";

   //--------------------------
	   /**
	    * Report path is defined here
	    */
	   public static final String REPORT_TEST_PATH = getPropertyValue("ReportPath");
	   
	   /**
	    * 
	    */
	   public static final String UPLOAD_DOC_PATH = getPropertyValue("UploadPath");
	   
	   /**
		 * Defining IE driver path
		 */
		//public static final String IE_DRIVER_PATH = getPropertyValue("IEDriverPath");
		
		/**
		 * Defining nodeURL
		 */
		//public static final String NODEURL = getPropertyValue("NodeURL");
		/**
		 * Defining nodeURL
		 */
		//public static final String DRIVERTYPE = getPropertyValue("DriverType");
	   
//	   /**
//	    * alfresco path
//	    */
//	   public static final String ALFRESCO_PATH =	getPropertyValue("AlfrescoPath");
//	   
//	   /**
//	    * Alfresco username and password
//	    */
//	   public static final String ALFRESCO_USERNAME = getPropertyValue("AlfrescoUsername");
//	   public static final String ALFRESCO_PASSWORD = getPropertyValue("AlfrescoPassword");
	   //--------------------------
	  
	   public static final String NOBJECT=null;
	   
	   /**
	    * Defining WebDriver
	    */
	   private static WebDriver driver = null;
 /**
  * getWebDriver method
  * @return
  */

	 //---------------------------------------------------
	   public static WebDriver getAlfresco()
	   {
	  	 UrlFinder urlFinder = new UrlFinder();
	  	 
	  	 String HOST_URL = urlFinder.returnAlfresco();
	  	 String BROWSER_NAME = urlFinder.returnBrowser();
	  	 
	  	 if(BROWSER_NAME.equalsIgnoreCase("Firefox"))
	       {
	           FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
	           driver = webDrvFac.createWebDriver();
	       }
	  	 else if ("Chrome".equals(BROWSER))
	  	 {
	  	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
	  	     System.out.println(CHROME_DRIVER_PATH);
	  	     driver = webDrvFac.createWebDriver();
	  	 }
	  	 else if ("Safari".equals(BROWSER))
	       {
	           SafariDriverStore webDrvFac = new SafariDriverStore();
	           driver = webDrvFac.createWebDriver();
	       }
	  	 
	  	 driver.get(HOST_URL);
	       return driver;
	   }
	   
	   public static WebDriver getManifold()
	   {
		   UrlFinder urlFinder = new UrlFinder();
		  	 
		  	 String HOST_URL = urlFinder.returnManifold();
		  	 String BROWSER_NAME = urlFinder.returnBrowser();
		  	 
		  	 if(BROWSER_NAME.equalsIgnoreCase("Firefox"))
		       {
		           FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 else if ("Chrome".equals(BROWSER))
		  	 {
		  	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
		  	     System.out.println(CHROME_DRIVER_PATH);
		  	     driver = webDrvFac.createWebDriver();
		  	 }
		  	 else if ("Safari".equals(BROWSER))
		       {
		           SafariDriverStore webDrvFac = new SafariDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 
		  	 driver.get(HOST_URL);
		       return driver;
	   }
	   
	   public static WebDriver getSensefyLocalHost()
	   {
		   UrlFinder urlFinder = new UrlFinder();
		  	 
		  	 String HOST_URL = urlFinder.returnSensefyLocalhost();
		  	 String BROWSER_NAME = urlFinder.returnBrowser();
		  	 
		  	 if(BROWSER_NAME.equalsIgnoreCase("Firefox"))
		       {
		           FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 else if ("Chrome".equals(BROWSER))
		  	 {
		  	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
		  	     System.out.println(CHROME_DRIVER_PATH);
		  	     driver = webDrvFac.createWebDriver();
		  	 }
		  	 else if ("Safari".equals(BROWSER))
		       {
		           SafariDriverStore webDrvFac = new SafariDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 
		  	 driver.get(HOST_URL);
		       return driver;
	   }
	   
	   public static WebDriver getSensefyQa()
	   {
		   UrlFinder urlFinder = new UrlFinder();
		  	 
		  	 String HOST_URL = urlFinder.returnSensefyQa();
		  	 String BROWSER_NAME = urlFinder.returnBrowser();
		  	 
		  	if(BROWSER_NAME.equalsIgnoreCase("Firefox"))
		       {
		           FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 else if ("Chrome".equals(BROWSER))
		  	 {
		  	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
		  	     System.out.println(CHROME_DRIVER_PATH);
		  	     driver = webDrvFac.createWebDriver();
		  	 }
		  	 else if ("Safari".equals(BROWSER))
		       {
		           SafariDriverStore webDrvFac = new SafariDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 
		  	 driver.get(HOST_URL);
		       return driver;
	   }
	   
	   public static WebDriver getSensefyMico()
	   {
		   UrlFinder urlFinder = new UrlFinder();
		  	 
		  	 String HOST_URL = urlFinder.returnSensefyMico();
		  	 String BROWSER_NAME = urlFinder.returnBrowser();
		  	 
		  	if(BROWSER_NAME.equalsIgnoreCase("Firefox"))
		       {
		           FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 else if ("Chrome".equals(BROWSER))
		  	 {
		  	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
		  	     System.out.println(CHROME_DRIVER_PATH);
		  	     driver = webDrvFac.createWebDriver();
		  	 }
		  	 else if ("Safari".equals(BROWSER))
		       {
		           SafariDriverStore webDrvFac = new SafariDriverStore();
		           driver = webDrvFac.createWebDriver();
		       }
		  	 
		  	 driver.get(HOST_URL);
		       return driver;
  }



	  
//Get webdriver for AdminUI

 public static WebDriver getWebDriver()
 {
	 UrlFinder un=new UrlFinder();
	 
	 //To use without parsing URL through mvn 
	 String HOST_URL = un.urlcaterher();
	 
     if (driver != null)
     {
         closeDriver(driver);
     }
     if ("Firefox".equals(BROWSER))
     //if(BrowserName.equalsIgnoreCase("Firefox"))
     {
    	 System.out.println("Firefox calling...");
         FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
         driver = webDrvFac.createWebDriver();
     }
     else if ("Chrome".equals(BROWSER))
     //else if(BrowserName.equalsIgnoreCase("Chrome"))
     {
    	 System.out.println("Chorme calling...");
         ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
         driver = webDrvFac.createWebDriver();
     }
     else if ("Safari".equals(BROWSER))
     //else if(BrowserName.equalsIgnoreCase("Safari"))
     {
         SafariDriverStore webDrvFac = new SafariDriverStore();
         driver = webDrvFac.createWebDriver();
     }
     driver.get(System.getProperty("admin"));
     //To use without parsing URL through mvn 
     //driver.get(HOST_URL);
     return driver;
 }
 
 //get webdriver for searchUI
 public static WebDriver getWebDriverForSearch()
 {
	 UrlFinder un=new UrlFinder();
	//To use without parsing URL through mvn 
	String HOST_URL = un.searchUrlCatcher();
	 //String HOST_URL =System.getProperty("url");
	 //System.out.println("HOST URL is: "+HOST_URL);
	 //System.out.println("NOBJECT is: "+NOBJECT);
	UrlFinder uf=new UrlFinder();
	String BrowserName=uf.returnBrowser();
	 
	 if(BrowserName.equalsIgnoreCase("Firefox"))
     {
         FirefoxDriverStore webDrvFac = new FirefoxDriverStore();
         driver = webDrvFac.createWebDriver();
     }
	 else if ("Chrome".equals(BROWSER))
	 {
	     ChromeDriverStore webDrvFac = new ChromeDriverStore(CHROME_DRIVER_PATH);
	     System.out.println(CHROME_DRIVER_PATH);
	     driver = webDrvFac.createWebDriver();
	 }
	 else if ("Safari".equals(BROWSER))
     {
         SafariDriverStore webDrvFac = new SafariDriverStore();
         driver = webDrvFac.createWebDriver();
     }
	 
     driver.get(HOST_URL);
     return driver;
 }
 
 
 /**
  * @param oldDriver
  */
 public static void closeDriver(WebDriver oldDriver)
 {
     oldDriver.close();
     driver = null;
 }

 /**
  * @param propertyName
  * @return
  */
 public static String getPropertyValue(String propertyName)
 {
     String result = null;
     try
     {
         Node serverUrl = getProperty(propertyName);
         result = serverUrl.getNodeValue();
     }
     catch (ParserConfigurationException e)
     {
         LOGGER.error("ParserConfigurationException", e);
     }
     catch (SAXException e)
     {
         LOGGER.error("SAXException", e);
     }
     catch (IOException e)
     {
         LOGGER.error("IOException", e);
     }
     return result;
 }

 /**
  * @param PropertyName
  * @return
  * @throws ParserConfigurationException
  * @throws SAXException
  * @throws IOException
  */
 private static Node getProperty(String propertyName) throws ParserConfigurationException, SAXException, IOException
 {
     File testValues = new File(TEST_CASE_PROPERTIES_XML);
     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     Document doc = dBuilder.parse(testValues);
     doc.getDocumentElement().normalize();
     NodeList nodes = doc.getElementsByTagName(propertyName);
     Node node = nodes.item(0);
     NodeList testdata = node.getChildNodes();
     return testdata.item(0);
 }
}

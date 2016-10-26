package com.zaizi.sensefy.sensefyui.info;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
	    * Declaring the variables
	    */
		String HOST_URL = "";
		static String Search_HOST_URL="";
	   
		/**
	    * Defining Browser (Firefox or Safari or Chrome)
	    */
		public static final String BROWSER = getPropertyValue("Browser");
	  	   
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

		/**
	    * Report path is defined here
	    */
		public static final String REPORT_TEST_PATH = getPropertyValue("ReportPath");
	   
		/**
	    * Defining the Upload Path
	    */
		public static final String UPLOAD_DOC_PATH = getPropertyValue("UploadPath");
	  
		//public static final String NOBJECT=null;
	   
		/**
	    * Defining WebDriver
	    */
		private static WebDriver driver = null;

		/**
	    * Defining the Admin Login
	    */
		public static final String ADMIN_REST = getPropertyValue("adminrest");
	   
		/**
	    * Derfining the Sensefy QA Url
	    */
		public static final String SEARCH_SERVER_URL = getPropertyValue("searchserverurl");
	   
		/**
	    * Defining the Port
	    */
		public static final String SEARCH_PORT = getPropertyValue("searchport");

		/**
	    * Defining the search rest
	    */
		public static final String SEARCH_REST = getPropertyValue("searchrest");
	   
		/**
	    * Defining the Alfresco Url
	    */
		public static final String ALFRESCO_PATH = getPropertyValue("alfrescopath");
	   
		/**
	    * Defining the Manifold Url
	    */
		public static final String MANIFOLD_PATH = getPropertyValue("manifoldpath");
	   
		/**
	    * Defining the Sensefy Local Host Url
	    */
		public static final String SENSEFY_LOCAL_HOST = getPropertyValue("sensefylocalhost");
	      
		/**
		* Defining Driver type
		*/
		public static final String DRIVERTYPE = getPropertyValue("DriverType");
	 
		//Get Alfresco
		public static WebDriver getAlfresco()
		{
			String HOST_URL = returnAlfresco();
			String BROWSER_NAME = returnBrowser();
	  	 
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
	   
		//Get MAnifold
		public static WebDriver getManifold()
		{		 	 
			String HOST_URL = returnManifold();
			String BROWSER_NAME = returnBrowser();
		  	 
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
	   
		//Get Sensefy Local Host
		public static WebDriver getSensefyLocalHost()
		{
			String HOST_URL = returnSensefyLocalhost();
			String BROWSER_NAME = returnBrowser();
		  	 
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
	   
		//Get Sensefy QA
		public static WebDriver getSensefyQa()
		{
			String HOST_URL =returnSensefyQa();
			String BROWSER_NAME = returnBrowser();
		  	 
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
	   
		//Return the Browser
		public static String returnBrowser()
		{
			return BROWSER;
		}
		
		//Return the Alfresco Url
		public static String returnAlfresco()
		{
			System.out.println(ALFRESCO_PATH);
			return ALFRESCO_PATH;
		}
		
		//Return the Manifold Url
		public static String returnManifold()
		{
			System.out.println(MANIFOLD_PATH);
			return MANIFOLD_PATH;
		}
		
		//Return the Senesfy Local Host
		public static String returnSensefyLocalhost()
		{
			System.out.println(SENSEFY_LOCAL_HOST);
			return SENSEFY_LOCAL_HOST;
		}
		
		//Return the Sesnefy QA
		public static String returnSensefyQa()
		{
			System.out.println(SEARCH_SERVER_URL);
			return SEARCH_SERVER_URL;
		}
		
		//Return test case properties xml
		public static String returnTestPropertiesXml()
		{
			return TEST_CASE_PROPERTIES_XML;
		}
		
		//Return the Search URL Catcher
		public static String searchUrlCatcher()
		{
			if((SEARCH_PORT.equalsIgnoreCase("NA"))&&(SEARCH_REST.equalsIgnoreCase("NA")))
			{
				Search_HOST_URL=SEARCH_SERVER_URL;
			}
			else if (SEARCH_REST.equalsIgnoreCase("NA"))
			{
				Search_HOST_URL=SEARCH_SERVER_URL+SEARCH_PORT;
			}
			else if(SEARCH_PORT.equalsIgnoreCase("NA"))
			{
				Search_HOST_URL=SEARCH_SERVER_URL+SEARCH_REST;
			}
			else
			{
				Search_HOST_URL=SEARCH_SERVER_URL+SEARCH_PORT+SEARCH_REST;
			}
			return Search_HOST_URL;
		}
		

/*		  
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
		
		//generating URL for AdminUI
		public String urlcaterher()
		{
			if((SEARCH_PORT.equalsIgnoreCase("NA"))&&(SEARCH_REST.equalsIgnoreCase("NA")))
			{
				HOST_URL=SEARCH_SERVER_URL;
			}
			else if(SEARCH_REST.equalsIgnoreCase("NA"))
			{
				HOST_URL=SEARCH_SERVER_URL+SEARCH_PORT;
			}
			else if(SEARCH_PORT.equalsIgnoreCase("NA"))
			{
				HOST_URL=SEARCH_SERVER_URL+SEARCH_REST;
			}
			else
			{
				HOST_URL=SEARCH_SERVER_URL+SEARCH_PORT+SEARCH_REST;
			}
			return HOST_URL;		
		}
 */
		//get webdriver for searchUI
		public static WebDriver getWebDriverForSearch()
		{
			//To use without parsing URL through mvn 
			String HOST_URL = searchUrlCatcher();
			
			String BrowserName=returnBrowser();
	 
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

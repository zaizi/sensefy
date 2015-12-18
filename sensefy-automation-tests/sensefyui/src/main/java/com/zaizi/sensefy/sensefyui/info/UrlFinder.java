package com.zaizi.sensefy.sensefyui.info;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UrlFinder {
	// Define Base URL
	public static final Logger LOGGER = LogManager.getLogger(TestCaseProperties.class.getName());

	private static final String TEST_CASE_PROPERTIES_XML = "pom.xml";
	
	   String HOST_URL = "";
	   String Search_HOST_URL="";
	  
	   String HOST_Port = getPropertyValue("port");
	   String HOST_UR = getPropertyValue("serverurl");
	   String HOST_REST=getPropertyValue("adminrest");
	   String Search_HOST=getPropertyValue("searchserverurl");
	   String Search_Port=getPropertyValue("searchport");
	   String Search_REST=getPropertyValue("searchrest");
	   
	 
	   
	//generating URL for AdminUI
	public String urlcaterher()
	{
		if((HOST_Port.equalsIgnoreCase("NA"))&&(HOST_REST.equalsIgnoreCase("NA")))
		{
			HOST_URL=HOST_UR;
		}
		else if(HOST_REST.equalsIgnoreCase("NA"))
		{
			HOST_URL=HOST_UR+HOST_Port;
		}
		else if(HOST_Port.equalsIgnoreCase("NA"))
		{
			HOST_URL=HOST_UR+HOST_REST;
		}
		else
		{
			HOST_URL=HOST_UR+HOST_Port+HOST_REST;
		}
				
		
		return HOST_URL;
		
	}
	
	//Generating URL for SearchUI
	public String searchUrlCatcher()
	{
		if((Search_Port.equalsIgnoreCase("NA"))&&(Search_REST.equalsIgnoreCase("NA")))
		{
			Search_HOST_URL=Search_HOST;
		}
		else if (Search_REST.equalsIgnoreCase("NA"))
		{
			Search_HOST_URL=Search_HOST+Search_Port;
		}
		else if(Search_Port.equalsIgnoreCase("NA"))
		{
			Search_HOST_URL=Search_HOST+Search_REST;
		}
		else
		{
			Search_HOST_URL=Search_HOST+Search_Port+Search_REST;
		}
		
		return Search_HOST_URL;
		
	}
	
	
	
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
	
	public void waitforDone()
	{
		String done ="//span[@class='normal-f d-block ng-binding'] [contains(text(),'done')]";
		String running="//span[@class='normal-f d-block ng-binding'] [contains(text(),'running')]";
	}
}
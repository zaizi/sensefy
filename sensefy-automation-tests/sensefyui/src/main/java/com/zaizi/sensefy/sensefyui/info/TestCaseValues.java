package com.zaizi.sensefy.sensefyui.info;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zaizi.sensefy.sensefyui.exceptions.IterableException;

public class TestCaseValues {
	
	private static final String TEST_CASE_VALUES_XML = "src/test/resources/TestValues.xml";

    /**
     * @param testCaseName
     * @return
     * @throws Exception
     */
    public static Iterable<Object[]> documentLibraryTestValues(final String testCaseName)
            throws IterableException
    {
        Iterable<Object[]> result = null;
        NodeList testdata;
        try
        {
            testdata = getTestData(testCaseName);
        }
        catch (ParserConfigurationException e)
        {
            throw new IterableException("ParserConfigurationException : ", e);
        }
        catch (SAXException e)
        {
            throw new IterableException("SAXException : ", e);
        }
        catch (IOException e)
        {
            throw new IterableException("IOException : ", e);
        }
        int noOfTestValues = getNumberOfValues(testdata);
        Object[][] bar = new Object[noOfTestValues][];
        int k = 0;
        for (int j = 0; j < testdata.getLength(); j++)
        {
            Node valueNode = testdata.item(j);
            if (valueNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element valueElement = (Element) valueNode;
                                
                if (("Username").equals(testCaseName))
                {
                    Object[] foo = { getValue("parameter1", valueElement), getValue("parameter2", valueElement),
                    		Boolean.valueOf(getValue("parameter3", valueElement))};
                    bar[k] = foo;
                    System.out.println("Values Loaded - TestName1");
                }
                else if (("LoginTest").equals(testCaseName))
                {
                    Object[] foo = { getValue("Username", valueElement), getValue("Password", valueElement)};
                    bar[k] = foo;
                    System.out.println("Values Loaded - LoginTest");
                }  
                else if (("SearchTest").equals(testCaseName))
                {
                	Object[] foo = { getValue("Username", valueElement), getValue("Password", valueElement),getValue("nodocword", valueElement),getValue("didumean", valueElement),getValue("closeword", valueElement)};
                    bar[k] = foo;
                    System.out.println("Values Loaded - SearchTest");
                }
                else if (("SortTest").equals(testCaseName))
                {
                	Object[] foo = { getValue("Username", valueElement), getValue("Password", valueElement),getValue("searchWord", valueElement)};
                    bar[k] = foo;
                    System.out.println("Values Loaded - SortTest");
                }
                else if (("FacetTest").equals(testCaseName))
                {
                	Object[] foo = { getValue("Username", valueElement), getValue("Password", valueElement),getValue("searchWord", valueElement), getValue("creator", valueElement), getValue("doctype", valueElement),getValue("language", valueElement),getValue("size", valueElement),
                			getValue("sharpness", valueElement), getValue("modifiedDate", valueElement),getValue("creationDate", valueElement),getValue("topic", valueElement),};
                    bar[k] = foo;
                    System.out.println("Values Loaded - FacetTest");
                }
                else if(("hUploadandSearch").equals(testCaseName))
                {
                	Object[] foo = { getValue("username", valueElement), 
                						getValue("password", valueElement),
                						getValue("site_name", valueElement),
                						getValue("document_name", valueElement),
                						getValue("screenshot_name", valueElement)
                    						};
                	
                	bar[k]=foo;
                	System.out.println("Values Loaded - Alfresco / Manifold");                	
                }
                else if(("iTestUpload").equals(testCaseName))
                {
                	Object[] foo = { getValue("username", valueElement), 
                						getValue("password", valueElement),
                						getValue("site_name", valueElement),
                						getValue("uploadDocument", valueElement),
                						};
                	
                	bar[k]=foo;
                	System.out.println("Values Loaded - Alfresco - File Upload");                	
                }
                else if(("jPrivateSiteDocuments").equals(testCaseName))
                {
                	Object[] foo = { getValue("username", valueElement), 
                						getValue("password", valueElement),
                						getValue("userA", valueElement),
                						getValue("emailA", valueElement),
                						getValue("usernameA", valueElement), 
                						getValue("passwordA", valueElement),
                						getValue("userB", valueElement),
                						getValue("emailB", valueElement),
                						getValue("usernameB", valueElement), 
                						getValue("passwordB", valueElement),
                						getValue("privateSiteName", valueElement),
                						getValue("documentName", valueElement),
                						getValue("screen_shot", valueElement), 
                	};
                	
                	bar[k]=foo;
                	System.out.println("Values Loaded - Alfresco - File Upload");                	
                }
                else if(("kMediaSearch").equals(testCaseName))
                {
                	Object[] foo = { getValue("username1", valueElement), 
                						getValue("password1", valueElement),
                						getValue("username2", valueElement), 
                						getValue("password2", valueElement),
                						getValue("sitename", valueElement),
                						getValue("videoname", valueElement),
                						getValue("screenshot_name", valueElement), 
                	};
                	
                	bar[k]=foo;
                	System.out.println("Values Loaded - Alfresco - Video Upload");                	
                }
                else if(("lEntitySearch").equals(testCaseName))
                {
                	Object[] foo = { getValue("username", valueElement), 
                						getValue("password", valueElement),
                						getValue("search_term_people", valueElement), 
                						getValue("search_term_places", valueElement),
                						getValue("search_term_organization", valueElement),
                						getValue("screenshot_name", valueElement), 
                	};
                	
                	bar[k]=foo;
                	System.out.println("Values Loaded - Entity Search");                	
                }
                
                k++;
            }
        }
        result = Arrays.asList(bar);
        return result;
    }

    /**
     * @param testCaseName
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private static NodeList getTestData(final String testCaseName) throws ParserConfigurationException, SAXException,
            IOException
    {
        File testValues = new File(TEST_CASE_VALUES_XML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(testValues);
        doc.getDocumentElement().normalize();
        NodeList nodes = doc.getElementsByTagName(testCaseName);
        Node node = nodes.item(0);
        NodeList testdata = node.getChildNodes();
        return testdata;
    }

    /**
     * @param nodeList
     * @return
     */
    private static int getNumberOfValues(final NodeList nodeList)
    {
        int length = 0;
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                length++;
            }
        }
        return length;
    }

    /**
     * @param tag
     * @param element
     * @return
     */
    private static String getValue(final String tag, final Element element)
    {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

}

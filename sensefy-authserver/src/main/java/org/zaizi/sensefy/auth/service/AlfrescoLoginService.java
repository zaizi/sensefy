/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
  **/
package org.zaizi.sensefy.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * Alfresco Login Service class
 * </p>
 * <p>
 * Service to authenticate users against an Alfresco instance
 * </p>
 * 
 * @author Antonio David Perez Morales <aperez@zaizi.com>
 * 
 */
// @EnableAutoConfiguration
// @ConfigurationProperties("sensefy.authentication.alfresco")
@Component
public class AlfrescoLoginService implements InitializingBean
{

    /**
     * <p>
     * Default Alfresco Endpoint
     * </p>
     */
    private static final String DEFAULT_ALFRESCO_ENDPOINT = "http://localhost:8080/alfresco";

    /**
     * <p>
     * Path to the WebScript for login
     * </p>
     */
    private static final String LOGIN_PATH = "/service/api/login?u={username}&pw={password}";

    @Value("${sensefy.authentication.alfresco.endpoint}")
    private String alfrescoEndpoint = DEFAULT_ALFRESCO_ENDPOINT;

    /**
     * <p>
     * Logger object
     * </p>
     */
    private Logger log = LoggerFactory.getLogger(AlfrescoLoginService.class);

    /**
     * <p>
     * The Alfresco endpoint
     * </p>
     */
    private RestTemplate restTemplate;

    public AlfrescoLoginService()
    {
        initializeRestTemplate();
    }

    public AlfrescoLoginService(String alfrescoEndpoint)
    {
        if (alfrescoEndpoint != null && !alfrescoEndpoint.isEmpty())
            this.alfrescoEndpoint = alfrescoEndpoint;
        initializeRestTemplate();
    }

    /**
     * <p>
     * Initialize the REST template object used as HTTP Client
     * </p>
     */
    private void initializeRestTemplate()
    {
        this.restTemplate = new RestTemplate();
    }

    /**
     * <p>
     * Sets the Alfresco endpoint used to validate tickets
     * </p>
     * 
     * @param alfrescoEndpoint the Alfresco endpoint
     */
    public void setAlfrescoEndpoint(String alfrescoEndpoint)
    {
        if (alfrescoEndpoint != null && !alfrescoEndpoint.isEmpty())
            this.alfrescoEndpoint = alfrescoEndpoint;
    }

    /**
     * <p>
     * Gets the configured Alfresco endpoint used to validate the tickets
     * </p>
     * 
     * @return a {@code String} containing the Alfresco endpoint being used
     */
    public String getAlfrescoEndpoint()
    {
        return this.alfrescoEndpoint;
    }

    /**
     * <p>
     * Gets the Rest Template being used
     * </p>
     * 
     * @return the {@code RestTemplate} used
     */
    public RestTemplate getRestTemplate()
    {
        return restTemplate;
    }

    /**
     * <p>
     * Sets the Rest Template to be used to make the requests
     * </p>
     * 
     * @param restTemplate the {@code RestTemplate} to be used for requests
     */
    public void setRestTemplate(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    /**
     * <p>
     * Validates an Alfresco ticket obtaining the username of the user behind the ticket if the ticket is valid
     * </p>
     * 
     * @param username The username of the user to be authenticated
     * @param password The password of the user
     * @return a {@code Boolean} indicating if the login has been successful or not
     */
    public boolean login(String username, String password) throws RestClientException
    {

        String endpoint = this.buildAlfrescoLoginUrl();

        try
        {
            restTemplate.getForEntity(endpoint, String.class, username, password);
            log.debug("Login for user {} performed successfully", new Object[] { username });
            return true;
        }
        catch (RestClientException rce)
        {
            log.error("Error making the HTTP request to login the user {}, [AlfrescoLoginUrl=" + endpoint + "]",
                    username, rce);
        }
        return false;
    }

    /**
     * <p>
     * Build the URL of the alfresco ticket validation endpoint
     * </p>
     * 
     * @return the {@code String} containing the Alfresco ticket validation endpoint</p>
     */
    private String buildAlfrescoLoginUrl()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(alfrescoEndpoint).append(LOGIN_PATH);
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        if (alfrescoEndpoint == null)
        {
            this.alfrescoEndpoint = DEFAULT_ALFRESCO_ENDPOINT;
        }
    }
}
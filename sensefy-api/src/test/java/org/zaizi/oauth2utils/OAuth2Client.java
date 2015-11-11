/*******************************************************************************
 * Sensefy
 *
 * Copyright (c) Zaizi Limited, All rights reserved.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 *******************************************************************************/
package org.zaizi.oauth2utils;

import java.util.Properties;

public class OAuth2Client
{

    public static void main(String[] args)
    {

        Properties config = OAuthUtils.getClientConfigProps("com/zaizi/Oauth2Client.config");
        String resourceServerUrl = config.getProperty(OAuthConstants.RESOURCE_SERVER_URL);
        String username = config.getProperty(OAuthConstants.USERNAME);
        String password = config.getProperty(OAuthConstants.PASSWORD);
        String grantType = config.getProperty(OAuthConstants.GRANT_TYPE);
        String authenticationServerUrl = config.getProperty(OAuthConstants.AUTHENTICATION_SERVER_URL);

        if (!OAuthUtils.isValid(username) || !OAuthUtils.isValid(password)
                || !OAuthUtils.isValid(authenticationServerUrl) || !OAuthUtils.isValid(grantType))
        {
            System.out.println("Please provide valid values for username, password, authentication server url and grant type");
            System.exit(0);
        }
        if (!OAuthUtils.isValid(resourceServerUrl))
        {
            // Resource server url is not valid. Only retrieve the access token
            System.out.println("Retrieving Access Token");
            OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(config);
            String accessToken = OAuthUtils.getAccessToken(oauthDetails);
            if (OAuthUtils.isValid(accessToken))
            {
                System.out.println("Successfully retrieved Access token for Password Grant: " + accessToken);
            }
        }
        else
        {
            // Response from the resource server must be in Json or Urlencoded or xml
            System.out.println("Resource endpoint url: " + resourceServerUrl);
            System.out.println("Attempting to retrieve protected resource");
            OAuthUtils.getProtectedResource(config);
        }
    }
}

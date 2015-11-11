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
package org.zaizi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.zaizi.oauth2utils.OAuth2Details;
import org.zaizi.oauth2utils.OAuthConstants;
import org.zaizi.oauth2utils.OAuthUtils;

public class TestOAuthUtils
{
    public static final String OAUTH2_SERVER_USER_INFO_URI = "http://localhost:8090/ua/user";
    public static final String OAUTH2_SERVER_ACCESS_TOKEN_URI = "http://localhost:8090/ua/oauth/token";

    public static final String ZAIZI_USER = "admin";
    public static final String ZAIZI_USER_PWD = "ulibranxi";

    private static final Map<String, String> userOAuth2Tokens = new HashMap<>();

    public static void addOAuth2AccessTokenToHeader(final HttpHeaders headers, String accessToken)
    {
        headers.add(OAuthConstants.AUTHORIZATION, OAuthUtils.getAuthorizationHeaderForAccessToken(accessToken));
    }

    public static String getDefOAuth2AccessToken()
    {
        return getOAuth2AccessToken(ZAIZI_USER, ZAIZI_USER_PWD);
    }

    public static String getOAuth2AccessToken(String userName, String password)
    {
        String oath2Token = userOAuth2Tokens.get(userName);
        if (oath2Token == null)
        {
            Map<String, String> oAuth2Config = getOAuth2Config(userName, password);
            OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(oAuth2Config);
            oath2Token = OAuthUtils.getAccessToken(oauthDetails);
            userOAuth2Tokens.put(userName, oath2Token);
        }
        return oath2Token;
    }

    public static Map<String, String> getOAuth2Config(String userName, String password)
    {
        Map<String, String> cfg = new HashMap<>();
        cfg.put("scope", "openid");
        cfg.put("grant_type", "password");
        cfg.put("username", userName);
        cfg.put("password", password);
        cfg.put("client_id", "sensefy");
        cfg.put("client_secret", "sensefysecret");
        // #access_token=http://localhost:8090/ua/oauth/token
        // #refresh_token=
        cfg.put("authentication_server_url", OAUTH2_SERVER_ACCESS_TOKEN_URI);
        cfg.put("resource_server_url", OAUTH2_SERVER_USER_INFO_URI);
        return cfg;
    }

    public static Map<String, String> getDefOAuth2Config()
    {
        return getOAuth2Config(ZAIZI_USER, ZAIZI_USER_PWD);
    }
}

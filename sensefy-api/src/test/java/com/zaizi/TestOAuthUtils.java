package com.zaizi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import com.zaizi.oauth2utils.OAuth2Details;
import com.zaizi.oauth2utils.OAuthConstants;
import com.zaizi.oauth2utils.OAuthUtils;

public class TestOAuthUtils
{
    public static final String OAUTH2_SERVER_USER_INFO_URI = "http://localhost:8090/ua/user";
    public static final String OAUTH2_SERVER_ACCESS_TOKEN_URI = "http://localhost:8090/ua/oauth/token";
    public static final String ZAIZI_USER_PWD = "zaizi321#$%";
    public static final String ZAIZI_USER = "zaizi1";

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
        Map<String, String> oAuth2Config = getOAuth2Config(userName, password);
        OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(oAuth2Config);
        return OAuthUtils.getAccessToken(oauthDetails);
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

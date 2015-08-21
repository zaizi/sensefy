package com.zaizi.oauth2;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

public abstract class AbstractOAuth2Filter extends AbstractLoginFilter
{
    private static final Logger LOG = Logger.getLogger(AbstractOAuth2Filter.class);

    // private static final String REDIRECT_TO = "redirectTo";

    private static final String PARAM_STATE = "state";
    private static final String PARAM_CODE = "code";
    private static final String PARAM_ACCESS_TOKEN = "access_token";
    private static final String PARAM_LOGIN_STATE = "loginState";
    private static final String HTTP_HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    final AbstractOAuth2Conf oauth2Config;

    public AbstractOAuth2Filter(final AbstractOAuth2Conf oauth2Config)
    {
        this.oauth2Config = oauth2Config;
    }

    /**
     * @param httpRequest
     * @return true if the user request to logout
     */
    @Override
    protected boolean doLogin(final HttpServletRequest httpRequest)
    {
        return oauth2Config.getLoginUrl().equals(httpRequest.getRequestURI());
    }

    @Override
    protected boolean _doFilter(final OAuth2HttpRequestWrapper request, final HttpServletResponse response)
            throws ServletException, IOException
    {
        final String code = request.getParameter(PARAM_CODE);
        final String state = request.getParameter(PARAM_STATE);

        if (StringUtils.isBlank(code) && StringUtils.isBlank(state))
        {
            // step 1: saving the 'redir' parameter to the session
            // and redirect to the oath2 provider login page...
            // final String redirectUrl = request.getParameter(REDIRECT_TO);
            // if (redirectUrl != null) {
            // request.getSession().setAttribute(REDIRECT_TO, redirectUrl);
            // }
            redirectToLoginPage(request, response);
            return false;
        }
        else if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state))
        {
            // step 2: continue with authorisation
            final ExtAOth2Credential credential = loginAuthorisation(request, response, code, state);

            request.setExtOAuth2credential(credential);
            credential.addCredentialToHeader(request);

            // setting the authentication
            // (http://docs.spring.io/spring-security/site/docs/3.1.x/reference/technical-overview.html#d0e1833)
            // SecurityContextHolder.getContext().setAuthentication(auth);

            // if (request.getSession().getAttribute(REDIRECT_TO) != null) {
            // final String redirectPath = (String) request.getSession()
            // .getAttribute(REDIRECT_TO);
            // request.getSession().setAttribute(REDIRECT_TO, null);
            // redirectStrategy.sendRedirect(request, response, redirectPath);
            // } else {
            // redirectStrategy.sendRedirect(request, response,
            // request.getContextPath());
            // }
        }
        else
        {
            final String errmsg = oauth2Config.getProviderType()
                    + " login error: missing mandatory parameter after redirecting the user to the login page [state: "
                    + state + ", code: " + code + "] {" + convertMap(request.getParameterMap()) + "}";
            LOG.error(errmsg);
            throw new ServletException(errmsg);
        }
        return true;
    }

    private static Map<String, String> convertMap(final Map<String, String[]> map)
    {
        final Map<String, String> res = new HashMap<>(map.size());
        for (final Entry<String, String[]> entry : map.entrySet())
        {
            res.put(entry.getKey(), Arrays.toString(entry.getValue()));
        }
        return res;
    }

    private ExtAOth2Credential loginAuthorisation(final HttpServletRequest request, final HttpServletResponse response,
            final String code, final String state) throws ServletException, IOException
    {

        // 1- verify oauth2 state code
        final String expextedLoginState = (String) request.getSession().getAttribute(PARAM_LOGIN_STATE);
        if (!state.equals(expextedLoginState))
        {
            final String errmsg = oauth2Config.getProviderType() + " login error: parameter '" + PARAM_LOGIN_STATE
                    + "' returned after login is not as expected";
            LOG.error(errmsg + " [expected: " + expextedLoginState + ", found: " + state);
            throw new ServletException(errmsg);
        }

        final HttpClient httpClient = new HttpClient();

        // 2- getting oauth2 access_token
        final PostMethod post2 = new PostMethod(oauth2Config.getAccessTokenUri());
        post2.setRequestHeader(HttpHeaders.CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE);
        final NameValuePair[] data2 = { new NameValuePair("client_secret", oauth2Config.getClientSecret()),
                new NameValuePair("grant_type", "authorization_code"),
                new NameValuePair("redirect_uri", request.getRequestURL().toString()), new NameValuePair("code", code),
                new NameValuePair("client_id", oauth2Config.getClientId()), };
        post2.setRequestBody(data2);
        httpClient.executeMethod(post2);
        final JSONObject respAsJson2 = validateResponse(post2);
        final String access_token = respAsJson2.optString(PARAM_ACCESS_TOKEN);
        if (StringUtils.isBlank(access_token))
        {
            final String errmsg = oauth2Config.getProviderType() + " login error: missing parameter '"
                    + PARAM_ACCESS_TOKEN + "' in the response from " + post2.getURI() + " [response: " + respAsJson2
                    + "]";
            LOG.error(errmsg);
            throw new ServletException(errmsg);
        }

        // 3- getting user profile
        final GetMethod get3 = new GetMethod(oauth2Config.getUserInfoUri());
        get3.setRequestHeader(HttpHeaders.CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE);
        final NameValuePair[] data3 = { new NameValuePair(PARAM_ACCESS_TOKEN, access_token), };
        get3.setQueryString(data3);
        httpClient.executeMethod(get3);
        final JSONObject userProfileJson = validateResponse(get3);

        return buildAuthentication(userProfileJson);
    }

    private JSONObject validateResponse(final HttpMethodBase httpReq) throws ServletException, IOException
    {
        if (HttpStatus.SC_OK != httpReq.getStatusCode())
        {
            final String errmsg = oauth2Config.getProviderType() + " login error: <" + httpReq.getStatusText()
                    + ">, httpReturnCode" + httpReq.getStatusCode() + " invoking " + httpReq.getURI() + " [params: "
                    + httpReq.getParams() + "]";
            LOG.error(errmsg);
            throw new ServletException(errmsg);
        }
        final String resp = httpReq.getResponseBodyAsString();
        try
        {
            return new JSONObject(resp);
        }
        catch (final JSONException e)
        {
            final String errmsg = oauth2Config.getProviderType() + " login parsing http response from "
                    + httpReq.getURI() + " [response: " + resp + "]";
            LOG.error(errmsg);
            throw new ServletException(errmsg);
        }
    }

    private void redirectToLoginPage(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException
    {
        final String state = new BigInteger(130, new SecureRandom()).toString(32);
        request.getSession().setAttribute(PARAM_LOGIN_STATE, state);

        URIBuilder uriBuilder;
        try
        {
            uriBuilder = new URIBuilder(oauth2Config.getUserAuthorizationUri());
        }
        catch (final URISyntaxException e)
        {
            final String errmsg = "Internal error. Wrong userAuthorizationUri configured: "
                    + oauth2Config.getUserAuthorizationUri();
            LOG.error(errmsg, e);
            throw new ServletException(errmsg, e);
        }
        uriBuilder.addParameter("scope", oauth2Config.getScope());
        uriBuilder.addParameter("response_type", "code");
        uriBuilder.addParameter("redirect_uri", request.getRequestURL().toString());
        uriBuilder.addParameter(PARAM_STATE, state);
        uriBuilder.addParameter("client_id", oauth2Config.getClientId());

        redirectStrategy.sendRedirect(request, response, uriBuilder.toString());
    }

    ExtAOth2Credential buildAuthentication(final JSONObject userJson) throws ServletException
    {
        final ExtAOth2Credential user = buildUser(userJson);
        final String domainRoles = oauth2Config.getDomainRoles().get(user.getDomain());
        final String userRoles = oauth2Config.getUserRoles().get(user.getUserName());
        final Collection<SimpleGrantedAuthority> authorities = buildAuthorityCollection(domainRoles, userRoles);
        user.setAuthorities(authorities);
        return user;
        // return buildAuthentication(user.getUserName(),
        // oauth2Config.getProviderType() + " - " + oauth2Config.getUserAuthorizationUri(), authorities);
    }

    abstract ExtAOth2Credential buildUser(final JSONObject userJson) throws ServletException;
}

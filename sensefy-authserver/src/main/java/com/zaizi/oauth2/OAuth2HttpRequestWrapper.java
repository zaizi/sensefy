package com.zaizi.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

public class OAuth2HttpRequestWrapper implements HttpServletRequest
{
    private final HttpServletRequest req;
    private ExtAOth2Credential extOAuth2credential;
    private Map<String, String> additionalHeaders = new HashMap<>();

    OAuth2HttpRequestWrapper(HttpServletRequest req)
    {
        this.req = req;
    }

    public void setExtOAuth2credential(ExtAOth2Credential extOAuth2credential)
    {
        this.extOAuth2credential = extOAuth2credential;
    }

    public ExtAOth2Credential getExtOAuth2credential()
    {
        return extOAuth2credential;
    }

    public String setHeader(String key, String value)
    {
        return additionalHeaders.put(key, value);
    }

    @Override
    public String getHeader(String key)
    {
        String res = additionalHeaders.get(key);
        return (res != null) ? res : req.getHeader(key);
    }

    @Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException
    {
        return req.authenticate(arg0);
    }

    @Override
    public String changeSessionId()
    {
        return req.changeSessionId();
    }

    @Override
    public AsyncContext getAsyncContext()
    {
        return req.getAsyncContext();
    }

    @Override
    public Object getAttribute(String arg0)
    {
        return req.getAttribute(arg0);
    }

    @Override
    public Enumeration<String> getAttributeNames()
    {
        return req.getAttributeNames();
    }

    @Override
    public String getAuthType()
    {
        return req.getAuthType();
    }

    @Override
    public String getCharacterEncoding()
    {
        return req.getCharacterEncoding();
    }

    @Override
    public int getContentLength()
    {
        return req.getContentLength();
    }

    @Override
    public long getContentLengthLong()
    {
        return req.getContentLengthLong();
    }

    @Override
    public String getContentType()
    {
        return req.getContentType();
    }

    @Override
    public String getContextPath()
    {
        return req.getContextPath();
    }

    @Override
    public Cookie[] getCookies()
    {
        return req.getCookies();
    }

    @Override
    public long getDateHeader(String arg0)
    {
        return req.getDateHeader(arg0);
    }

    @Override
    public DispatcherType getDispatcherType()
    {
        return req.getDispatcherType();
    }

    @Override
    public Enumeration<String> getHeaderNames()
    {
        return req.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String arg0)
    {
        return req.getHeaders(arg0);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return req.getInputStream();
    }

    @Override
    public int getIntHeader(String arg0)
    {
        return req.getIntHeader(arg0);
    }

    @Override
    public String getLocalAddr()
    {
        return req.getLocalAddr();
    }

    @Override
    public String getLocalName()
    {
        return req.getLocalName();
    }

    @Override
    public int getLocalPort()
    {
        return req.getLocalPort();
    }

    @Override
    public Locale getLocale()
    {
        return req.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales()
    {
        return req.getLocales();
    }

    @Override
    public String getMethod()
    {
        return req.getMethod();
    }

    @Override
    public String getParameter(String arg0)
    {
        return req.getParameter(arg0);
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
        return req.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return req.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String arg0)
    {
        return req.getParameterValues(arg0);
    }

    @Override
    public Part getPart(String arg0) throws IOException, ServletException
    {
        return req.getPart(arg0);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException
    {
        return req.getParts();
    }

    @Override
    public String getPathInfo()
    {
        return req.getPathInfo();
    }

    @Override
    public String getPathTranslated()
    {
        return req.getPathTranslated();
    }

    @Override
    public String getProtocol()
    {
        return req.getProtocol();
    }

    @Override
    public String getQueryString()
    {
        return req.getQueryString();
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        return req.getReader();
    }

    @Override
    public String getRealPath(String arg0)
    {
        return req.getRealPath(arg0);
    }

    @Override
    public String getRemoteAddr()
    {
        return req.getRemoteAddr();
    }

    @Override
    public String getRemoteHost()
    {
        return req.getRemoteHost();
    }

    @Override
    public int getRemotePort()
    {
        return req.getRemotePort();
    }

    @Override
    public String getRemoteUser()
    {
        return req.getRemoteUser();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String arg0)
    {
        return req.getRequestDispatcher(arg0);
    }

    @Override
    public String getRequestURI()
    {
        return req.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL()
    {
        return req.getRequestURL();
    }

    @Override
    public String getRequestedSessionId()
    {
        return req.getRequestedSessionId();
    }

    @Override
    public String getScheme()
    {
        return req.getScheme();
    }

    @Override
    public String getServerName()
    {
        return req.getServerName();
    }

    @Override
    public int getServerPort()
    {
        return req.getServerPort();
    }

    @Override
    public ServletContext getServletContext()
    {
        return req.getServletContext();
    }

    @Override
    public String getServletPath()
    {
        return req.getServletPath();
    }

    @Override
    public HttpSession getSession()
    {
        return req.getSession();
    }

    @Override
    public HttpSession getSession(boolean arg0)
    {
        return req.getSession(arg0);
    }

    @Override
    public Principal getUserPrincipal()
    {
        return req.getUserPrincipal();
    }

    @Override
    public boolean isAsyncStarted()
    {
        return req.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported()
    {
        return req.isAsyncSupported();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie()
    {
        return req.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL()
    {
        return req.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl()
    {
        return req.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean isRequestedSessionIdValid()
    {
        return req.isRequestedSessionIdValid();
    }

    @Override
    public boolean isSecure()
    {
        return req.isSecure();
    }

    @Override
    public boolean isUserInRole(String arg0)
    {
        return req.isUserInRole(arg0);
    }

    @Override
    public void login(String arg0, String arg1) throws ServletException
    {
        req.login(arg0, arg1);
    }

    @Override
    public void logout() throws ServletException
    {
        req.logout();
    }

    @Override
    public void removeAttribute(String arg0)
    {
        req.removeAttribute(arg0);
    }

    @Override
    public void setAttribute(String arg0, Object arg1)
    {
        req.setAttribute(arg0, arg1);
    }

    @Override
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
    {
        req.setCharacterEncoding(arg0);
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException
    {
        return req.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException
    {
        return req.startAsync(arg0, arg1);
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException
    {
        return req.upgrade(arg0);
    }
}

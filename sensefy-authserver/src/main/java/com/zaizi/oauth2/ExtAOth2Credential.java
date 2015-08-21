package com.zaizi.oauth2;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Base64Utils;

public class ExtAOth2Credential
{
    private static final Logger LOG = LoggerFactory.getLogger(ExtAOth2Credential.class);
    public static final String CREDENDIAL_HEADER_SEPARATOR = ":";
    public static final String CREDENDIAL_HEADER_KEY = "authorization";
    public static final String CREDENDIAL_HEADER_PREFIX_VALUE = "Basic ";

    private final String userName, password, domain, providerName;
    private Collection<SimpleGrantedAuthority> authorities;

    public ExtAOth2Credential(final String providerName, final String userName, final String password,
            final String domain)
    {
        this.providerName = providerName;
        this.userName = userName;
        this.password = password;
        this.domain = domain;
    }

    public String getProviderName()
    {
        return providerName;
    }

    public Collection<SimpleGrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }

    // public static Credential getCredentialFromHeader(
    // final HttpServletRequest request) {
    // // --------------01234567
    // // authorization:Basic enp6enp6Omtra2tra2traw==
    // String authorization = request.getHeader(CREDENDIAL_HEADER_KEY);
    // if (authorization == null || authorization.length() < 7) {
    // LOG.warn("Error parsing authorisation header. Header '"
    // + CREDENDIAL_HEADER_KEY + "' is shorter than 7 chars");
    // return null;
    // }
    // authorization = authorization.substring(6);
    // final String credential = new String(
    // Base64Utils.decodeFromString(authorization));
    // final String[] usrPwd = credential.split(CREDENDIAL_HEADER_SEPARATOR);
    // if (usrPwd.length < 2) {
    // LOG.warn("Error parsing authorisation header, no separator found '"
    // + CREDENDIAL_HEADER_SEPARATOR + "'");
    // return null;
    // }
    // return new Credential(usrPwd[0], usrPwd[1], null);
    // }

    public String buildCredentialHeaderB64()
    {
        final String usrPwd = new StringBuffer().append(userName).append(CREDENDIAL_HEADER_SEPARATOR).append(
                providerName).toString();
        final String usrPwdB64 = Base64Utils.encodeToString(usrPwd.getBytes());
        return CREDENDIAL_HEADER_PREFIX_VALUE + usrPwdB64;
    }

    public void addCredentialToHeader(final OAuth2HttpRequestWrapper req)
    {
        req.setHeader(CREDENDIAL_HEADER_KEY, buildCredentialHeaderB64());
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDomain()
    {
        return domain;
    }
}

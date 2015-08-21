package com.zaizi.oauth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.ExceptionTranslationFilter;

public abstract class AbstractLoginFilter implements Filter
{
    private static final Logger LOG = Logger.getLogger(AbstractLoginFilter.class);

    public static final ThreadLocal<OAuth2HttpRequestWrapper> oauth2HttpRequestWrapper = new ThreadLocal<>();

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void destroy()
    {
    }

    @Override
    public final void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain)
            throws IOException, ServletException
    {
        final OAuth2HttpRequestWrapper request = new OAuth2HttpRequestWrapper((HttpServletRequest) req);
        final HttpServletResponse response = (HttpServletResponse) resp;
        boolean callChainDoFilter = true;
        if (doLogin(request))
        {
            try
            {
                callChainDoFilter = _doFilter(request, response);
            }
            catch (final Exception e)
            {
                LOG.error(e.getMessage(), e);
                throw e;
            }
        }

        if (callChainDoFilter)
        {
            if (request.getExtOAuth2credential() != null)
            {
                oauth2HttpRequestWrapper.set(request);
            }
            try
            {
                chain.doFilter(request, response);
            }
            finally
            {
                oauth2HttpRequestWrapper.remove();
            }
        }
    }

    protected abstract boolean _doFilter(OAuth2HttpRequestWrapper request, HttpServletResponse response)
            throws IOException, ServletException;

    protected abstract boolean doLogin(final HttpServletRequest httpRequest);

    public void addFilter(final HttpSecurity http)
    {
        http.addFilterBefore(this, ExceptionTranslationFilter.class);
        // http.addFilterBefore(this, FilterSecurityInterceptor.class);
    }

    protected Collection<SimpleGrantedAuthority> buildAuthorityCollection(final String... roleLists)
    {
        final Set<String> roles = new HashSet<>();
        for (final String roleList : roleLists)
        {
            if ((roleList != null) && !roleList.isEmpty())
            {
                roles.addAll(Arrays.asList(roleList.split(",")));
            }
        }
        final Collection<SimpleGrantedAuthority> authorities = buildAuthorityCollection(roles);
        return authorities;
    }

    // protected Collection<SimpleGrantedAuthority> buildAuthorityCollection(
    // final String... roles) {
    // return buildAuthorityCollection(Arrays.asList(roles));
    // }

    protected Collection<SimpleGrantedAuthority> buildAuthorityCollection(final Iterable<String> roles)
    {
        final Collection<SimpleGrantedAuthority> res = new ArrayList<>();
        for (final String role : roles)
        {
            res.add(new SimpleGrantedAuthority(role.trim()));
        }
        return res;
    }
}

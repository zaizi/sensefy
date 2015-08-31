package com.zaizi.sensefy.ui;

import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@EnableMBeanExport(defaultDomain="sensefy-ui-app")
public class SensefySearchUiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// SpringApplication.run(SensefySearchUiApplication.class, args);

		SpringApplication.run(SensefySearchUiApplication.class, args);
		int port = Integer.parseInt(System.getProperty("jetty.port", "8080"));
		int requestHeaderSize = Integer.parseInt(System.getProperty("jetty.header.size", "65536"));
		Server server = new Server(port);
		ProtectionDomain domain = SensefySearchUiApplication.class.getProtectionDomain();
		URL location = domain.getCodeSource().getLocation();

		// Set request header size
		// server.getConnectors()[0].setRequestHeaderSize(requestHeaderSize);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/ui");
		webapp.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
		webapp.setServer(server);
		webapp.setWar(location.toExternalForm());

		// (Optional) Set the directory the war will extract to.
		// If not set, java.io.tmpdir will be used, which can cause problems
		// if the temp directory gets cleaned periodically.
		// Your build scripts should remove this directory between deployments
		// webapp.setTempDirectory(new File("/path/to/webapp-directory"));

		server.setHandler(webapp);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Configuration
	protected static class SecurityConfiguration extends OAuth2SsoConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/login", "/logout").permitAll().anyRequest().authenticated().and()
					.csrf().csrfTokenRepository(csrfTokenRepository()).and()
					.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

		}

		// @Override
		// public void configure(HttpSecurity http) throws Exception {
		//
		//// http.authorizeRequests().anyRequest().authenticated().and().csrf()
		//// .csrfTokenRepository(csrfTokenRepository()).and()
		//// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		//
		// http.authorizeRequests().antMatchers("/login",
		// "/").permitAll().anyRequest()
		// .authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
		// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		//
		// /*
		// * http.authorizeRequests().antMatchers("/login").permitAll().
		// * anyRequest().hasRole("USER").and().csrf()
		// * .csrfTokenRepository(csrfTokenRepository()).and()
		// * .addFilterAfter(csrfHeaderFilter(),
		// * CsrfFilter.class).logout().deleteCookies("remove")
		// * .invalidateHttpSession(false);
		// */
		// }

		@Override
		public void match(RequestMatchers matchers) {
			matchers.anyRequest();
		}

		private Filter csrfHeaderFilter() {
			return new OncePerRequestFilter() {

				@Override
				protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
						FilterChain filterChain) throws ServletException, IOException {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
					if (csrf != null) {

						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
						String token = csrf.getToken();
						if (cookie == null || token != null && !token.equals(cookie.getValue())) {
							cookie = new Cookie("XSRF-TOKEN", token);
							cookie.setPath("/");
							response.addCookie(cookie);
							// response.setHeader("Access-Control-Allow-Origin",
							// "*");
							// response.setHeader("Access-Control-Allow-Methods",
							// "POST, GET, OPTIONS, DELETE");
							// response.setHeader("Access-Control-Max-Age",
							// "3600");
							// response.setHeader("Access-Control-Allow-Headers",
							// "x-requested-with");
						}

					}
					filterChain.doFilter(request, response);
				}
			};
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.context.web.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SensefySearchUiApplication.class);
	}

}

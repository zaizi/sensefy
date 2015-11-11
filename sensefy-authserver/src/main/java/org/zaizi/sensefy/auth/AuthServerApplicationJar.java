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
package org.zaizi.sensefy.auth;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.zaizi.sensefy.auth.service.AlfrescoLoginService;
import org.zaizi.sensefy.auth.user.SensefyUser;
import org.zaizi.sensefy.auth.user.acl.ACLRequester;

/**
 * 
 * @author mfahiz
 *
 */
@SpringBootApplication
@ComponentScan
@EnableResourceServer
public class AuthServerApplicationJar {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplicationJar.class, args);

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		private boolean doInitAuthMan = true;

		@Autowired
		private ACLRequester aclRequester;

		@Autowired
		private AlfrescoLoginService alfrescoLoginService;

		/*
		 * Domain for windows shares. Used to retrieve the ACLs from users (with
		 * and without domain)
		 */
		@Value("${sensefy.shares.domains}")
		private String sharesDomain;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			if (doInitAuthMan) {
				AbstractUserDetailsAuthenticationProvider ap = new AbstractUserDetailsAuthenticationProvider() {

					@Override
					protected UserDetails retrieveUser(String username,
							UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
						boolean login = alfrescoLoginService.login(username, (String) authentication.getCredentials());
						if (login) {
							SensefyUser user = new SensefyUser(username, (String) authentication.getCredentials(),
									Arrays.asList(new SimpleGrantedAuthority("USER"),
											new SimpleGrantedAuthority("ROLE_USER")));

							if (sharesDomain != null && !sharesDomain.isEmpty()) {
								List<String> domains = new LinkedList<String>();
								domains.add(sharesDomain);
								user.setDomains(domains);
							}

							user.initAcls(aclRequester);

							return user;

							// return new User(username, (String)
							// authentication.getCredentials(), Arrays.asList(
							// new SimpleGrantedAuthority("USER"), new
							// SimpleGrantedAuthority("ROLE_USER")));
						}
						setHideUserNotFoundExceptions(true);
						throw new BadCredentialsException("User not found or invalid credentials");
						
						//return null;
					}

					@Override
					protected void additionalAuthenticationChecks(UserDetails userDetails,
							UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
					}
				};

				ProviderManager pm = (ProviderManager) authenticationManager;
				List<AuthenticationProvider> providers = pm.getProviders();
				providers.clear();
				providers.add(ap);

				doInitAuthMan = false;
			}

			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

			clients.inMemory().withClient("sensefy").secret("sensefysecret")
					.authorizedGrantTypes("authorization_code", "refresh_token", "password").scopes("openid")
					.autoApprove(true)// .redirectUris("http://localhost:8080","oauthsample://authorize","https://localhost:8080")
					.and().withClient("sensefyMobile").secret("sensefyMobileSecret")
					.authorizedGrantTypes("authorization_code", "client_credentials").authorities("ROLE_CLIENT")
					.scopes("read", "trust").redirectUris("http://localhost:8080", "oauthsample://authorize");

		}

	}

}
package com.zaizi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@PropertySource({ "classpath:oauth2.properties", "classpath:application.yml" })
public class WebMvcConfig extends WebMvcAutoConfigurationAdapter {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	
	
	
//	@Bean
//	public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
//		ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
//		ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
//		contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
//		contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
//		contentViewResolver.setDefaultViews(Arrays.<View> asList(new MappingJackson2JsonView()));
//		return contentViewResolver;
//	}
//
//	@Bean
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/resources/template/");
//		viewResolver.setSuffix(".ftl");
//		return viewResolver;
//	}


	@Configuration
	@EnableOAuth2Client
	protected static class SensefyResourceConfiguration {

		@Value("${sensefy.accessTokenUri}")
		private String accessTokenUri;

		@Value("${sensefy.client.id}")
		private String clientId;

		@Value("${sensefy.userAuthorizationUri}")
		private String userAuthorizationUri;

		@Value("${sensefy.client.secret}")
		private String clientSecret;

		@Bean
		public OAuth2ProtectedResourceDetails sensefyAuth() {

			AuthorizationCodeResourceDetails sensefy = new AuthorizationCodeResourceDetails();
			sensefy.setId("sensefy");
			sensefy.setClientId(clientId);
			sensefy.setClientSecret(clientSecret);
			sensefy.setAccessTokenUri(accessTokenUri);
			sensefy.setUserAuthorizationUri(userAuthorizationUri);
			sensefy.setUseCurrentUri(Boolean.FALSE);

			return sensefy;
		}

		@Bean
		public OAuth2RestTemplate sensefyRestTemplate(OAuth2ClientContext clientContext) {

			return new OAuth2RestTemplate(sensefyAuth(), clientContext);

		}
	}

}

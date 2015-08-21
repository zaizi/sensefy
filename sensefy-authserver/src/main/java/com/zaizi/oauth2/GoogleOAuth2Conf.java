package com.zaizi.oauth2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@ConfigurationProperties(GoogleOAuth2Conf.ID)
@Component
public class GoogleOAuth2Conf extends AbstractOAuth2Conf {
	static final String ID = "google-oauth2";

	public GoogleOAuth2Conf() {
		setProviderType(ID);
		setScope("openid profile email");
	}
}

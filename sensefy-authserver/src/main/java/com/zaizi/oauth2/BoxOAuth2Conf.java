package com.zaizi.oauth2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@ConfigurationProperties(BoxOAuth2Conf.ID)
@Component
public class BoxOAuth2Conf extends AbstractOAuth2Conf {
	static final String ID = "box-oauth2";

	public BoxOAuth2Conf() {
		setProviderType(ID);
		setScope("");
	}
}

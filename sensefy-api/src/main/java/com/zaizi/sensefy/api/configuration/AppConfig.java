package com.zaizi.sensefy.api.configuration;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaizi.sensefy.api.dto.faceting.configuration.FacetConfigurer;

/**
 * @author mfahiz
 * @since 2.0
 */
@Configuration
public class AppConfig {

	@Bean
	public FacetConfigurer facetConfigurers() throws FileNotFoundException, JAXBException {
		return new FacetConfigurer();
	}

}

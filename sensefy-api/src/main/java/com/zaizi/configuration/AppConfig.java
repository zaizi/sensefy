package com.zaizi.configuration;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaizi.dto.faceting.configuration.FacetConfigurer;

/**
 * @author mfahiz
 * @since 2.0
 */
@Configuration
public class AppConfig {

	@Bean
	public FacetConfigurer facetConfigurers() throws FileNotFoundException, JAXBException{
		return new FacetConfigurer();
	}
	
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer(){
//	    return new error();
//	}
//	
//	private static class error implements EmbeddedServletContainerCustomizer {
//
//	    @Override
//	    public void customize(ConfigurableEmbeddedServletContainer container) {
//	        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
//	    }
//
//	}
	
}



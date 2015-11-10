/*******************************************************************************
 * Sensefy
 *
 * Copyright (c) Zaizi Limited, All rights reserved.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 *******************************************************************************/
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

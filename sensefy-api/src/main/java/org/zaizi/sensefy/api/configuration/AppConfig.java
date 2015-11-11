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
package org.zaizi.sensefy.api.configuration;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zaizi.sensefy.api.dto.faceting.configuration.FacetConfigurer;

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

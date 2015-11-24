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
package org.zaizi.sensefy.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;

/**
 * Spring boot initializer
 * 
 * @author mfahiz
 * @since 2.0
 */
@SpringBootApplication(exclude=SolrAutoConfiguration.class)
@EnableOAuth2Resource
@EnableAutoConfiguration(exclude=SolrAutoConfiguration.class)
public class SensefyResourceApplicationJar{

	public static void main(String[] args) {
		SpringApplication.run(SensefyResourceApplicationJar.class, args);
	}


}

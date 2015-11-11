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

import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;

/**
 * Spring boot initializer
 * 
 * @author mfahiz
 * @since 2.0
 */
@SpringBootApplication
@EnableOAuth2Resource
@EnableAutoConfiguration
public class SensefyResourceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SensefyResourceApplication.class, args);
		int port = Integer.parseInt(System.getProperty("jetty.port", "8080"));
		int requestHeaderSize = Integer.parseInt(System.getProperty("jetty.header.size", "65536"));
		Server server = new Server(port);
		server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
		ProtectionDomain domain = SensefyResourceApplication.class.getProtectionDomain();
		URL location = domain.getCodeSource().getLocation();

		// Set request header size
	//	server.getConnectors()[0].setRequestHeaderSize(requestHeaderSize);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/api");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.context.web.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SensefyResourceApplication.class);

	}

}

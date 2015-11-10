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
package sensefy.application.runner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import com.zaizi.sensefy.api.SensefyResourceApplication;
import com.zaizi.sensefy.ui.SensefySearchUiApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
public class App {
	public static void main(String[] args) {

		start(SensefyResourceApplication.class).properties("server.port=9080").run(args);
		//start(AuthServerApplication.class).properties("server.port=9090").run(args);
		 start(SensefySearchUiApplication.class).properties("server.port=9070").run(args);
		// start(Another.class).properties("server.port=${other.port:9000}").run(args);
	}

	private static SpringApplicationBuilder start(Class<?>... sources) {
		return new SpringApplicationBuilder(App.class).showBanner(false).child(sources).web(true);
	}
}

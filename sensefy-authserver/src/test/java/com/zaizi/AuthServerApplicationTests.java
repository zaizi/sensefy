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
package com.zaizi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.zaizi.sensefy.auth.AuthServerApplication;

/**
 * Unit tests for OAuth Server
 * @author mfahiz
 * @since 2.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AuthServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthServerApplicationTests {

	@Value("${local.server.port}")
	private int port;
	@Value("${server.contextPath}")
	private String contextPath;

	private RestTemplate template = new TestRestTemplate();

	@Test
	public void homePageProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + contextPath+"/", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		String auth = response.getHeaders().getFirst("WWW-Authenticate");
		assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
	}

	@Test
	public void userEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:" + port+ contextPath+"/", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		String auth = response.getHeaders().getFirst("WWW-Authenticate");
		assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
	}

	@Test
	public void authorizationRedirects() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + contextPath+"/oauth/authorize",
				String.class);
		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		String location = response.getHeaders().getFirst("Location");
		assertTrue("Wrong header: " + location, location.startsWith("http://localhost:" + port + contextPath+"/login"));
	}

	@Test
	public void loginSucceeds() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + contextPath+"/login",
				String.class);
		String csrf = getCsrf(response.getBody());
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("username", "admin");
		form.set("password", "ulibraxi");
		form.set("_csrf", csrf);
		HttpHeaders headers = new HttpHeaders();
		headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(form,
				headers, HttpMethod.POST, URI.create("http://localhost:" + port + contextPath+"/login"));
		ResponseEntity<Void> location = template.exchange(request, Void.class);
		assertEquals("http://localhost:" + port + contextPath+"/", location.getHeaders().getFirst("Location"));
	}

	private String getCsrf(String soup) {
		Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(soup);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
}

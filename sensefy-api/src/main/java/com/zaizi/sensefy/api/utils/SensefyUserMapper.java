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
package com.zaizi.sensefy.api.utils;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.zaizi.sensefy.api.controller.ServiceController;
import com.zaizi.sensefy.api.dto.SensefyUser;

/**
 * @author mfahiz
 *
 */
public class SensefyUserMapper {

	private static Log logger = LogFactory.getLog(ServiceController.class);

	@SuppressWarnings("unchecked")
	public static SensefyUser getSensefyUserFromPrincipal(Principal user) {

		SensefyUser sensefyUser = new SensefyUser();

		if (user != null) {
			OAuth2Authentication authUser = (OAuth2Authentication) user;
			if (authUser != null) {

				LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) authUser.getUserAuthentication()
						.getDetails();

				if (details != null && !details.isEmpty() && details.containsKey("principal")) {
					LinkedHashMap<String, Object> principal = (LinkedHashMap<String, Object>) details.get("principal");

					if (principal != null && !principal.isEmpty()) {
						try {
							BeanUtils.populate(sensefyUser, principal);
						} catch (IllegalAccessException e) {
							logger.debug(e.getMessage());
						} catch (InvocationTargetException e) {
							logger.debug(e.getMessage());
						}
					}

				}

			}

		}
		return sensefyUser;
	}
}

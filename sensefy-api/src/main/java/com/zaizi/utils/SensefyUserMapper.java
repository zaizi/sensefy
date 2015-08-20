/**
 * @author Fahiz Mohamed
 * @since  
 */
package com.zaizi.utils;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.zaizi.controller.ServiceController;
import com.zaizi.dto.SensefyUser;

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

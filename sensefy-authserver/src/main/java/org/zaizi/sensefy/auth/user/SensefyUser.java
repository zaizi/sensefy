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
/**
 * @author Fahiz Mohamed
 * @since  
 */
package org.zaizi.sensefy.auth.user;

import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zaizi.sensefy.auth.user.acl.ACLRequester;

/**
 * @author mfahiz
 *
 */
public class SensefyUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3080006360361149766L;

	private TimeZone timezone = TimeZone.getDefault();

	private List<String> userAcl;

	private List<String> domainAcl;

	private String username;

	private List<String> domains;

	/**
	 * @param username
	 * @param password
	 * @param authorities
	 */
	public SensefyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.username = username;

	}

	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	public List<String> getUserAcl() {
		return userAcl;
	}

	public void setUserAcl(List<String> userAcl) {
		this.userAcl = userAcl;
	}

	public List<String> getDomainAcl() {
		return domainAcl;
	}

	public void setDomainAcl(List<String> domainAcl) {
		this.domainAcl = domainAcl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public void initAcls(ACLRequester requester) {
		this.userAcl = requester.getACLs(this.username);
		if (domainAcl != null && !domainAcl.isEmpty()) {
			for (String domain : domainAcl) {
				String userDomain = domain.startsWith("@") ? domain : "@" + domain;
				domainAcl.addAll(requester.getACLs(this.username + userDomain));
			}
		}
	}

}

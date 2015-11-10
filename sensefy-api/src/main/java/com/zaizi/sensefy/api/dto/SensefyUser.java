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
package com.zaizi.sensefy.api.dto;

import java.util.List;
import java.util.TimeZone;

/**
 * @author mfahiz
 *
 */
public class SensefyUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3080006360361149766L;

	private String timezone = TimeZone.getDefault().getID();

	private List<String> userAcl;

	private List<String> domainAcl;

	private String username;

	private List<String> domains;

	public SensefyUser() {

	}

	public TimeZone getTimezone() {
		return TimeZone.getTimeZone(timezone);
	}
	
	public void setTimezone(String timezone) {
		this.timezone =timezone;
		 
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

}

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
package org.zaizi.sensefy.api.dto;

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

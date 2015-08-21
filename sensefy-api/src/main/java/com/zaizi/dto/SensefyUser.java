/**
 * @author Fahiz Mohamed
 * @since  
 */
package com.zaizi.dto;

import java.util.List;
import java.util.TimeZone;

import org.apache.solr.util.TimeZoneUtils;
import org.joda.time.DateTimeUtils;

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

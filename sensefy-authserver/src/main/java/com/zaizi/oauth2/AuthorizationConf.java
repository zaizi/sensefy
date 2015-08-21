package com.zaizi.oauth2;

import java.util.Map;

public class AuthorizationConf {
	public static String DEF_DOMAIN = "default";
	public static String PROVIDER_TYPE_ALFRESCO = "alfresco";
	public static String PROVIDER_TYPE_LDAP = "ldap";

	private String providerType;
	private Map<String, String> domainRoles = null;
	private Map<String, String> userRoles = null;

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(final String providerType) {
		this.providerType = providerType;
	}

	public Map<String, String> getDomainRoles() {
		return domainRoles;
	}

	public String getDefaultDomainRoles() {
		return domainRoles.get(DEF_DOMAIN);
	}

	public void setDomainRoles(final Map<String, String> domainRoles) {
		this.domainRoles = domainRoles;
	}

	public Map<String, String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(final Map<String, String> userRoles) {
		this.userRoles = userRoles;
	}
}

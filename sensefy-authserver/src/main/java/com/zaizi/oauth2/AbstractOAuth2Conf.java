package com.zaizi.oauth2;

public class AbstractOAuth2Conf extends AuthorizationConf {

	private String clientId;// "85453529243-ag9n02jhaqdueloplhq1d0flebrd1okq.apps.googleusercontent.com";
	private String clientSecret;// "3h34HxPQ9yWCzAlafI8zyrmo";
	private String scope;
	private String accessTokenUri;// "https://www.googleapis.com/oauth2/v3/token";
	private String userAuthorizationUri;// "https://accounts.google.com/o/oauth2/auth";
	private String userInfoUri;// "https://www.googleapis.com/plus/v1/people/me";
	private String loginUrl;// = "/google-oauth";
	private String errorUrl;// = "/error";

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(final String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(final String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccessTokenUri() {
		return accessTokenUri;
	}

	public void setAccessTokenUri(final String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public String getUserAuthorizationUri() {
		return userAuthorizationUri;
	}

	public void setUserAuthorizationUri(final String userAuthorizationUri) {
		this.userAuthorizationUri = userAuthorizationUri;
	}

	public String getUserInfoUri() {
		return userInfoUri;
	}

	public void setUserInfoUri(final String userInfoUri) {
		this.userInfoUri = userInfoUri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}
}

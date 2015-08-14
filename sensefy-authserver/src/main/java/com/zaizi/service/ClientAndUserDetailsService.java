package com.zaizi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

public class ClientAndUserDetailsService implements UserDetailsService, ClientDetailsService {

	private final ClientDetailsService clients;

	private final UserDetailsService users;

	private final ClientDetailsUserDetailsService clientDetailsWrapper;

	public ClientAndUserDetailsService(ClientDetailsService clients, UserDetailsService users) {
		super();
		this.clients = clients;
		this.users = users;
		clientDetailsWrapper = new ClientDetailsUserDetailsService(this.clients);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clients.loadClientByClientId(clientId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			user = users.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			user = clientDetailsWrapper.loadUserByUsername(username);
		}
		return user;
	}

}

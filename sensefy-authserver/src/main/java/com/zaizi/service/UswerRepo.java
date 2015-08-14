package com.zaizi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.zaizi.entity.User;

@Repository
public class UswerRepo {
	
	
	public UserDetails findUser(String username){
		return User.create("admin", "pass", "ADMIN", "USER");
	}

}

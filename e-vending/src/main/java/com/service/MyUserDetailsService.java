package com.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Resource(name = "userService")
	private UserDAO userService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		com.model.User user = userService.findByUserName(username);
		return user;
	}
	
}
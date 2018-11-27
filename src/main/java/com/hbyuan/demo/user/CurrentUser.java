package com.hbyuan.demo.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

	private User user;

	private User getUser() {
		if (user == null) {
			user = (User) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		}
		return user;
	}

	public Collection<GrantedAuthority> getGrantedAuthorities() {
		return getUser().getAuthorities();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

}

package com.hbyuan.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hbyuan.demo.user.entity.UserEntity;
import com.hbyuan.demo.user.repo.UserRepo;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// get user details from database, username case insensitive
		UserEntity userEntity = userRepo.findByUsername(username.toLowerCase());
		if (userEntity == null) {
			throw new UsernameNotFoundException(
					"User not found, name: " + username);
		}
		// return user
		User user = new User(userEntity.getUsername(), userEntity.getPassword(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						userEntity.getGrantedAuthorities()));
		return user;
	}

}

package com.hbyuan.demo.user.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hbyuan.demo.config.Profiles;
import com.hbyuan.demo.user.Authority;
import com.hbyuan.demo.user.entity.UserEntity;
import com.hbyuan.demo.user.repo.UserRepo;

@Component
@Profile({Profiles.LOCAL, Profiles.TEST})
public class DemoUserInitializer implements InitializingBean {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void afterPropertiesSet() throws Exception {
		initUser("admin", "password", Authority.ADMIN, Authority.USER);
		initUser("guest", "password", Authority.USER);
	}

	private void initUser(String username, String password,
			Authority... roles) {
		UserEntity userEntity = userRepo.findByUsername(username);
		if (userEntity == null) {
			// create initial user
			userEntity = new UserEntity();
			userEntity.setUsername(username);
			userEntity.setGrantedAuthorities(
					StringUtils.arrayToCommaDelimitedString(roles));
			userEntity.setPassword(passwordEncoder.encode(password));
			userRepo.save(userEntity);
		}
	}

}

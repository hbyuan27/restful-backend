package com.hbyuan.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuditorAware implements AuditorAware<String> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getCurrentAuditor() {
		String username = null;
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			username = (auth == null) ? null : auth.getName();
		} catch (Exception e) {
			logger.error(
					"Failed to get username as auditor from security context.");
		}
		return username;
	}

}
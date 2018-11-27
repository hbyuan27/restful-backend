package com.hbyuan.demo.config;

import static com.hbyuan.demo.user.Authority.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hbyuan.demo.config.authentication.AuthenticationConfig;
import com.hbyuan.demo.config.authentication.JwtAuthenticationTokenFilter;
import com.hbyuan.demo.config.authentication.LdapConfig;
import com.hbyuan.demo.config.authentication.RestAuthenticationEntryPoint;
import com.hbyuan.demo.config.authentication.RestLoginFailureHandler;
import com.hbyuan.demo.config.authentication.RestLoginSuccessHandler;
import com.hbyuan.demo.config.authentication.RestLogoutSuccessHandler;
import com.hbyuan.demo.user.service.CurrentUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationConfig authConfig;

	@Autowired
	private CurrentUserDetailsService userDetailsService;

	@Autowired
	private RestLogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private RestLoginSuccessHandler loginSuccessHandler;

	@Autowired
	private RestLoginFailureHandler loginFailureHandler;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationTokenFilter jwtTokenFilter;

	private String[] publicAntPatterns = {
			"/api/**",
			"/v2/api-docs",
			"/swagger-*/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers(publicAntPatterns).permitAll()
			.antMatchers("/user/**", "/admin/**").hasAnyAuthority(ADMIN.name())
			.anyRequest().fullyAuthenticated()
			//.and().rememberMe().tokenValiditySeconds(60 * 60 * 24)
			.and()
//		// SpringMVC风格的login, logout
//		.formLogin()
//			.loginPage("/login")
//			.failureUrl("/login?error")
//			.permitAll()
//			.and()
//		.logout()
//			.logoutUrl("/logout")
//			.logoutSuccessUrl("/login")
//			.deleteCookies("JSESSIONID")
//			.permitAll();
		// RESTful风格的login, logout
		.formLogin()
			.successHandler(loginSuccessHandler)
			.failureHandler(loginFailureHandler)
			.permitAll()
			.and()
		.logout()
			// 需要严格logout的话, 可以设置仅允许 POST且必须携带 JWT token
			.logoutSuccessHandler(logoutSuccessHandler);

		// RESTful风格的错误处理, 如果要换成SpringMVC风格, 在main/resources/public/error下添加40x.html即可
		http
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
		.authenticationEntryPoint(authenticationEntryPoint);

		// 启用JWT过滤servlet请求
		http
		.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		// 已使用 JWT, 所以可以禁用csrf和session
		http
		.csrf()
			.disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 禁用缓存
		http
		.headers().cacheControl();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		switch (authConfig.getMode()) {
			case LDAP :
				LdapConfig ldapConfig = authConfig.getLdapConfig();
				LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> configure = auth
					.ldapAuthentication()
						.userDnPatterns(ldapConfig.dnPattern())
						.contextSource()
							.url(ldapConfig.url())
							.managerDn(ldapConfig.username())
							.managerPassword(ldapConfig.password())
							.and()
							.groupSearchBase(ldapConfig.searchBase());
				if (ldapConfig.isCompareEnabled()) {
					configure.passwordCompare()
						.passwordAttribute(ldapConfig.pwdAttribute())
						.passwordEncoder(ldapConfig.passwordEncoder());
				}
				break;
			case BASIC :
			default :
				auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
				break;
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
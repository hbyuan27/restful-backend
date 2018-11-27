package com.hbyuan.demo.config.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hbyuan.demo.user.service.CurrentUserDetailsService;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CurrentUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 检查 非login request是否符合规范 (如果是login request, 漏给下一个filter处理)
		String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (requestHeader != null
				&& requestHeader.startsWith(JwtTokenUtil.SCHEME_BEARER)) {
			// 解析 token, 检查合法性
			String token = requestHeader
					.substring(JwtTokenUtil.SCHEME_BEARER.length());
			if (jwtTokenUtil.isValid(token)) {
				String username = jwtTokenUtil.getUsernameFromToken(token);
				// 从数据库中获取 UserDetails 作为 Principle
				UserDetails user = userDetailsService
						.loadUserByUsername(username);
				/*
				 * 足够信任token的话, 可以用token中的信息构建Principle, 代替数据库访问.
				 * 但是必须保证Principle的class和{CurrentUser}中的一致
				 */
				Authentication authentication = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());
				// 把 Principle 放入 Authentication 中, 更新到 SecurityContext
				SecurityContextHolder.getContext()
						.setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}

}

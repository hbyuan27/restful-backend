package com.hbyuan.demo.config.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hbyuan.demo.common.JsonResponse;
import com.hbyuan.demo.user.entity.UserEntity;
import com.hbyuan.demo.user.repo.UserRepo;

@Component
public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication)
			throws IOException, ServletException {
		// 把当前 Authentication 放入 SecurityContext 里
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 使用 JwtUtil 生成 token, 通过 response 的 Authorization header 颁发给 client
		User userDetails = (User) authentication.getPrincipal();
		String token = jwtTokenUtil.generateToken(userDetails);
		httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION,
				JwtTokenUtil.SCHEME_BEARER + token);
		// 更新最后登录时间
		UserEntity userEntity = userRepo
				.findByUsername(userDetails.getUsername());
		userEntity.setLastSuccessLogin(new Date());
		userRepo.save(userEntity);
		// 更新Response, 返回login成功信息
		httpServletResponse.setStatus(HttpStatus.OK.value());
		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
		httpServletResponse
				.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		PrintWriter printWriter = httpServletResponse.getWriter();
		printWriter.write(JsonResponse.success().toJSONString());
		printWriter.flush();
	}

}

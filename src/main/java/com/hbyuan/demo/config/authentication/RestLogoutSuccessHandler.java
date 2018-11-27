package com.hbyuan.demo.config.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.hbyuan.demo.common.JsonResponse;
import com.hbyuan.demo.user.entity.UserEntity;
import com.hbyuan.demo.user.repo.UserRepo;

@Component
public class RestLogoutSuccessHandler
		extends
			HttpStatusReturningLogoutSuccessHandler {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// 清除SecurityContext中的Principle
		SecurityContextHolder.clearContext();
		// 更新最后登出时间
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			// TODO 如果当前 JWT 还没过期, 就把它放入 DB 的 token id blacklist 里
			UserEntity userEntity = userRepo
					.findByUsername(userDetails.getUsername());
			userEntity.setLastSuccessLogout(new Date());
			userRepo.save(userEntity);
		}
		// 更新Response, 返回logout成功信息
		response.setStatus(HttpStatus.OK.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JsonResponse.success().toJSONString());
		printWriter.flush();
		// 如果当前handler的父handler中有特殊逻辑, 记得调用父亲的方法
		// super.onLogoutSuccess(request, response, authentication);
	}

}

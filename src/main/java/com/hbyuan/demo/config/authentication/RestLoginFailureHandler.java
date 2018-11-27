package com.hbyuan.demo.config.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.hbyuan.demo.common.JsonResponse;

@Component
public class RestLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		JsonResponse body = JsonResponse.failed(401, exception.getMessage());
		PrintWriter printWriter = response.getWriter();
		printWriter.write(body.toJSONString());
		printWriter.flush();
	}

}

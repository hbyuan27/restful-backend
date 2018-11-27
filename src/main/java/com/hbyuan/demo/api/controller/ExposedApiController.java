package com.hbyuan.demo.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbyuan.demo.api.DemoApiBody;
import com.hbyuan.demo.common.CustomizedException;
import com.hbyuan.demo.common.DateUtil;
import com.hbyuan.demo.common.JsonResponse;
import com.hbyuan.demo.common.LocaleMessage;

@RestController
@RequestMapping("/api")
public class ExposedApiController {

	/*
	 * TODO 对于public API, 需要检查HttpRequest的Header中的Authentication,
	 * 解析其中的token来鉴权非系统用户
	 */

	@Autowired
	private LocaleMessage localeMessage;

	@Autowired
	private DateUtil dateUtil;

	@GetMapping("/demo/get")
	public JsonResponse getExternalData() {
		return JsonResponse.success(localeMessage.getMessage("DEMO_GET",
				"anonymous", dateUtil.formatDate(new Date())));
	}

	@PostMapping("/demo/post")
	public JsonResponse receiveExternalData(@RequestBody DemoApiBody body) {
		return JsonResponse.success(localeMessage.getMessage("DEMO_POST",
				body.getId(), body.getContent()));
	}

	@GetMapping("/demo/exception")
	public JsonResponse sendException() {
		String exMessage = localeMessage.getMessage("DEMO_EXCEPTION");
		throw new CustomizedException(12345, exMessage);
	}

}

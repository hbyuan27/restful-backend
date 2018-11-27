package com.hbyuan.demo.user.controller;

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
import com.hbyuan.demo.user.CurrentUser;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private LocaleMessage localeMessage;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private CurrentUser currentUser;

	@GetMapping("/demo/get")
	public JsonResponse getExternalData() {
		return JsonResponse.success(localeMessage.getMessage("DEMO_GET",
				currentUser.getUsername(), dateUtil.formatDate(new Date())));
	}

	@PostMapping("/demo/post")
	public JsonResponse receiveExternalData(@RequestBody DemoApiBody body) {
		return JsonResponse.success(localeMessage.getMessage("DEMO_POST"));
	}

	@GetMapping("/demo/exception")
	public JsonResponse sendException() {
		String exMessage = localeMessage.getMessage("DEMO_EXCEPTION");
		throw new CustomizedException(12345, exMessage);
	}

}

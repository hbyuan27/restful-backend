package com.hbyuan.demo.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 目前仅仅用于异常信息的国际化, 已阻止改变浏览器语言导致的 locale 变化.<br>
 * 要改变session的locale, 只能通过发送带有locale参数的HttpServletRequest来触发拦截器达成
 */
@Component
public class LocaleMessage {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LocaleResolver localeResolver;

	/**
	 * @param code：i18n消息代码
	 * @return 对应地区的消息字符串
	 */
	public String getMessage(String code) {
		return this.getMessage(code, new Object[]{});
	}

	public String getMessage(String code, Object... args) {
		return this.getMessage(code, getLocale(), args);
	}

	public String getMessage(String code, Locale locale, Object... args) {
		return messageSource.getMessage(code, args, "", locale);
	}

	public Locale getLocale() {
		Locale locale = localeResolver.resolveLocale(request);
		return (locale == null) ? Locale.getDefault() : locale;
	}

}
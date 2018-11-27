package com.hbyuan.demo.config;

import java.util.Locale;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver htmlViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".html");
		return resolver;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/logout").setViewName("login");
		registry.addViewController("/upload").setViewName("upload");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:/static/i18n/messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

	/**
	 * 此处使用了SessionLocaleResolver, 方便将来添加语言一键切换功能.<br>
	 * 目前 CustomizedException 的信息显示已经用这个 resolver 实现
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.getDefault());
		return localeResolver;
	}

	/**
	 * 发送带有locale参数的HttpServletRequest, 通过LocaleResolver改变当前session的locale值.
	 * 参数格式: ?locale=zh_CN
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * 配置上传文件的 multipart 尺寸
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 设置单个文件的最大尺寸
		factory.setMaxFileSize("20MB");
		// 设置全部文件的最大尺寸
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}

}

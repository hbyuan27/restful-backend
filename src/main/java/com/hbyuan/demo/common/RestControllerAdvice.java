package com.hbyuan.demo.common;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@ResponseBody
public class RestControllerAdvice {

	/**
	 * 处理全局异常, 错误代码 1
	 */
	@ExceptionHandler(Exception.class)
	public JsonResponse exceptionHandler(Exception ex) {
		return JsonResponse.failed(1, ex.getMessage());
	}

	/**
	 * 处理运行时异常, 错误代码 2
	 */
	@ExceptionHandler(RuntimeException.class)
	public JsonResponse runtimeExceptionHandler(RuntimeException ex) {
		return JsonResponse.failed(2, ex.getMessage());
	}

	/**
	 * 处理NullPointer异常, 错误代码 3
	 */
	@ExceptionHandler(NullPointerException.class)
	public JsonResponse nullPointerExceptionHandler(NullPointerException ex) {
		return JsonResponse.failed(3, ex.getMessage());
	}

	/**
	 * IO异常, 错误代码 4
	 */
	@ExceptionHandler(IOException.class)
	public JsonResponse iOExceptionHandler(IOException ex) {
		return JsonResponse.failed(4, ex.getMessage());
	}

	/**
	 * 文件上传下载异常, 错误代码 5
	 */
	@ExceptionHandler(MultipartException.class)
	public JsonResponse handleMultipartException(MultipartException ex) {
		return JsonResponse.failed(5, ex.getMessage());
	}

	/**
	 * 自定义异常, 错误代码请参考 {@link ExceptionCode}
	 */
	@ExceptionHandler(CustomizedException.class)
	public JsonResponse handleCustomizedException(CustomizedException ex) {
		return JsonResponse.failed(ex.getCode(), ex.getMessage());
	}

}

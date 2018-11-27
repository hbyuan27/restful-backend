package com.hbyuan.demo.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 统一的前后端 RESTful API 数据传递格式.
 * <li><b>code:</b> 返回 API 处理结果代码. 0 - 表示成功, 其他 - 错误码. 特定错误码可用于前端国际化
 * <li><b>message:</b> 返回 API 处理的消息
 * <li><b>data:</b> 返回 API 处理完的结果数据, 符合 JSON 格式
 */
public class JsonResponse implements Serializable {

	private static final long serialVersionUID = 7078703215911650393L;

	private final int code;

	private final String message;

	private final Object data;

	private JsonResponse(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = JSON.toJSON(data);
	}

	/**
	 * 返回默认成功信息'Success'和成功代码'0'
	 */
	public static JsonResponse success() {
		return success(null);
	}

	/**
	 * 返回默认成功信息'Success'和成功代码'0'. 同时返回应答数据
	 */
	public static JsonResponse success(Object data) {
		if (data == null) {
			data = new Object();
		}
		return new JsonResponse(0, "Success", data);
	}

	/**
	 * 返回默认错误信息'Failed'和错误代码'-1'
	 */
	public static JsonResponse failed() {
		return failed("Failed");
	}

	/**
	 * 返回默认错误代码'-1'和指定错误信息
	 */
	public static JsonResponse failed(String message) {
		return failed(-1, message, null);
	}

	/**
	 * 返回指定错误代码和错误信息
	 */
	public static JsonResponse failed(int code, String message) {
		return failed(code, message, null);
	}

	/**
	 * 返回指定错误代码, 错误信息和相关数据
	 */
	public static JsonResponse failed(int code, String message, Object data) {
		if (data == null) {
			data = new Object();
		}
		return new JsonResponse(code, message, data);
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

}

package com.hbyuan.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	@Value("${format.date}")
	private String format;

	public String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		return (new SimpleDateFormat(format)).format(date);
	}

	public Date parseDate(String source) {
		if (source == null) {
			return null;
		}
		try {
			return (new SimpleDateFormat(format)).parse(source);
		} catch (Exception e) {
			throw new CustomizedException(1001, "Parse date failed, source: "
					+ source + "; format: " + format, e.getCause());
		}
	}

}

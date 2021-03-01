package com.platform.modules.iot.internal.utils;

import com.platform.modules.iot.ApiRuleException;
import com.platform.modules.iot.FileItem;
import com.platform.modules.iot.internal.utils.json.JSONReader;
import com.platform.modules.iot.internal.utils.json.JSONValidatingReader;

import java.io.IOException;
import java.util.List;





public class RequestCheckUtils {

	public static final String ERROR_CODE_ARGUMENTS_MISSING = "40";
	public static final String ERROR_CODE_ARGUMENTS_INVALID = "41";

	public static void checkNotEmpty(Object value, String fieldName) throws ApiRuleException {
		if (value == null) {
			throw new ApiRuleException(ERROR_CODE_ARGUMENTS_MISSING, "client-error:Missing required arguments:" + fieldName + "");
		}
		if (value instanceof String) {
			if (((String) value).trim().length() == 0) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_MISSING, "client-error:Missing required arguments:" + fieldName + "");
			}
		}
	}

	public static void checkMaxLength(String value, int maxLength, String fieldName) throws ApiRuleException {
		if (value != null) {
			if (value.length() > maxLength) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the string length of " + fieldName + " can not be larger than " + maxLength + ".");
			}
		}
	}

	public static void checkMaxLength(FileItem fileItem, int maxLength, String fieldName) throws ApiRuleException {
		try {
			if (fileItem != null && fileItem.getContent() != null) {
				if (fileItem.getContent().length > maxLength) {
					throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the file size of " + fieldName + " can not be larger than " + maxLength + ".");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void checkMaxListSize(String value, int maxSize, String fieldName) throws ApiRuleException {
		if (value != null) {
			String[] list = value.split(",");
			if (list != null && list.length > maxSize) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the array size of " + fieldName + " must be less than " + maxSize + ".");
			}
		}
	}

	public static void checkObjectMaxListSize(String value, int maxSize, String fieldName) throws ApiRuleException {
		if (value != null) {
			JSONReader reader = new JSONValidatingReader();
			Object obj = reader.read(value);
			if (obj instanceof List<?> && ((List<?>) obj).size() > maxSize) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the array size of " + fieldName + " must be less than " + maxSize + ".");
			}
		}
	}

	public static void checkMaxValue(Long value, long maxValue, String fieldName) throws ApiRuleException {
		if (value != null) {
			if (value > maxValue) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the value of " + fieldName + " can not be larger than " + maxValue + ".");
			}
		}
	}

	public static void checkMinValue(Long value, long minValue, String fieldName) throws ApiRuleException {
		if (value != null) {
			if (value < minValue) {
				throw new ApiRuleException(ERROR_CODE_ARGUMENTS_INVALID, "client-error:Invalid arguments:the value of " + fieldName + " can not be less than " + minValue + ".");
			}
		}
	}
}

package com.example.utils;

/***
 * 
 * @author saps.weaver
 *
 */
public class StringUtils {

	public static String ifNull(Object obj, String defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return obj.toString();
	}

	public static String ifNull(Object obj) {
		return ifNull(obj, "");
	}

	public static long ifLongNull(Object obj, long defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Long.parseLong(obj.toString());
	}

	public static long ifLongNull(Object obj) {
		return ifLongNull(obj, 0);
	}

	public static int ifIntNull(Object obj, int defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Integer.parseInt(obj.toString());
	}

	public static int ifIntNull(Object obj) {
		return ifIntNull(obj, 0);
	}

	public static boolean isNull(Object obj) {
		if (obj == null || "".equals(obj.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
}
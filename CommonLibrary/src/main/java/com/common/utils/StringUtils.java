package com.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 */
public class StringUtils {

	private StringUtils() {}

	/**
	 * 是否为空白的，带有去掉空格
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	public static String nullStrToEmpty(String str) {
		return (str == null ? "" : str);
	}

	/**
	 * 首字母转换为大写
	 *
	 * @param str
	 * @return
	 */
	public static String capitalizeFirstLetter(String str) {
		if (isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
				: new StringBuilder(str.length())
						.append(Character.toUpperCase(c))
						.append(str.substring(1)).toString();
	}

	/**
	 * 给字符串utf-8编码
	 *
	 * @param str
	 * @return
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * 给字符串utf-8编码
	 *
	 * @param str
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * 给字符串utf-8解码
	 *
	 * @param str
	 * @return
	 */
	public static String utf8Decode(String str) {
		if (!isEmpty(str)) {
			try {
				return URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * 对字符串进行反序
	 */
	public static String reverseStr(String str) {
		StringBuilder result = new StringBuilder(str).reverse();
		return result.toString();
	}

	/**
	 * 是否内容全为数字
	 *
	 * @param text
	 * @return
	 */
	public static boolean isWholeDigit(String text) {
		boolean isWholeDigit = true;
		for (int i = 0; i < text.length(); i++) {
			boolean isDigit = Character.isDigit(text.charAt(i));
			if (!isDigit) {
				isWholeDigit = false;
				break;
			}
		}
		return isWholeDigit;
	}

	// 顺序表
	static String orderStr = "";
	static {
		for (int i = 33; i < 127; i++) {
			orderStr += Character.toChars(i)[0];
		}
	}

	/**
	 * 判断是否有顺序
	 *
	 * @param str
	 *            传入的字符串
	 * @return
	 */
	public static boolean isOrder(String str) {
		if (!str.matches("((\\d)|([a-z])|([A-Z]))+")) {
			return false;
		}
		return orderStr.contains(str);
	}

	/**
	 * 去掉空格
	 *
	 * @param str 如:"138 0013 8000"为：13800138000
	 * @return 返回:"13800138000"
	 */
	public static String removeBlank(String str) {
		String tempStr = str.replace(" ", "");
		return tempStr;
	}

	/**
	 * 得到手机号码最后4位
	 *
	 * @param phoneNumber
	 * @return
	 */
	public static String getPhoneLast4Number(String phoneNumber) {
		String last4Number;
		phoneNumber = removeBlank(phoneNumber);
		int beginIndex = 7, endIndex = phoneNumber.length();
		last4Number = phoneNumber.substring(beginIndex, endIndex);
		return last4Number;
	}

	/**
	 * 中文为2个字符的长度
	 *
	 * @param content
	 * @return
	 */
	public static int trulyLength(String content) {
		int length = 0;
		String chinese = "[\\u4e00-\\u9fa5]";
		for (int i = 0; i < content.length(); i++) {
			String temp = content.substring(i, i + 1);
			if (temp.matches(chinese)) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * 加密身份证中间后四位（只用做加密，不可逆）
	 *
	 * @param encryptIds
	 * @return
	 */
	public static String getEncrypt4NumIds(String encryptIds) {
		char[] returnEncryptyIds = encryptIds.toCharArray();
		int startIndex = 10, endIndex = 14;
		if (encryptIds.length() > endIndex) {
			for (int i = startIndex; i < endIndex; i++) {
				returnEncryptyIds[i] = '*';
			}
		}
		return new String(returnEncryptyIds);
	}

	/**
	 * 把email的中间替换成*，保留用户名前两位和最后一位
	 *
	 * @param email 传入的email:"abcdef@berchina.com"
	 * @return 返回"ab***f@berchina.com"
	 */
	public static String replaceEmailMiddle2Asterisk(String email) {
		char[] emailCharArray = email.toCharArray();
		int place = email.indexOf("@");

		// @前面的邮箱用户名大于2个才去加上*号，否则不加
		if (place > 2) {
			int startIndex = 2, endIndex = place - 1;
			for (int i = startIndex; i < endIndex; i++) {
				emailCharArray[i] = '*';
			}
		}
		return new String(emailCharArray);
	}

	/**
	 * 把身份证号的中间替换成*
	 *
	 * @param idNumber 传入的身份证号:"441702198808084321"
	 * @return 返回"4417****4321"
	 */
	public static String replaceIdNumberMiddle2Asterisk(String idNumber) {
		char[] idNumberCharArray = idNumber.toCharArray();
		int startIndex = 4, endIndex = idNumberCharArray.length - 4;
		for (int i = startIndex; i < endIndex; i++) {
			idNumberCharArray[i] = '*';
		}
		return new String(idNumberCharArray);
	}

	/**
	 * 把手机号的中间替换成*
	 *
	 * @param phoneNumber 传入的手机号:"18664548654"
	 * @return 返回"186****8654"
	 */
	public static String replacePhoneMiddle2Asterisk(String phoneNumber) {
		char[] phoneCharArray = phoneNumber.toCharArray();
		int startIndex = 3, endIndex = startIndex + 4;
		for (int i = startIndex; i < endIndex; i++) {
			phoneCharArray[i] = '*';
		}
		return new String(phoneCharArray);
	}
}

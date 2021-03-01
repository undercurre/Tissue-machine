package com.platform.modules.iot.internal.utils;

import com.platform.modules.iot.Constants;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;




/**
 * 字符串工具类。
 * 
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class StringUtils {

	private static final TimeZone TZ_GMT8 = TimeZone.getTimeZone(Constants.DATE_TIMEZONE);
	private static final String QUOT = "&quot;";
	private static final String AMP = "&amp;";
	private static final String APOS = "&apos;";
	private static final String GT = "&gt;";
	private static final String LT = "&lt;";

	private StringUtils() {}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if(length < 1)
			return false;
		
		int i = 0;
		if(length > 1 && chars[0] == '-')
			i = 1;
		
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 把名称转换为小写加下划线的形式。
	 */
	public static String toUnderlineStyle(String name) {
		StringBuilder newName = new StringBuilder();
		int len = name.length();
		for (int i = 0; i < len; i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					newName.append("_");
				}
				newName.append(Character.toLowerCase(c));
			} else {
				newName.append(c);
			}
		}
		return newName.toString();
	}

	/**
	 * 把名称转换为首字母小写的驼峰形式。
	 */
	public static String toCamelStyle(String name) {
		StringBuilder newName = new StringBuilder();
		int len = name.length();
		for (int i = 0; i < len; i++) {
			char c = name.charAt(i);
			if (i == 0) {
				newName.append(Character.toLowerCase(c));
			} else {
				newName.append(c);
			}
		}
		return newName.toString();
	}

	/**
	 * 把字符串解释为日期对象，采用yyyy-MM-dd HH:mm:ss的格式。
	 */
	public static Date parseDateTime(String str) {
		DateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		format.setTimeZone(TZ_GMT8);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对日期进行字符串格式化，采用yyyy-MM-dd HH:mm:ss的格式。
	 */
	public static String formatDateTime(Date date) {
		DateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		format.setTimeZone(TZ_GMT8);
		return format.format(date);
	}

	/**
	 * 对日期进行字符串格式化，采用指定的格式。
	 */
	public static String formatDateTime(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(TZ_GMT8);
		return format.format(date);
	}

	/**
	 * XML字符转义包括(<,>,',&,")五个字符.
	 * 
	 * @param value 所需转义的字符串
	 * 
	 * @return 转义后的字符串 @
	 */
	public static String escapeXml(String value) {
		StringBuilder writer = new StringBuilder();
		char[] chars = value.trim().toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case '<':
				writer.append(LT);
				break;
			case '>':
				writer.append(GT);
				break;
			case '\'':
				writer.append(APOS);
				break;
			case '&':
				writer.append(AMP);
				break;
			case '\"':
				writer.append(QUOT);
				break;
			default:
				if ((c == 0x9) || (c == 0xA) || (c == 0xD) || ((c >= 0x20) && (c <= 0xD7FF))
						|| ((c >= 0xE000) && (c <= 0xFFFD)) || ((c >= 0x10000) && (c <= 0x10FFFF)))
					writer.append(c);
			}
		}
		return writer.toString();
	}

	/**
	 * 获取类的get/set属性名称集合。
	 * 
	 * @param clazz 类
	 * @param isGet 是否获取读方法，true为读方法，false为写方法
	 * @return 属性名称集合
	 */
	public static Set<String> getClassProperties(Class<?> clazz, boolean isGet) {
		Set<String> propNames = new HashSet<String>();
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (PropertyDescriptor prop : props) {
				String name = prop.getName();
				Method method;
				if (isGet) {
					method = prop.getReadMethod();
				} else {
					method = prop.getWriteMethod();
				}
				if (!"class".equals(name) && method != null) {
					propNames.add(name);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return propNames;
	}

	   /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */   
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
    
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
     private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

 	public static byte numToByte(int num){
		return (byte) num;
	}
	public static byte[] compleBytes(byte[] srcBytes, int baseDigt, byte compleBits){
		byte[] bytes = null;
		if(srcBytes.length % baseDigt != 0){
			bytes = new byte[(srcBytes.length/baseDigt+1)*baseDigt];
			System.arraycopy(srcBytes, 0, bytes, 0, srcBytes.length);
			for(int i=0; i+srcBytes.length<bytes.length; i++){
				bytes[srcBytes.length+i] = compleBits;
			}
		}else{
			bytes = srcBytes;
		}
		return bytes;
	}
	public static byte[] removeCompleBytes(byte[] bytes, byte compleBits){
		int pos = bytes.length;
		while(bytes[--pos] == compleBits);
		byte[] newBytes = new byte[++pos];
		System.arraycopy(bytes, 0, newBytes, 0, pos);
		return newBytes;
	}
}

package com.platform.modules.iot.internal.utils;



import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


@SuppressWarnings("restriction")
public class ApaasSignUtil {

	public static final String _app_id = "app_id";
	public static final String _trans_id = "trans_id";
	public static final String _timestamp = "timestamp";
	public static final String _token = "token";
	public static final String _app_secrect = "app_secrect";
	public static final SimpleDateFormat TIMESTAMP_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

	public static void buildAppParams(Map<String, Object> map) throws NoSuchAlgorithmException, IOException{
//		Date date = new Date();
//		String timestamp = TIMESTAMP_SDF.format(date);
//		String serialNumber = SERIAL_NUMBER_SDF.format(date) + String.valueOf(1000000 + (int)(Math.random()*1000000)).substring(1);
//		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//		parameters.put(_app_id, map.get("_app_id"));
//		parameters.put(_serial_number, serialNumber);
//		parameters.put(_timestamp, timestamp);
//		parameters.put(_key, map.get(_key));

//		String sign = createSign("UTF-8",parameters);

		Map<String, Object> result = SM3.makeToken(map.get(_app_id).toString(), map.get(_app_secrect).toString());
        map.put("token", result.get("token"));
        map.put("trans_id", result.get("trans_id"));
        map.put("timestamp", result.get("timestamp"));
		map.remove(_app_secrect);
	}
	/**
	 * @Description：sign签名
	 * @param characterEncoding
	 *            编码格式
	 * @param parameters
	 *            请求参数
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("rawtypes")
    public static String createSign(String characterEncoding,
			SortedMap<Object, Object> parameters) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (v instanceof String[]) {
				v = ((String[]) v)[0];
			}
			if (null != v && !"".equals(v) && !_app_secrect.equals(k)) {
				sb.append(k).append(v);
			}
		}
		sb.append(parameters.get(_app_secrect));
		return getMD5Encode(characterEncoding, sb.toString());
	}

	/**
	 * md5 base64加密
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5Encode(String characterEncoding, String md5Code) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			byte[] bytes = md5Code.getBytes(characterEncoding);
			BASE64Encoder base64 = new BASE64Encoder();
			md5Code = base64.encode(md5.digest(bytes));
			return md5Code;
	}
}

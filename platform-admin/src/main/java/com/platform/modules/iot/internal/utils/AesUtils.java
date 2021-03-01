package com.platform.modules.iot.internal.utils;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {
    @SuppressWarnings("unused")
	private static final String AES_ALGRITHOM = "AES";
    @SuppressWarnings({"unchecked", "rawtypes"})
	private static ThreadLocal<Cipher> aesCipher = new ThreadLocal() {
		protected Cipher initialValue() {
			try {
				return Cipher.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("getInstance Cipher Error!", e);
			} catch (NoSuchPaddingException e) {		
				throw new RuntimeException("getInstance Cipher Error!", e);
			}
		}
	};

	public static String decrypt(String paramValue, String key)
			throws Exception {
		return decrypt(paramValue, Base64.decode(key));
	}

	public static String decrypt(String paramValue, byte[] key)
			throws Exception {
		byte[] value = Base64.decode(ByteUtils.toBytes(paramValue));
		byte[] decrypt = decrypt(value, key);

		return ByteUtils.toString(decrypt);
	}

	public static String encrypt(String message, String key) throws Exception {
		return encrypt(message, Base64.decode(key));
	}

	public static String encrypt(String message, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		((Cipher) aesCipher.get()).init(1, skeySpec);

		byte[] encrypted = ((Cipher) aesCipher.get()).doFinal(ByteUtils
				.toBytes(message));
		return Base64.encodeToString(encrypted, false);
	}

	public static byte[] decrypt(byte[] value, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		((Cipher) aesCipher.get()).init(2, skeySpec);
		byte[] decrypted = ((Cipher) aesCipher.get()).doFinal(value);
		return decrypted;
	}
}

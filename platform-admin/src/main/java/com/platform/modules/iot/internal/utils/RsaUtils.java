package com.platform.modules.iot.internal.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class RsaUtils {
    @SuppressWarnings("unused")
	private static final String ALGORITHM = "RSA";
    @SuppressWarnings("unused")
	private static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    @SuppressWarnings({"unchecked", "rawtypes"})
	private static ThreadLocal<Cipher> rsaCipher = new ThreadLocal() {
		protected Cipher initialValue() {
			try {
				return Cipher.getInstance("RSA/ECB/PKCS1Padding");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("getInstance Cipher Error!", e);
			} catch (NoSuchPaddingException e) {
				throw new RuntimeException("getInstance Cipher Error!", e);
			}
		}
	};

	public static String encrypt(String paramValue, String prikey)
			throws Exception {
		byte[] plainText = paramValue.getBytes();
		((Cipher) rsaCipher.get()).init(1, getPrivateKey(prikey));
		byte[] enBytes = ((Cipher) rsaCipher.get()).doFinal(plainText);
		return Base64.encodeToString(enBytes, false);
	}

	public static String encrypts(String paramValue, String pubkey)
			throws Exception {
		byte[] plainText = paramValue.getBytes();
		((Cipher) rsaCipher.get()).init(1, getPublicKey(pubkey));
		byte[] enBytes = ((Cipher) rsaCipher.get()).doFinal(plainText);
		return Base64.encodeToString(enBytes, false);
	}

	public static String decrypt(String paramValue, String pubkey)
			throws Exception {
		byte[] enBytes = Base64.decode(paramValue);
		((Cipher) rsaCipher.get()).init(2, getPublicKey(pubkey));
		byte[] deBytes = ((Cipher) rsaCipher.get()).doFinal(enBytes);
		return ByteUtils.toString(deBytes);
	}

	public static String decrypts(String paramValue, String prikey)
			throws Exception {
		byte[] enBytes = Base64.decode(paramValue);
		((Cipher) rsaCipher.get()).init(2, getPrivateKey(prikey));
		byte[] deBytes = ((Cipher) rsaCipher.get()).doFinal(enBytes);
		return ByteUtils.toString(deBytes);
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String pri = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAjSww8T8YtScn8zgjufcpPOFP5A2v8pnbKfVTGv225P2C1AIgtjYVbU+2RWr5IjfSZxm7lTkx6nDirj1Pg59uEwIDAQABAkEAi/1yWwhSm/DEMO9Oni51+iUDcAYSn+Pp7OWVD4LgRpmUEBt+2Pldo0bJzIsDF+86TUymXycFV1GDA8ZGUBo2YQIhAMe+AnEKxjDX+29Q0H1O0Gzujaj/4l3ebj6NkH6Kk+h/AiEAtO8k9H5LCxcdrP+1cec3fK2y5gYm7QMcvaoNmhEikG0CIQDB/0YXzMMZhWxrfS5Bxl6grkFgNscBLJwenRgOD0IAuQIga7Ig3ArEXkCPIGdAOCE5bNPzNWmaB9+fXuF2oSrr2O0CIQCnIZTaJ/4EUTv5DXdRyeuWZkvLgtsZOl8dc9foQhvPuw==";
		String pub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI0sMPE/GLUnJ/M4I7n3KTzhT+QNr/KZ2yn1Uxr9tuT9gtQCILY2FW1PtkVq+SI30mcZu5U5Mepw4q49T4OfbhMCAwEAAQ==";

		String mingwen = "dEJNeC7ONz7DAY5mkIwbxg==";
		String dd = encrypt(mingwen, pri);
	}
}
package com.platform.modules.iot.internal.utils;

import java.io.UnsupportedEncodingException;

public class ByteUtils {
	public static final int SIZEOF_LONG = 8;

	public static byte[] toBytes(long val) {
		byte[] b = new byte[SIZEOF_LONG];
		for (int i = SIZEOF_LONG-1; i > 0; i--) {
			b[i] = (byte) (int) val;
			val >>>= SIZEOF_LONG;
		}
		b[0] = (byte) (int) val;
		return b;
	}

	public static long toLong(byte[] bytes) {
		if (SIZEOF_LONG > bytes.length) {
			return 0L;
		}

		long l = 0L;
		for (int i = 0; i < SIZEOF_LONG; i++) {
			l <<= SIZEOF_LONG;
			l ^= bytes[i] & 0xFF;
		}
		return l;
	}

	public static byte[] toBytes(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static String toString(byte[] b) {
		return toString(b, 0);
	}

	public static String toString(byte[] b, int off) {
		if (b == null)
			return null;
		try {
			return new String(b, off, b.length - off, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
}
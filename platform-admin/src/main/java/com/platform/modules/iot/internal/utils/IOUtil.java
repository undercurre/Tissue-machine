package com.platform.modules.iot.internal.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
	public static final String LINE_SEPARATOR;
    @SuppressWarnings("unused")
	private static final int DEFAULT_BUFFER_SIZE = 4096;

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null)
				closeable.close();
		} catch (Exception ex) {
		}
	}

	public static List<String> readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

    @SuppressWarnings({"rawtypes", "unchecked"})
	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List list = new ArrayList();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}

		return list;
	}

	public static InputStream toInputStream(String input) {
		try {
			byte[] bytes = input.getBytes("UTF-8");
			return new ByteArrayInputStream(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static void copy(InputStream input, Writer output) {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	public static int copy(Reader input, Writer output) {
		long count = copyLarge(input, output);
		if (count > 2147483647L) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(Reader input, Writer output) {
		char[] buffer = new char[4096];
		long count = 0L;
		int n = 0;
		try {
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
				count += n;
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return count;
	}

	public static void copy(Reader input, OutputStream output)
			throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		copy(input, out);
		out.flush();
	}

	public static void copy(Reader input, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			OutputStreamWriter out = new OutputStreamWriter(output, encoding);
			copy(input, out);

			out.flush();
		}
	}

	public static String toString(InputStream input) {
		StringBuilderWriter sw = new StringBuilderWriter();
		copy(input, sw);
		return sw.toString();
	}

	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringBuilderWriter sw = new StringBuilderWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	public static String readFile(String fileName) throws IOException {
		StringWriter stringWriter = new StringWriter();
		copy(new FileInputStream(new File(fileName)), stringWriter);

		return stringWriter.toString();
	}

	static {
		StringBuilderWriter buf = new StringBuilderWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
		out.close();
	}
}

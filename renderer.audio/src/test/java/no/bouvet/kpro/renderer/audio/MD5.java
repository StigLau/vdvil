package no.bouvet.kpro.renderer.audio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public abstract class MD5 {
	public static String get(InputStream input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[4096];

		for (;;) {
			int read = input.read(buffer);
			if (read < 1)
				break;
			md.update(buffer, 0, read);
		}

		byte[] digest = md.digest();
		StringBuffer output = new StringBuffer();

		for (int index = 0; index < digest.length; index++) {
			int value = digest[index] & 0xFF;
			if (value < 16)
				output.append("0");
			output.append(Integer.toHexString(value));
		}

		return output.toString();
	}

	public static String get(byte[] input) throws Exception {
		return get(new ByteArrayInputStream(input));
	}

	public static String get(File file) throws Exception {
		FileInputStream input = new FileInputStream(file);

		try {
			return get(input);
		} finally {
			input.close();
		}
	}
}

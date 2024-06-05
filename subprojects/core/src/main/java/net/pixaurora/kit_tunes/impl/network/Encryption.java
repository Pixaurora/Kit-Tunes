package net.pixaurora.kit_tunes.impl.network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Encryption {
	public static String signMd5(String input) {
		return bytesToHex(getMd5Digest(input));
	}

	public static byte[] getMd5Digest(String input) {
		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to get MD5 algorithm.");
		}

		digester.update(input.getBytes(StandardCharsets.UTF_8));

		return digester.digest();
	}

	public static String bytesToHex(byte[] bytes) {
		List<Integer> hexDigits = new ArrayList<>();

		for (byte rawByte : bytes) {
			int processedByte = Byte.toUnsignedInt(rawByte);

			// Example byte: 11 01 10 00
			hexDigits.add(processedByte >> 4); // Grabs the part that is 11 01
			hexDigits.add(processedByte % 16); // Grabs the part that is 10 00
		}

		String output = "";

		for (int i : hexDigits) {
			output += Integer.toHexString(i);
		}

		return output;
	}
}

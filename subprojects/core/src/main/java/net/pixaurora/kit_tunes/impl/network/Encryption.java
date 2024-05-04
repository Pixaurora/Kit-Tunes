package net.pixaurora.kit_tunes.impl.network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Encryption {
	public static String signMd5(String input) {
		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to get MD5 algorithm.");
		}

		digester.update(input.getBytes(StandardCharsets.UTF_8));
		return HexFormat.of().formatHex(digester.digest());
	}
}

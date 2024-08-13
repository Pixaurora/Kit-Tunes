package net.pixaurora.kitten_heart.impl.network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        StringBuilder hexString = new StringBuilder(bytes.length * 2);

        for (byte rawByte : bytes) {
            int processedByte = Byte.toUnsignedInt(rawByte);

            // Example byte: 11 01 10 00
            hexString.append(Integer.toHexString(processedByte >> 0b100)); // Grabs the part that is 11 01
            hexString.append(Integer.toHexString(processedByte & 0b1111)); // Grabs the part that is 10 00
        }

        return hexString.toString();
    }
}

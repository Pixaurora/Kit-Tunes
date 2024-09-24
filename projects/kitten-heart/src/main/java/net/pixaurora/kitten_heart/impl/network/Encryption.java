package net.pixaurora.kitten_heart.impl.network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.pixaurora.catculator.impl.util.CryptoUtil;

public class Encryption {
    public static String signMd5(String input) {
        return CryptoUtil.toHex(getMd5Digest(input));
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
}

package com.example.wateronl.Helper.HMac;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMacUtil {
    public final static String HMACSHA256 = "HmacSHA256";

    /**
     * Calculates a message authentication code (MAC) and returns it as a hex-encoded string.
     *
     * @param algorithm The HMAC algorithm (e.g., "HmacSHA256").
     * @param key       The secret cryptographic key.
     * @param data      The message to be authenticated.
     * @return Hex-encoded HMAC String.
     * @throws NoSuchAlgorithmException if the specified algorithm is not available.
     * @throws InvalidKeyException      if the given key is inappropriate for initializing this MAC.
     */
    public static String HMacHexStringEncode(final String algorithm, final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
        // 1. Get an algorithm instance
        Mac macGenerator = Mac.getInstance(algorithm);

        // 2. Create a key spec from the key string
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);

        // 3. Initialize the MAC with the key
        macGenerator.init(signingKey);

        // 4. Compute the MAC on the input data
        byte[] hmacEncodedBytes = macGenerator.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // 5. Convert the byte array to a hex string using a reliable library and return
        return Hex.encodeHexString(hmacEncodedBytes);
    }
}

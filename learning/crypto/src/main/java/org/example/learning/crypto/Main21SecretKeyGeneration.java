package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Main21SecretKeyGeneration {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        {
            final byte[] keyBytes = Hex.decode("00112233445566778899aabbccddeeff");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, BouncyCastleProvider.PROVIDER_NAME);
            System.out.println(Hex.toHexString(secretKeySpec.getEncoded()));
        }
        {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            final SecretKey secretKey = keyGenerator.generateKey();
            System.out.println(Hex.toHexString(secretKey.getEncoded()));
        }
        {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            keyGenerator.init(512);
            final SecretKey secretKey = keyGenerator.generateKey();
            System.out.println(Hex.toHexString(secretKey.getEncoded()));
        }
    }
}

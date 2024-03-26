package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Main11MessageDigest {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        {
            final MessageDigest instance = MessageDigest.getInstance("SHA-256", BouncyCastleProvider.PROVIDER_NAME);
            final byte[] digest = instance.digest("secrdt".getBytes(StandardCharsets.UTF_8));
            System.out.println("digest: " + Hex.toHexString(digest));
        }
        {
            final MessageDigest instance = MessageDigest.getInstance("SHA-256", BouncyCastleProvider.PROVIDER_NAME);
            final byte[] digest = instance.digest("secret".getBytes(StandardCharsets.UTF_8));
            System.out.println("digest: " + Hex.toHexString(digest));
        }
        {
            // 3fb0700a41ce6e41413ba764f98bf2135ba6ded516bea2fae8429cc5bdd46d6d
            final MessageDigest instance = MessageDigest.getInstance("GOST3411-2012-256", BouncyCastleProvider.PROVIDER_NAME);
            final byte[] digest = instance.digest("secret".getBytes(StandardCharsets.UTF_8));
            System.out.println("digest: " + Hex.toHexString(digest));
        }
    }
}

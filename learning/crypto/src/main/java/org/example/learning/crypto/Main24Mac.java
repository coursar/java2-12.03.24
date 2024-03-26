package org.example.learning.crypto;

import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Main24Mac {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Security.addProvider(new BouncyCastleProvider());
        final Mac instance = Mac.getInstance("HmacSHA256", BouncyCastleProvider.PROVIDER_NAME);
        instance.init(new SecretKeySpec("secret".getBytes(StandardCharsets.UTF_8), "HMAC-SHA256"));
        final byte[] mac = instance.doFinal("hello".getBytes(StandardCharsets.UTF_8));
        System.out.println("mac: " + Hex.toHexString(mac));
    }
}

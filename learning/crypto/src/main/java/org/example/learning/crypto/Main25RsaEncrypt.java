package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;

public class Main25RsaEncrypt {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Security.addProvider(new BouncyCastleProvider());
        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
        generator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));

        final KeyPair keyPair = generator.generateKeyPair();
        final PrivateKey privateKey = keyPair.getPrivate();
        final PublicKey publicKey = keyPair.getPublic();

        final byte[] document = "document".getBytes(StandardCharsets.UTF_8);
        System.out.println("plaintext: " + Hex.toHexString(document));

        final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        final byte[] encrypted = cipher.doFinal(document);

        System.out.println("encrypted: " + Hex.toHexString(encrypted));

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        final byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("decrypted: " + Hex.toHexString(decrypted));
    }
}

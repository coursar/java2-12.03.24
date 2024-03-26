package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class Main23EncryptAndAuth {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("GCM mode");
        Security.addProvider(new BouncyCastleProvider());
        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
        final SecretKey secretKey = keyGenerator.generateKey();

        final byte[] input = Hex.decode("00112233");

        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", BouncyCastleProvider.PROVIDER_NAME);

        final SecureRandom secureRandom = new SecureRandom();
        final byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        final byte[] encrypted = cipher.doFinal(input);
        System.out.println("encrypted: " + Hex.toHexString(encrypted));

        // send encrypted text + taglen + iv
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        final byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("decrypted: " + Hex.toHexString(decrypted));

        byte[] encryptedCorrupted = Arrays.clone(encrypted);
        encryptedCorrupted[encryptedCorrupted.length - 1] += 1;
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        final byte[] decryptedCorrupted = cipher.doFinal(encryptedCorrupted);
        System.out.println("decrypted: " + Hex.toHexString(decryptedCorrupted));
        // AEADBadTagException

    }
}

package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class Main22Encrypt {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastleProvider());
        {
            System.out.println("ECB mode");
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            keyGenerator.init(256);
            final SecretKey secretKey = keyGenerator.generateKey();

            final byte[] input = Hex.decode("000102030405060708090a0b0c0d0e0f" + "000102030405060708090a0b0c0d0e0f");

            final Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final byte[] encrypted = cipher.doFinal(input);
            System.out.println("encrypted: " + Hex.toHexString(encrypted));

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final byte[] decrypted = cipher.doFinal(encrypted);
            System.out.println("decrypted: " + Hex.toHexString(decrypted));

            byte[] encryptedCorrupted = Arrays.clone(encrypted);
            encryptedCorrupted[encryptedCorrupted.length - 1] += 1; // mallory
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final byte[] decryptedCorrupted = cipher.doFinal(encryptedCorrupted);
            System.out.println("decrypted: " + Hex.toHexString(decryptedCorrupted));
        }

        {
            System.out.println("CBC mode");
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            final SecretKey secretKey = keyGenerator.generateKey();

            final byte[] input = Hex.decode("000102030405060708090a0b0c0d0e0f" + "000102030405060708090a0b0c0d0e0f");

            final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", BouncyCastleProvider.PROVIDER_NAME);

            final SecureRandom secureRandom = new SecureRandom();
            final byte[] iv = new byte[cipher.getBlockSize()];
            secureRandom.nextBytes(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final byte[] encrypted = cipher.doFinal(input);
            System.out.println("encrypted: " + Hex.toHexString(encrypted));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final byte[] decrypted = cipher.doFinal(encrypted);
            System.out.println("decrypted: " + Hex.toHexString(decrypted));

            byte[] encryptedCorrupted = Arrays.clone(encrypted);
            encryptedCorrupted[encryptedCorrupted.length - 1] += 1;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            final byte[] decryptedCorrupted = cipher.doFinal(encryptedCorrupted);
            System.out.println("decrypted: " + Hex.toHexString(decryptedCorrupted));
        }
    }
}

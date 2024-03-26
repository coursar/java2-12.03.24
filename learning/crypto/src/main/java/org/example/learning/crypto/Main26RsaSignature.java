package org.example.learning.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;

public class Main26RsaSignature {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException {
        Security.addProvider(new BouncyCastleProvider());
        final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
        generator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));

        final KeyPair keyPair = generator.generateKeyPair();
        final PrivateKey privateKey = keyPair.getPrivate();
        final PublicKey publicKey = keyPair.getPublic();

        final byte[] document = "document".getBytes(StandardCharsets.UTF_8);

        byte[] sign = null;

        {
            final Signature signature = Signature.getInstance("SHA256withRSA", BouncyCastleProvider.PROVIDER_NAME);
            signature.initSign(privateKey);
            signature.update(document);
            sign = signature.sign();
            System.out.println("signature: " + Hex.toHexString(sign));
        }

        {
            final Signature signature = Signature.getInstance("SHA256withRSA", BouncyCastleProvider.PROVIDER_NAME);
            signature.initVerify(publicKey);
            signature.update(document);
            final boolean verified = signature.verify(sign);
            System.out.println("verified: " + verified);
        }

        {
            System.out.println("corrupted");
            final byte[] corrupted = Arrays.clone(sign);
            corrupted[corrupted.length - 1] += 1;
            final Signature signature = Signature.getInstance("SHA256withRSA", BouncyCastleProvider.PROVIDER_NAME);
            signature.initVerify(publicKey);
            signature.update(document);
            final boolean verified = signature.verify(corrupted);
            System.out.println("verified: " + verified);
        }
    }
}

package org.example.learning.crypto;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyAgreement;
import java.security.*;

public class Main27ECDH {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        Security.addProvider(new BouncyCastleProvider());

        ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("Curve25519");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        generator.initialize(parameterSpec);

        // Server:
        KeyPair aKeyPair = generator.generateKeyPair();
        KeyAgreement aKeyAgreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        aKeyAgreement.init(aKeyPair.getPrivate());

        // Client:
        KeyPair bKeyPair = generator.generateKeyPair();
        KeyAgreement bKeyAgreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        bKeyAgreement.init(bKeyPair.getPrivate());

        // exchange public keys & generate secret
        aKeyAgreement.doPhase(bKeyPair.getPublic(), true);
        bKeyAgreement.doPhase(aKeyPair.getPublic(), true);

        // Server:
        byte[] aSecret = aKeyAgreement.generateSecret();
        // Client:
        byte[] bSecret = bKeyAgreement.generateSecret();

        System.out.println("a secret: " + Hex.toHexString(aSecret));
        System.out.println("b secret: " + Hex.toHexString(bSecret));

        System.out.println("secret equals: " + MessageDigest.isEqual(aSecret, bSecret));
    }
}

package org.example.learning.crypto;

import java.nio.charset.StandardCharsets;

public class Main00Crypto {
    public static void main(String[] args) {
        {
            // TODO: one-time pad
            //  plaintext
            //  secretkey
            final byte[] plaintext = {'c', 'o', 'd', 'e', ' ', 'm', 'o', 'o', 'n'};
            // final byte[] key = {10, 14, 2, 77, 80, 12, 29, 5, 8};
            final byte[] key = {19, 14, 9, 69, 80, 12, 29, 14, 3};
            final byte[] ciphertext = new byte[9];
            for (int i = 0; i < 9; i++) {
                byte p = plaintext[i];
                byte k = key[i];
                byte c = (byte) (p ^ k);
                ciphertext[i] = c;
            }
            System.out.println(new String(ciphertext, StandardCharsets.UTF_8));
        }

        // TODO:
        //  plaintext -> word word - 9 chars with space
        //  encrypt -> ciphertext (pam param)
        //  plaintext + secret (by bytes)
        //  known ciphertext (0-255, 256 values)
        {
            final byte[] ciphertext = {'p', 'a', 'm', ' ', 'p', 'a', 'r', 'a', 'm'};
            // final byte[] key = {10, 14, 2, 77, 80, 12, 29, 5, 8};
            // final byte[] key = {28, 14, 2, 77, 80, 12, 29, 5, 8};
            final byte[] key = {19, 14, 9, 69, 80, 12, 29, 14, 3}; // valid code
            final byte[] plaintext = new byte[9];
            for (int i = 0; i < 9; i++) {
                byte c = ciphertext[i];
                byte k = key[i];
                byte p = (byte) (c ^ k);
                plaintext[i] = p;
            }
            System.out.println(new String(plaintext, StandardCharsets.UTF_8));
        }
    }
}

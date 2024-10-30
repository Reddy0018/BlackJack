package com.blackjack.game.user;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class Encryption {

    private static final byte[] SECRET_KEY_BYTES = new byte[] {
            0x01, 0x23, 0x45, 0x67, 0x01, 0x23, 0x45, 0x67,
            0x01, 0x23, 0x45, 0x67, 0x01, 0x23, 0x45, 0x67
    };

    private static final String algorithm = "AES";

    // Create a SecretKey object from the constant key bytes
    private static final SecretKey SECRET_KEY = new SecretKeySpec(SECRET_KEY_BYTES, algorithm);

    // Method to encrypt a string using AES
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to decrypt a string using AES
    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes);
    }
}
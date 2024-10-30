package com.blackjack.game.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.sun.mail.iap.ByteArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.crypto.Cipher;
import java.util.Base64;

@SpringBootTest
public class EncryptionTest {

    private Encryption encryption;

    @BeforeEach
    public void setUp() {
        encryption = new Encryption();
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        String originalText = "Password";

        // Encrypt the original text
        String encryptedText = encryption.encrypt(originalText);
        assertNotNull(encryptedText);
        assertNotEquals(originalText, encryptedText, "Encrypted text should differ from original text");

        // Decrypt the encrypted text
        String decryptedText = encryption.decrypt(encryptedText);
        assertNotNull(decryptedText);
        assertEquals(originalText, decryptedText, "Decrypted text should match the original text");
    }

    @Test
    public void testEncryptDecryptEmptyString() throws Exception {
        String originalText = "";

        // Encrypt the original text
        String encryptedText = encryption.encrypt(originalText);
        assertNotNull(encryptedText);

        // Decrypt the encrypted text
        String decryptedText = encryption.decrypt(encryptedText);
        assertEquals(originalText, decryptedText, "Decrypted text should match the original empty text");
    }

    @Test
    public void testEncryptDecryptSpecialCharacters() throws Exception {
        String originalText = "Special characters: !@#$%^&*()_+";

        // Encrypt the original text
        String encryptedText = encryption.encrypt(originalText);
        assertNotNull(encryptedText);

        // Decrypt the encrypted text
        String decryptedText = encryption.decrypt(encryptedText);
        assertEquals(originalText, decryptedText, "Decrypted text should match the original text with special characters");
    }

    @Test
    public void testDecryptInvalidCipherText() {
        String invalidCipherText = "invalidCipherText";

        Exception exception = assertThrows(Exception.class, () -> {
            encryption.decrypt(invalidCipherText);
        });

        assertNotNull(exception.getMessage());
    }
}
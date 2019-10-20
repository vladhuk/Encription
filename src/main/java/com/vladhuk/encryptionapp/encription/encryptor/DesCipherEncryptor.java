package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DesCipherEncryptor implements Encryptor {

    private SecretKey secretKey;

    public DesCipherEncryptor(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public DesCipherEncryptor(String key) {
        try {
            byte[] byteKey = normalizeKey(key.getBytes(StandardCharsets.UTF_8));
            secretKey = new SecretKeySpec(byteKey, 0, 8, "DES");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] normalizeKey(byte[] key) {
        if (key.length >= 8) {
            return key;
        }
        final String strKey = new String(key);
        return normalizeKey((strKey + strKey).getBytes());
    }

    @Override
    public String encode(String text) {
        try {
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final byte[] utf8 = text.getBytes(StandardCharsets.UTF_8);
            final byte[] enc = cipher.doFinal(utf8);

            return new String(Base64.getEncoder().encode(enc));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(String text) {
        try {
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            final byte[] dec = Base64.getDecoder().decode(text);
            final byte[] utf8 = cipher.doFinal(dec);

            return new String(utf8, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char encode(char symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char decode(char symbol) {
        throw new UnsupportedOperationException();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
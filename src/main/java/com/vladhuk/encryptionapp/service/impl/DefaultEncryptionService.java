package com.vladhuk.encryptionapp.service.impl;

import com.vladhuk.encryptionapp.encription.Encryption;
import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.encription.encryptor.*;
import com.vladhuk.encryptionapp.service.EncryptionService;
import com.vladhuk.encryptionapp.util.Language;

public class DefaultEncryptionService implements EncryptionService {

    @Override
    public Encryptor getEncryptor(Encryption encryption, Language language, String key) {
        switch (encryption) {
            case CAESAR: return new CaesarEncryptor(language, Integer.valueOf(key));
            case XOR: return new XorCipherEnryptor(language, key);
            case BOOK: return new BookCipherEncryptor(key, 10);
            case DES: return new DesCipherEncryptor(key);
            case BAG:return new BagEncyptor(language, key);
            case RSA: return new RsaEncryptor(key);
            default: throw new IllegalArgumentException("No encryptors");
        }
    }

}

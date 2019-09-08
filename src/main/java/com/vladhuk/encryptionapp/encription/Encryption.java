package com.vladhuk.encryptionapp.encription;

import com.vladhuk.encryptionapp.encription.encryptor.CaesarEncryptor;
import com.vladhuk.encryptionapp.util.Language;

public enum Encryption {
    CAESAR {
        public Encryptor getEncryptor(Language language, int key) {
            return new CaesarEncryptor(language, key);
        }
    };

    public abstract Encryptor getEncryptor(Language language, int key);
}

package com.vladhuk.encription;

import com.vladhuk.encription.encryptor.CaesarEncryptor;
import com.vladhuk.util.Language;

public enum Encryption {
    CAESAR {
        public Encryptor getEncryptor(Language language, int key) {
            return new CaesarEncryptor(language, key);
        }
    };

    public abstract Encryptor getEncryptor(Language language, int key);
}

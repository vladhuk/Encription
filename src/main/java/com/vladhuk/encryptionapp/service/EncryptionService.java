package com.vladhuk.encryptionapp.service;

import com.vladhuk.encryptionapp.encription.Encryption;
import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;

public interface EncryptionService {

    Encryptor getEncryptor(Encryption encryption, Language language, String key);

}

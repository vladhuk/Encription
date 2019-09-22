package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XorCipherEnryptorTest {

    @Test
    public void encode_string_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new XorCipherEnryptor(Language.ENGLISH, "bb");
        final String text = "az";
        final String expectedText = "ba";

        assertEquals(expectedText, encryptor.encode(text));
    }

    @Test
    public void decode_string_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new XorCipherEnryptor(Language.ENGLISH, "bb");
        final String text = "ba";
        final String expectedText = "az";

        assertEquals(expectedText, encryptor.decode(text));
    }

}

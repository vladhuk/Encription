package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaesarEncryptorTest {

    @Test
    public void encode_symbol_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new CaesarEncryptor(Language.ENGLISH, 1);
        final char symbol = 'H';
        final char expectedSymbol = 'I';

        assertEquals(expectedSymbol, encryptor.encode(symbol));
    }

    @Test
    public void encode_string_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new CaesarEncryptor(Language.ENGLISH, 1);
        final String text = "Hello! Z";
        final String expectedText = "Ifmmp! A";

        assertEquals(expectedText, encryptor.encode(text));
    }

    @Test
    public void decode_symbol_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new CaesarEncryptor(Language.ENGLISH, 1);
        final char symbol = 'I';
        final char expectedSymbol = 'H';

        assertEquals(expectedSymbol, encryptor.decode(symbol));
    }

    @Test
    public void decode_string_When_LanguageCorrect_Expected_CorrectResult() {
        final Encryptor encryptor = new CaesarEncryptor(Language.ENGLISH, 1);
        final String text = "Ifmmp! A";
        final String expectedText = "Hello! Z";

        assertEquals(expectedText, encryptor.decode(text));
    }

}

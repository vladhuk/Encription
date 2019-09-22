package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;


public class XorCipherEnryptor implements Encryptor {

    private Language language;

    private String key;

    public XorCipherEnryptor(Language language, String key) {
        this.language = language;
        this.key = key;
    }

    @Override
    public char encode(char symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char decode(char symbol) {
        throw new UnsupportedOperationException();
    }

    private String multiplyGamma(int finalLength) {
        final StringBuilder result = new StringBuilder(key);

        while (result.length() < finalLength) {
            result.append(key);
        }

        return result.toString();
    }

    @Override
    public String encode(String text) {
        final String gamma = key.length() < text.length()
                ? multiplyGamma(text.length())
                : key;
        final String letters = language.letters();
        final StringBuilder encryptedString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                encryptedString.append(' ');
                continue;
            }

            final int indexOfEnryptedLetter = (letters.indexOf(text.charAt(i)) + letters.indexOf(gamma.charAt(i))) % letters.length();
            encryptedString.append(letters.charAt(indexOfEnryptedLetter));
        }

        return encryptedString.toString();
    }

    @Override
    public String decode(String text) {
        final String gamma = key.length() < text.length()
                ? multiplyGamma(text.length())
                : key;
        final String letters = language.letters();
        final StringBuilder encryptedString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                encryptedString.append(' ');
                continue;
            }

            final int indexOfEnryptedLetter =
                    (letters.indexOf(text.charAt(i)) - letters.indexOf(gamma.charAt(i)) + letters.length()) % letters.length();

            encryptedString.append(letters.charAt(indexOfEnryptedLetter));
        }

        return encryptedString.toString();
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

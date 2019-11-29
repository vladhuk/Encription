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
                ? multiplyGamma(text.length()).toLowerCase()
                : key.toLowerCase();
        final String letters = language.letters();
        final StringBuilder encodedString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (letters.indexOf(Character.toLowerCase(text.charAt(i))) == -1) {
                encodedString.append(text.charAt(i));
                continue;
            }

            final int indexOfEncodedLetter =
                    (letters.indexOf(Character.toLowerCase(text.charAt(i))) + letters.indexOf(gamma.charAt(i))) % letters.length();

            final char encodedLetter = Character.isUpperCase(text.charAt(i))
                    ? Character.toUpperCase(letters.charAt(indexOfEncodedLetter))
                    : letters.charAt(indexOfEncodedLetter);

            encodedString.append(encodedLetter);
        }

        return encodedString.toString();
    }

    @Override
    public String decode(String text) {
        final String gamma = key.length() < text.length()
                ? multiplyGamma(text.length()).toLowerCase()
                : key.toLowerCase();
        final String letters = language.letters();
        final StringBuilder decodedString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (letters.indexOf(Character.toLowerCase(text.charAt(i))) == -1) {
                decodedString.append(text.charAt(i));
                continue;
            }

            final int indexOfDecodedLetter =
                    (letters.indexOf(Character.toLowerCase(text.charAt(i))) - letters.indexOf(gamma.charAt(i)) + letters.length()) % letters.length();

            final char decodedLetter = Character.isUpperCase(text.charAt(i))
                    ? Character.toUpperCase(letters.charAt(indexOfDecodedLetter))
                    : letters.charAt(indexOfDecodedLetter);

            decodedString.append(decodedLetter);
        }

        return decodedString.toString();
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

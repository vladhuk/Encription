package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;

public class CaesarEncryptor implements Encryptor {

    private Language language;

    private int key;

    public CaesarEncryptor(Language language, int key) {
        this.language = language;
        this.key = key;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String encode(String text) {
        final StringBuilder result = new StringBuilder();

        text
                .chars()
                .map(letter -> encode((char) letter))
                .forEach(letter -> result.append((char) letter));

        return result.toString();
    }

    @Override
    public String decode(String text) {
        final StringBuilder result = new StringBuilder();

        text
                .chars()
                .map(letter -> decode((char) letter))
                .forEach(letter -> result.append((char) letter));

        return result.toString();
    }

    private char encode(char symbol) {
        final String alphabet = language.letters();
        final char lowerCaseSymbol = Character.toLowerCase(symbol);
        final int indexInAlphabet = alphabet.indexOf(lowerCaseSymbol);

        if (indexInAlphabet == -1) {
            return symbol;
        }

        final char encodedSymbol = alphabet.charAt((indexInAlphabet + key) % alphabet.length());

        return Character.isLowerCase(symbol)
                ? encodedSymbol
                : Character.toUpperCase(encodedSymbol);
    }

    private char decode(char symbol) {
        final String alphabet = language.letters();
        final char lowerCaseSymbol = Character.toLowerCase(symbol);
        final int indexInAlphabet = alphabet.indexOf(lowerCaseSymbol);

        if (indexInAlphabet == -1) {
            return symbol;
        }

        final char decodedSymbol = alphabet.charAt(
                (alphabet.indexOf(lowerCaseSymbol) + alphabet.length() - (key % alphabet.length())) % alphabet.length()
        );

        return Character.isLowerCase(symbol)
                ? decodedSymbol
                : Character.toUpperCase(decodedSymbol);
    }

}

package com.vladhuk.encription;

public abstract class AbstractEncryptor implements Encryptor {

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

}

package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BagEncyptor implements Encryptor {

    private static final Random random = new Random();

    private Language language;
    private String secretKey;

    public BagEncyptor(Language language, String secretKey) {
        this.language = language;
        this.secretKey = secretKey;
    }

    private String getOpenKey() {
        final List<Integer> A = Arrays.stream(secretKey.split(" "))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        final int m = A.stream().mapToInt(i -> i).sum() + 2;

        double u = 1 / (double) t;



        return null;
    }

    @Override
    public String encode(String text) {
        if (text.isEmpty()) {
            return getOpenKey();
        }

        return null;
    }

    @Override
    public String decode(String text) {
        if (text.isEmpty()) {
            return getOpenKey();
        }

        return null;
    }

    @Override
    public char encode(char symbol) {
        throw new UnsupportedOperationException();;
    }

    @Override
    public char decode(char symbol) {
        throw new UnsupportedOperationException();;
    }

}

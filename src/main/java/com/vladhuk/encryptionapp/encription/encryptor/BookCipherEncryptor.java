package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookCipherEncryptor implements Encryptor {

    private static final String nonFractionPattern = "[^(\\d/)]";
    private static final String nonLetterOrNonEnterPattern = "[^(\\p{L}|\n)]";

    private String poem;
    private int tableSize;

    public BookCipherEncryptor(String poem, int tableSize) {
        this.poem = poem;
        this.tableSize = tableSize;
    }

    private char[][] poemToTable() {
        final char[][] table = new char[tableSize][tableSize];
        String row = poem.replaceAll(nonLetterOrNonEnterPattern, "");

        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                table[i][j] = Character.toLowerCase(row.charAt(j));
            }
            row = row.substring(row.indexOf('\n') + 1);
        }

        return table;
    }

    @Override
    public String encode(String text) {
        final StringBuilder result = new StringBuilder();

        text
                .chars()
                .forEach(letter -> result.append(encodeToFraction((char) letter)).append(", "));

        result.delete(result.length() - 2, result.length());

        return result.toString();
    }

    public String encodeToFraction(char symbol) {
        final char[][] table = poemToTable();

        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                if (table[i][j] == Character.toLowerCase(symbol)) {
                    return (i + 1) + "/" + (j + 1);
                }
            }
        }

        throw new IllegalArgumentException("Symbol is not founded in the current poem");
    }

    @Override
    public String decode(String text) {
        final StringBuilder result = new StringBuilder();

        final List<String> fractions = Arrays.asList(text.split(nonFractionPattern))
                .stream()
                .filter(fraction -> !fraction.equals(""))
                .collect(Collectors.toList());

        fractions
                .stream()
                .map(this::decodeFromFraction)
                .forEach(result::append);

        return result.toString();
    }

    public char decodeFromFraction(String fraction) {
        final char[][] table = poemToTable();
        final List<Integer> numbersInFraction = Arrays.asList(fraction.split("/"))
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return table[numbersInFraction.get(0) - 1][numbersInFraction.get(1) - 1];
    }

    @Override
    public char encode(char symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char decode(char symbol) {
        throw new UnsupportedOperationException();
    }

    public String getPoem() {
        return poem;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }
}

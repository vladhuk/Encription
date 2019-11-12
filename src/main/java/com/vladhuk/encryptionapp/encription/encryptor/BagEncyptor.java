package com.vladhuk.encryptionapp.encription.encryptor;

import com.vladhuk.encryptionapp.encription.Encryptor;
import com.vladhuk.encryptionapp.util.Language;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BagEncyptor implements Encryptor {

    private static final Random random = new Random();

    private Language language;
    private List<Integer> secretKey;

    public BagEncyptor(Language language, String secretKey) {
        this.language = language;
        this.secretKey = getIntegersFromString(secretKey, " ");
    }

    private List<Integer> getOpenKey() {
        return secretKey.stream()
                .map(i -> (i * getR()) % getQ())
                .collect(Collectors.toList());
    }

    private int getW() {
        return secretKey.stream().mapToInt(i -> i).sum();
    }

    private int getQ() {
        return getPrimeGreaterThan(getW());
    }

    private int getPrimeGreaterThan(int i) {
        while (!BigInteger.valueOf(++i).isProbablePrime(1)) ;
        return i;
    }

    private int getR() {
        return getQ() / 2;
    }

    private int getInverseR() {
        return modInverse(getR(), getQ());
    }

    private int modInverse(int n, int m) {
        int inverse = 0;
        for (int i = 1; i < m; i++) {
            if ((n * i) % m == 1) {
                inverse = i;
                break;
            }
        }
        return inverse;
    }

    private List<Integer> getIntegersFromString(String string, String delimiter) {
        return Stream.of(string.split(delimiter))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private String getStringFromIntegers(List<Integer> integers, String delimiter) {
        return integers
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(delimiter));
    }

    private String getBinaryText(String text) {
        final String letters = language.letters();
        final String zeroString = new String(new char[secretKey.size()]).replaceAll("\0", "0");
        return Stream.of(text.toLowerCase().split(""))
                .map(symbol -> {
                    final int index = letters.indexOf(symbol) + 1;
                    final String binary = Integer.toBinaryString(index);
                    return zeroString.substring(0, zeroString.length() - binary.length()) + binary;
                })
                .collect(Collectors.joining());
    }

    @Override
    public String encode(String text) {
        if (text.isEmpty()) {
            return getStringFromIntegers(getOpenKey(), " ");
        }

        final String binaryText = getBinaryText(text);
//        final String fullBinaryText = appendZeros(binaryText, secretKey.size());
        final List<Integer> fullBinaryTextIntegers = getIntegersFromString(binaryText, "");

        return getStringFromIntegers(encode(fullBinaryTextIntegers), " ");
    }

    private String appendZeros(String string, int finalSize) {
        final StringBuilder fullBinaryTextBuilder = new StringBuilder(string);
        while (fullBinaryTextBuilder.length() % finalSize != 0) {
            fullBinaryTextBuilder.append("0");
        }
        return fullBinaryTextBuilder.toString();
    }

    private List<Integer> encode(List<Integer> binaryText) {
        final List<Integer> encodedTextIntegers = new LinkedList<>();
        int sum = 0;
        for (int i = 0; i < binaryText.size(); i++) {
            if (i != 0 && i % secretKey.size() == 0) {
                encodedTextIntegers.add(sum);
                sum = 0;
            }
            sum += binaryText.get(i) * secretKey.get(i % secretKey.size());
        }
        encodedTextIntegers.add(sum);
        return encodedTextIntegers;
    }

    @Override
    public String decode(String text) {
        if (text.isEmpty()) {
            return getStringFromIntegers(getOpenKey(), " ");
        }

        final List<Integer> encodedText = getIntegersFromString(text, " ");
        final String letters = language.letters();
        final StringBuilder decodedText = new StringBuilder();

        encodedText
                .stream()
                .map(i -> (i * getInverseR()) % getQ())
                .map(this::decodeToBinary)
                .map(str -> str.isEmpty() ? -1 : Integer.parseInt(str, 2))
                .map(i -> i == -1 ? ' ' : letters.charAt(i - 1))
                .forEach(c -> decodedText.append((char) c));

        return decodedText.toString();
    }

    private String decodeToBinary(int i) {
        if (i == 0) {
            return "";
        }

        final List<Integer> binary = new ArrayList<>(Collections.nCopies(secretKey.size(), 0));

        List<Integer> secretKeyCopy;

        int max;
        do {
            secretKeyCopy = new ArrayList<>(secretKey);
            int indexOfMax;
            do {
                max = getMax(secretKeyCopy);
                indexOfMax = secretKeyCopy.indexOf(max);
                secretKeyCopy.set(indexOfMax, -1);
            } while (max > i);
            binary.set(indexOfMax, 1);
            i -= max;
        } while (i > 0 && max != -1);

        return getStringFromIntegers(binary, "");
    }

    private int getMax(List<Integer> numbers) {
        return numbers.stream().mapToInt(n -> n).max().getAsInt();
    }

    @Override
    public char encode(char symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char decode(char symbol) {
        throw new UnsupportedOperationException();
    }

}

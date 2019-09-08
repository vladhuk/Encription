package com.vladhuk.encription;

public interface Encryptor {

    String encode(String text);

    String decode(String text);

    char encode(char symbol);

    char decode(char symbol);

}

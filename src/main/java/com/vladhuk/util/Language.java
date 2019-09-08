package com.vladhuk.util;

import java.nio.charset.Charset;

public enum Language {
    RUSSIAN {
        public String letters() {
            return new String("абвгдеёжзийклмнопрстуфхцчшщъыьэюя".getBytes(), Charset.forName("UTF-8"));
        }
    },
    ENGLISH {
        public String letters() {
            return "abcdefghijklmnopqrstuvwxyz";
        }
    };

    public abstract String letters();
}

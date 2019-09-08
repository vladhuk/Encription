package com.vladhuk.util;

public enum Language {
    RUSSIAN {
        public String letters() {
            return "��������������������������������";
        }
    },
    ENGLISH {
        public String letters() {
            return "abcdefghijklmnopqrstuvwxyz";
        }
    };

    public abstract String letters();
}

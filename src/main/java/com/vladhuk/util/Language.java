package com.vladhuk.util;

public enum Language {
    RUSSIAN {
        public String letters() {
            return "אבגדהו¸זחטיךכלםמןנסעףפץצקרשתûü‎‏ÿ";
        }
    },
    ENGLISH {
        public String letters() {
            return "abcdefghijklmnopqrstuvwxyz";
        }
    };

    public abstract String letters();
}

package com.bank.util;

public class AccountNumberGenerator {
    public static String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int number = (int) (Math.random() * 10);
            sb.append(number);
        }
        return sb.toString();
    }
}

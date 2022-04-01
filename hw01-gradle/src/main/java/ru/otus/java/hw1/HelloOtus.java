package ru.otus.java.hw1;

import com.google.common.base.Strings;

public class HelloOtus {

    public static void main(String... args) {
        System.out.println(Strings.isNullOrEmpty(""));
        System.out.println(Strings.repeat("hello", 5));
    }

}

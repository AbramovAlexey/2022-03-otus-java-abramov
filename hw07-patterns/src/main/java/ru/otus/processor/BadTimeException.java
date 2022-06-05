package ru.otus.processor;

public class BadTimeException extends RuntimeException{

    public BadTimeException() {
        super("Bad time for process");
    }
}

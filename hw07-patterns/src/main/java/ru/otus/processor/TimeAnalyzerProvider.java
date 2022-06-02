package ru.otus.processor;

@FunctionalInterface
public interface TimeAnalyzerProvider {

    boolean isEvenSecondNow();

}

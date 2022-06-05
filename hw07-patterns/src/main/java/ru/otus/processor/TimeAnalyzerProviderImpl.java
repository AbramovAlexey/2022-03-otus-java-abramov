package ru.otus.processor;

import java.time.LocalDateTime;

public class TimeAnalyzerProviderImpl implements TimeAnalyzerProvider{

    @Override
    public boolean isEvenSecondNow() {
        return LocalDateTime.now().getSecond() % 2 == 0;
    }

}

package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorSwapFields;
import ru.otus.processor.ProcessorThrowEvenSecond;
import ru.otus.processor.TimeAnalyzerProvider;
import ru.otus.processor.TimeAnalyzerProviderImpl;

import java.util.List;

public class HomeWork {

    public static void main(String[] args) {
        TimeAnalyzerProvider timeAnalyzerProvider = new TimeAnalyzerProviderImpl();
        var processors = List.of(new ProcessorSwapFields(), new ProcessorThrowEvenSecond(timeAnalyzerProvider));
        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println(ex.getMessage()));
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);
        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);
        complexProcessor.removeListener(historyListener);
    }
}

package ru.otus.processor;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.otus.model.Message;

@Setter
@AllArgsConstructor
public class ProcessorThrowEvenSecond implements Processor{

    private TimeAnalyzerProvider timeAnalyzerProvider;

    @Override
    public Message process(Message message) {
        if (timeAnalyzerProvider.isEvenSecondNow()) {
            throw new BadTimeException();
        }
        return message;
    }

}

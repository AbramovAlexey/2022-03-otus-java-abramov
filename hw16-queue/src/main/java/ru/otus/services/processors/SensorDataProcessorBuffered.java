package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> priorityBlockingQueue;
    private final ReentrantLock reentrantLock = new ReentrantLock(true);

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.priorityBlockingQueue = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        priorityBlockingQueue.put(data);
        if (priorityBlockingQueue.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            reentrantLock.lock();
            if (priorityBlockingQueue.size() > 0) {
                var bufferedData = new ArrayList<SensorData>();
                priorityBlockingQueue.drainTo(bufferedData, bufferSize);
                writer.writeBufferedData(bufferedData);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

}

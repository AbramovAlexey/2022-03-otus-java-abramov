package ru.otus;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Semaphore;

public class SequenceGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SequenceGenerator.class);

    private final List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2);
    private final Semaphore semaphore1 = new Semaphore(1);
    private final Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
       var sequenceGenerator = new SequenceGenerator();
       sequenceGenerator.go();
    }

    private void go() throws InterruptedException {
        var thread1 = new Thread(() -> printValues(semaphore1, semaphore2));
        var thread2 = new Thread(() -> printValues(semaphore2, semaphore1));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private void printValues(Semaphore semaphore1, Semaphore semaphore2) {
        var iterator = Iterables.cycle(numbers).iterator();
        while (iterator.hasNext() && !Thread.currentThread().isInterrupted()) {
            try {
                semaphore1.acquire();
                logger.info("{}", iterator.next());
                semaphore2.release();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

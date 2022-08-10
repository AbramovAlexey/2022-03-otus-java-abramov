package ru.otus;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequenceGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SequenceGenerator.class);

    private List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2);
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
       var sequenceGenerator = new SequenceGenerator();
       sequenceGenerator.go();
    }

    private void go() {
        var thread1 = new Thread(() -> printValues(lock1, lock2));
        var thread2 = new Thread(() -> printValues(lock2, lock1));
        thread1.start();
        thread2.start();
    }

    private void printValues(Lock lock1, Lock lock2) {
        var iterator = Iterables.cycle(numbers).iterator();
        try {
            while (iterator.hasNext() && !Thread.currentThread().isInterrupted()) {
                lock1.lock();
                logger.info("{}", iterator.next());
                lock1.unlock();
                Thread.sleep(1_000);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}

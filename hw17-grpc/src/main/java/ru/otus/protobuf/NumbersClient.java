package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.GeneratedResponse;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestRange;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class NumbersClient {

    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int FIRST_REQUEST = 0;
    private static final int LAST_REQUEST = 30;
    private static final int FIRST_CYCLE = 0;
    private static final int LAST_CYCLE = 50;
    private static final int DELAY_SEC = 1;

    private final ReentrantLock reentrantLock = new ReentrantLock(true);
    private Integer lastServerValue;

    public static void main(String[] args) throws InterruptedException {
       new NumbersClient().start();
    }

    private void start() throws InterruptedException{
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                                           .usePlaintext()
                                           .build();
        lastServerValue = null;
        var newStub = RemoteServiceGrpc.newStub(channel);
        var latch = new CountDownLatch(1);
        log.info("numbers Client is starting...");
        newStub.requestSequence(prepareRequest(), new StreamObserver<>() {
            @Override
            public void onNext(GeneratedResponse value) {
               setLastServerValueSync(value.getNewValue());
               log.info("new value {}", value.getNewValue());
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error during request processing", t);
            }

            @Override
            public void onCompleted() {
                log.info("request completed");
                latch.countDown();
            }
        });
        var currentValue = 0;
        for (int i = FIRST_CYCLE; i < LAST_CYCLE; i++) {
            currentValue += 1 + getLastValueAndClearSync();
            log.info("currentValue {}", currentValue);
            TimeUnit.SECONDS.sleep(DELAY_SEC);
        }
        latch.await();
        channel.shutdown();
    }

    private RequestRange prepareRequest() {
        return RequestRange.newBuilder()
                .setFirstValue(FIRST_REQUEST)
                .setLastValue(LAST_REQUEST)
                .build();
    }

    private void setLastServerValueSync(Integer value) {
        reentrantLock.lock();
        lastServerValue = value;
        reentrantLock.unlock();
    }

    private Integer getLastValueAndClearSync() {
        int value = 0;
        reentrantLock.lock();
        if (Objects.nonNull(lastServerValue)) {
            value = lastServerValue;
            lastServerValue = null;
        }
        reentrantLock.unlock();
        return value;
    }

}

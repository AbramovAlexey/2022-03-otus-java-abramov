package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.GeneratedResponse;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestRange;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int FIRST = 0;
    private static final int LAST = 30;

    public static void main(String[] args) throws InterruptedException {
       new GRPCClient().start();
    }

    private void start() throws InterruptedException{
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                                           .usePlaintext()
                                           .build();
        var latch = new CountDownLatch(1);
        var newStub = RemoteServiceGrpc.newStub(channel);
        newStub.requestSequence(prepareRequest(), new StreamObserver<>() {
            @Override
            public void onNext(GeneratedResponse value) {
               log.info("new value {}", value.getCurrentValue());
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error during request processing", t);
            }

            @Override
            public void onCompleted() {
                log.info("Done");
                latch.countDown();
            }
        });
        latch.await();
        channel.shutdown();
    }

    private RequestRange prepareRequest() {
        return RequestRange.newBuilder()
                .setFirstValue(FIRST)
                .setLastValue(LAST)
                .build();
    }


}

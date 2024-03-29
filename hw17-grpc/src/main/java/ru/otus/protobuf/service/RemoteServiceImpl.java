package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.GeneratedResponse;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestRange;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(RemoteServiceImpl.class);
    private static final int DELAY_SEC = 2;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public void requestSequence(RequestRange request, StreamObserver<GeneratedResponse> responseObserver) {
        log.info("New request received");
        var sequence = sequenceGenerator.generate(request.getFirstValue(), request.getLastValue());
        sequence.forEach(number -> {
            responseObserver.onNext(number2GeneratedResponse(number));
            try {
                TimeUnit.SECONDS.sleep(DELAY_SEC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        responseObserver.onCompleted();
        log.info("Request processing done");
    }

    private GeneratedResponse number2GeneratedResponse(Integer number) {
        return GeneratedResponse.newBuilder()
                .setNewValue(number)
                .build();
    }

}

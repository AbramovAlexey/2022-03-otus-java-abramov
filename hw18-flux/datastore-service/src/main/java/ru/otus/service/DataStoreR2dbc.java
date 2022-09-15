package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.domain.Message;
import ru.otus.repository.MessageRepository;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class DataStoreR2dbc implements DataStore {
    private static final Logger log = LoggerFactory.getLogger(DataStoreR2dbc.class);
    private static String ROOM_SEE_ALL = "1408";
    private final MessageRepository messageRepository;
    private final Scheduler workerPool;

    public DataStoreR2dbc(Scheduler workerPool, MessageRepository messageRepository) {
        this.workerPool = workerPool;
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<Message> saveMessage(Message message, String roomId) {
        log.info("saveMessage:{}", message);
        if (ROOM_SEE_ALL.equals(roomId)) {
            log.info("message ignored for this room");
            return Mono.empty();
        } else {
            return messageRepository.save(message);
        }
    }

    @Override
    public Flux<Message> loadMessages(String roomId) {
        log.info("loadMessages roomId:{}", roomId);
        if (ROOM_SEE_ALL.equals(roomId)) {
            return messageRepository.findAll();
        } else {
            return messageRepository.findByRoomId(roomId)
                    .delayElements(Duration.of(3, SECONDS), workerPool);
        }
    }
}

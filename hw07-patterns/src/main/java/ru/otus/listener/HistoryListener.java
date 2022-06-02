package ru.otus.listener;

import lombok.SneakyThrows;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> historyMap = new HashMap<>();

    @SneakyThrows
    @Override
    public void onUpdated(Message msg) {
        historyMap.put(msg.getId(), msg.clone());
        System.out.printf("Message with id %d saved%n", msg.getId());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        Message message = historyMap.get(id);
        return Optional.ofNullable(message);
    }
}

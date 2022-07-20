package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.model.Client;
import ru.otus.service.DBServiceClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final DBServiceClient dbServiceClient;

    @GetMapping("/api/v1/client")
    public List<Client> getClients() {
        return dbServiceClient.findAll();
    }

}

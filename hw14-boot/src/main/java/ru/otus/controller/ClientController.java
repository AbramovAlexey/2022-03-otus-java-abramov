package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.ClientDto;
import ru.otus.service.DBServiceClient;
import ru.otus.service.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final DBServiceClient dbServiceClient;
    private final DtoConverter dtoConverter;

    @GetMapping("/api/v1/client")
    public List<ClientDto> getClients() {
        return dbServiceClient.findAll().stream()
                              .map(dtoConverter::clientToDto)
                              .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/client")
    public ClientDto addClient(@RequestBody ClientDto clientDto) {
        var client = dtoConverter.dtoToClient(clientDto);
        return dtoConverter.clientToDto(dbServiceClient.save(client));
    }

}

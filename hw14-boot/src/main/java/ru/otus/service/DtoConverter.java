package ru.otus.service;

import ru.otus.dto.ClientDto;
import ru.otus.model.Client;

public interface DtoConverter {

    Client dtoToClient(ClientDto clientDto);
    ClientDto clientToDto(Client client);

}

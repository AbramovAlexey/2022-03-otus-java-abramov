package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DtoConverterImpl implements DtoConverter{

    @Override
    public Client dtoToClient(ClientDto clientDto) {
        var phonesStr = clientDto.getPhones();
        List<Phone> phones = null;
        if (Objects.nonNull(phonesStr) && !phonesStr.isBlank()) {
            phones = Arrays.stream(phonesStr.split(","))
                           .map(Phone::new)
                           .collect(Collectors.toList());
        }
        return new Client(clientDto.getId(),
                          clientDto.getName(),
                          new Address(null, clientDto.getAddress()),
                          phones);
    }

    @Override
    public ClientDto clientToDto(Client client) {
        var clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        if (Objects.nonNull(client.getAddress())) {
            clientDto.setAddress(client.getAddress().getStreet());
        }
        if (Objects.nonNull(client.getPhones())) {
            clientDto.setPhones(client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining(",")));
        }
        return clientDto;
    }

}

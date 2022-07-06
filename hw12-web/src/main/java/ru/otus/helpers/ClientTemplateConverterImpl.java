package ru.otus.helpers;

import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientTemplateConverterImpl implements ClientTemplateConverter{

    @Override
    public Map convert(Client client) {
        if (Objects.isNull(client)) {
            return null;
        } else {
            Map result = new HashMap();
            return Map.of("id", client.getId(),
                          "name", client.getName(),
                          "address", Objects.nonNull(client.getAddress()) ? client.getAddress().getStreet() : "",
                          "phones", Objects.isNull(client.getPhones()) ? "" : client.getPhones()
                                                                                        .stream()
                                                                                        .map(Phone::getNumber)
                                                                                        .collect(Collectors.joining(",")));
        }
    }

}

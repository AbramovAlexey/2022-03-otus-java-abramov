package ru.otus.helpers;

import jakarta.servlet.http.HttpServletRequest;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientConverterImpl implements ClientConverter {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONE = "phone";

    @Override
    public Map convertToTemplate(Client client) {
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

    @Override
    public Client convertToClient(HttpServletRequest request) {
        var newClient = new Client();
        newClient.setName(request.getParameter(PARAM_NAME));
        var address = request.getParameter(PARAM_ADDRESS);
        newClient.setAddress(Objects.nonNull(address) ? new Address(null, address) : null);
        var phones = request.getParameter(PARAM_PHONE);
        if (Objects.nonNull(phones)) {
            newClient.setPhones(Arrays.stream(phones.split(","))
                     .map(phone -> new Phone(null, phone, newClient))
                     .collect(Collectors.toList()));
        }
        return newClient;
    }

}

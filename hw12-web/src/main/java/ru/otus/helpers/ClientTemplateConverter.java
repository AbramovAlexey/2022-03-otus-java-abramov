package ru.otus.helpers;

import ru.otus.model.Client;

import java.util.Map;

public interface ClientTemplateConverter {

    Map convert(Client client);

}

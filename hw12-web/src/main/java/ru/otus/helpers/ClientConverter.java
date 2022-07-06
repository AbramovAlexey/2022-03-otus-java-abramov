package ru.otus.helpers;

import jakarta.servlet.http.HttpServletRequest;
import ru.otus.model.Client;

import java.util.Map;

public interface ClientConverter {

    Map convertToTemplate(Client client);
    Client convertToClient(HttpServletRequest request);

}

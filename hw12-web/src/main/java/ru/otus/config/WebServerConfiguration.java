package ru.otus.config;

import ru.otus.server.ClientsWebServer;

import java.io.IOException;

public interface WebServerConfiguration {

    ClientsWebServer configure() throws IOException;

}

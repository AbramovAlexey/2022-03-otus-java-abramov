package ru.otus;

import ru.otus.config.HibernateConfigurationImpl;
import ru.otus.config.WebServerConfigurationImpl;

public class WebServerRunner {

    public static void main(String[] args) throws Exception {
        var hibernateConfiguration = new HibernateConfigurationImpl();
        var dbServiceClient = hibernateConfiguration.configure();
        var webServerConfiguration = new WebServerConfigurationImpl(dbServiceClient);
        var webServer = webServerConfiguration.configure();
        webServer.start();
        webServer.join();
    }

}

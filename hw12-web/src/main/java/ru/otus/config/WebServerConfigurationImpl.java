package ru.otus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Server;
import ru.otus.service.DBServiceClient;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerWithBasicSecurity;
import ru.otus.services.InMemoryLoginServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

import java.io.IOException;

@RequiredArgsConstructor
public class WebServerConfigurationImpl implements WebServerConfiguration{

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private final DBServiceClient dbServiceClient;

    @Override
    public ClientsWebServer configure() throws IOException {
        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        LoginService loginService = new InMemoryLoginServiceImpl(userDao);
        return new ClientsWebServerWithBasicSecurity(loginService, gson, templateProcessor, new Server(WEB_SERVER_PORT), dbServiceClient);
    }

}

package ru.otus.config;

import ru.otus.service.DBServiceClient;

public interface HibernateConfiguration {

    DBServiceClient configure();

}

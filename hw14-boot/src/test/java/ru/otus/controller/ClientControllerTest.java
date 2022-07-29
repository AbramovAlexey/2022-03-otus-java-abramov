package ru.otus.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.service.DBServiceClient;
import ru.otus.service.DtoConverter;
import ru.otus.service.DtoConverterImpl;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер клиентов ")
@WebMvcTest(ClientController.class)
@Import(value = {DtoConverterImpl.class})
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DBServiceClient dbServiceClient;

    @Autowired
    private DtoConverter dtoConverter;

    @DisplayName("должен возвращать корректный список клиентов")
    @Test
    void shouldReturnExpectedClients() throws Exception {
        Client expectedClient = new Client(1L, "Vasya", new Address(1L, "street1"),
                                           Set.of(new Phone(1L,"12345678")));
        given(dbServiceClient.findAll()).willReturn(List.of(expectedClient));
        mvc.perform(get("/clients"))
           .andExpect(status().isOk())
           .andExpect(view().name("clients"))
           .andExpect(content().string(containsString("Vasya")));;
    }

}
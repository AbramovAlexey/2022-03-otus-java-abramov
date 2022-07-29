package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.service.DBServiceClient;
import ru.otus.service.DtoConverter;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final DBServiceClient dbServiceClient;
    private final DtoConverter dtoConverter;

    @GetMapping("/clients")
    public String getClients(Model model) {
        model.addAttribute("clients", dbServiceClient.findAll()
                                                     .stream()
                                                     .map(dtoConverter::clientToDto)
                                                     .collect(Collectors.toList()));
        return "clients";
    }

    @PostMapping("/client")
    public RedirectView addClient(@ModelAttribute ClientDto clientDto) {
        var client = dtoConverter.dtoToClient(clientDto);
        dbServiceClient.save(client);
        return new RedirectView("/clients", true);
    }

}

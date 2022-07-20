package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Table(name = "client")
public class Client implements Cloneable {

    @Id
    private Long id;

    private String name;

    @MappedCollection(idColumn = "address_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private List<Phone> phones;

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        /*if (Objects.nonNull(phones)) {
            phones.forEach(phone -> phone.setClient(this));
        }*/
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name,
                          Objects.nonNull(this.address) ? this.address.clone() : null,
                          Objects.nonNull(this.phones) ? phones.stream()
                                                               .map(Phone::clone)
                                                               .collect(Collectors.toList()) : null);
    }

}

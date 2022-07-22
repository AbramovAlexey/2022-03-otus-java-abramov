package ru.otus.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;


@Table(name = "client")
@Getter
@ToString
public class Client implements Cloneable {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "number")
    private final List<Phone> phones;

    @PersistenceCreator
    public Client(Long id, String name,Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        /*if (Objects.nonNull(phones)) {
            phones.forEach(phone -> phone.setClient(this));
        }*/
    }

   /* @Override
    public Client clone() {
        return new Client(this.id, this.name,
                          Objects.nonNull(this.address) ? this.address.clone() : null,
                          Objects.nonNull(this.phones) ? phones.stream()
                                                               .map(Phone::clone)
                                                               .collect(Collectors.toList()) : null);
    }*/

}

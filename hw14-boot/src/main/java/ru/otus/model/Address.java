package ru.otus.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "address")
@Getter
@ToString
public class Address implements Cloneable{

    @Id
    private Long clientId;

    private String street;

    @PersistenceCreator
    public Address(Long clientId, String street) {
        this.clientId = clientId;
        this.street = street;
    }

    @Override
    public Address clone() {
        return new Address(this.clientId, this.street);
    }
}

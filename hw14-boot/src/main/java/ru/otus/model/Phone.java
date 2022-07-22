package ru.otus.model;


import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone")
@Getter
@ToString
public class Phone implements Cloneable{

    @Id
    private Long clientId;

    private String number;

    public Phone(String number) {
        this(null, number);
    }

    @PersistenceCreator
    public Phone(Long clientId, String number) {
        this.clientId = clientId;
        this.number = number;
    }

    @Override
    public Phone clone() {
        return new Phone(this.clientId, this.number);
    }

}

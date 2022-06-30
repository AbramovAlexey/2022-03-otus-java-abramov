package ru.otus.crm.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        if (Objects.nonNull(phones)) {
            phones.forEach(phone -> phone.setClient(this));
        }
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

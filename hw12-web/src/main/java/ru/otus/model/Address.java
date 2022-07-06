package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "address")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Override
    public Address clone() {
        return new Address(this.id, this.street);
    }
}

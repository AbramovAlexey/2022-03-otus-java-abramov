package ru.otus.crm.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "phone")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Phone implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public Phone clone() {
        return new Phone(this.id, this.number);
    }

}

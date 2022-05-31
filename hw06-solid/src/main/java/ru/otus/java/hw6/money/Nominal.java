package ru.otus.java.hw6.money;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nominal {

    N100(100),
    N200(200),
    N500(500),
    N1000(1000),
    N2000(2000),
    N5000(5000);

    private int value;

}

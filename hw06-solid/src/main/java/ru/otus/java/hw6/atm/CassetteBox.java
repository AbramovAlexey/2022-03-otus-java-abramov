package ru.otus.java.hw6.atm;

import ru.otus.java.hw6.money.Banknote;

import java.util.List;

public interface CassetteBox {

    int getTotalAmount();
    boolean insertBanknotes(List<Banknote> banknoteList);
    List<Banknote> extractBanknotes(int sum);
    void addCassette(Cassette cassette);

}

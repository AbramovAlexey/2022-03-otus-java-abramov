package ru.otus.java.hw6.atm;

import ru.otus.java.hw6.money.Banknote;

import java.util.List;

public interface Atm {

    boolean deposit(List<Banknote> banknoteList);
    List<Banknote> withdraw(int amount);
    int getBalance();

}

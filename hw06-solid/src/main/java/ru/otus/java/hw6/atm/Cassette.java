package ru.otus.java.hw6.atm;

import ru.otus.java.hw6.money.Banknote;
import ru.otus.java.hw6.money.Nominal;

public interface Cassette{

    boolean insertBanknote(Banknote banknote);
    Banknote extractBanknote();
    int getCurrentCount();
    Nominal getNominal();

}

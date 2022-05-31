package ru.otus.java.hw6.atm;

import lombok.RequiredArgsConstructor;
import ru.otus.java.hw6.money.Banknote;
import ru.otus.java.hw6.money.Nominal;

import java.util.ArrayDeque;
import java.util.Deque;

@RequiredArgsConstructor
public class CassetteImpl implements Cassette{

    private final Deque<Banknote> banknotes = new ArrayDeque<>();
    private final Nominal nominal;
    private final int capacity;

    @Override
    public boolean insertBanknote(Banknote banknote) {
        if (banknote.getNominal().equals(nominal) && banknotes.size() <= capacity) {
            banknotes.addLast(banknote);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Banknote extractBanknote() {
        return banknotes.pollLast();
    }

    @Override
    public int getCurrentCount() {
        return banknotes.size();
    }

    @Override
    public Nominal getNominal() {
        return nominal;
    }

}

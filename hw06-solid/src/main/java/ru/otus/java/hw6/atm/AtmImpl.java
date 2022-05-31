package ru.otus.java.hw6.atm;

import lombok.RequiredArgsConstructor;
import ru.otus.java.hw6.money.Banknote;

import java.util.List;

@RequiredArgsConstructor
public class AtmImpl implements Atm {

    private final CassetteBox cassetteBox;

    @Override
    public boolean deposit(List<Banknote> banknoteList) {
        return cassetteBox.insertBanknotes(banknoteList);
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        return cassetteBox.extractBanknotes(amount);
    }

    @Override
    public int getBalance() {
        return cassetteBox.getTotalAmount();
    }

}

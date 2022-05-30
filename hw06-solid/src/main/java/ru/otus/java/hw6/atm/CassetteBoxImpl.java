package ru.otus.java.hw6.atm;

import ru.otus.java.hw6.money.Banknote;
import ru.otus.java.hw6.money.Nominal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CassetteBoxImpl implements CassetteBox{

    Map<Nominal, Cassette> store = new HashMap<>();

    @Override
    public int getTotalAmount() {
        return store.values()
                    .stream()
                    .map(cassette -> cassette.getCurrentCount() * cassette.getNominal().getValue())
                    .reduce(0, (s1,s2) -> s1 + s2);
    }

    @Override
    public boolean insertBanknotes(List<Banknote> banknoteList) {
        for (Banknote banknote : banknoteList) {
            Cassette cassette = store.get(banknote.getNominal());
            if (Objects.isNull(cassette) || !cassette.insertBanknote(banknote)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Banknote> extractBanknotes(int sum) {
        if (getTotalAmount() < sum) {
            return null;
        }
        Map<Nominal, Integer> pack = new HashMap<>();
        var cassettes = store.entrySet()
                             .stream()
                             .sorted((e1,e2) -> e2.getKey().getValue() - e1.getKey().getValue())
                             .collect(Collectors.toList());
        for (var cassette : cassettes) {
            int nominalValue = cassette.getKey().getValue();
            if (sum >= nominalValue) {
                int noteCount = Math.min(sum / nominalValue, cassette.getValue().getCurrentCount());
                pack.put(cassette.getKey(), noteCount);
                sum = sum - nominalValue * noteCount;
            }
        }
        if (sum > 0) {
            return null;
        } else {
            return pack.entrySet()
                       .stream()
                       .flatMap(entry -> IntStream.range(0, entry.getValue())
                                .mapToObj(e -> store.get(entry.getKey()).extractBanknote()))
                       .collect(Collectors.toList());
        }
    }

    @Override
    public void addCassette(Cassette cassette) {
        store.put(cassette.getNominal(), cassette);
    }

}

package ru.otus.java.hw6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.java.hw6.atm.*;
import ru.otus.java.hw6.money.Banknote;
import ru.otus.java.hw6.money.Nominal;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Банкомат должен")
class AtmTest {

    private Atm atm;

    @BeforeEach
    void init(){
        CassetteBox cassetteBox = new CassetteBoxImpl();
        atm = new AtmImpl(cassetteBox);
        cassetteBox.addCassette(new CassetteImpl(Nominal.N1000,2));
        cassetteBox.addCassette(new CassetteImpl(Nominal.N500,2));
    }

    @Test
    @DisplayName("корректно определять баланс")
    void testBalance() {
        var banknotes = List.of(new Banknote(Nominal.N1000), new Banknote(Nominal.N500));
        atm.deposit(banknotes);
        assertThat(atm.getBalance()).isEqualTo(1500);
    }

    @Test
    @DisplayName("отказывать в приеме купюры неподдерживаемого номинала")
    void testWrongNominal() {
        var wrongBanknote = new Banknote(Nominal.N100);
        assertThat(atm.deposit(List.of(wrongBanknote))).isFalse();
    }

    @Test
    @DisplayName("отказывать в выдаче средств, если их недостаточно")
    void testWithdrawNotMoney() {
        assertThat(atm.withdraw(10000)).isNull();
    }

    @Test
    @DisplayName("отказывать в выдаче средств, если нет подходящих купюр")
    void testWithdrawBanknoteMissing() {
        atm.deposit(List.of(new Banknote(Nominal.N1000)));
        assertThat(atm.withdraw(500)).isNull();
    }

    @Test
    @DisplayName("успешно выдавать сумму минимальным количеством купюр")
    void testWithdraw() {
        atm.deposit(List.of(new Banknote(Nominal.N1000), new Banknote(Nominal.N1000),
                            new Banknote(Nominal.N500), new Banknote(Nominal.N500), new Banknote(Nominal.N500)));
        var banknotes = atm.withdraw(2500);
        assertThat(banknotes).isNotNull();
        assertThat(banknotes.stream().map(banknote -> banknote.getNominal().getValue()).collect(Collectors.toList()))
                .asList()
                .containsExactlyInAnyOrder(1000,1000,500);
        assertThat(atm.getBalance()).isEqualTo(1000);
    }

}
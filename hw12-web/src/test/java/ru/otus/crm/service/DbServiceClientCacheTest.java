package ru.otus.crm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DbServiceClientCacheTest extends AbstractHibernateTest {

    @Test
    @DisplayName("при использовании кэша чтение должно происходить быстрее")
    void shouldBeFasterWithCache() {
        //given
        List<Long> ids = new ArrayList<>();
        int count = 100;
        for (int i = 0; i < count; i++) {
            var client = new Client(null, "Vasya" + i, new Address(null, "AnyStreet" + i),
                                    List.of(new Phone(null, "13-555-22" + i), new Phone(null, "14-666-333" + i)));
           ids.add(dbServiceClient.saveClient(client).getId());
        }

        //when
        //without cache
        var start = System.currentTimeMillis();
        for (var id : ids) {
            dbServiceClient.getClient(id);
        }
        var end = System.currentTimeMillis();
        var withoutCacheTime = end - start;
        System.out.println("time without cache " + withoutCacheTime);

        //now cached
        start = System.currentTimeMillis();
        for (var id : ids) {
            dbServiceClient.getClient(id);
        }
        end = System.currentTimeMillis();
        var withCacheTime = end - start;
        System.out.println("time with cache " + withCacheTime);

        //then
        assertThat(withoutCacheTime).isGreaterThan(withCacheTime);
    }

}

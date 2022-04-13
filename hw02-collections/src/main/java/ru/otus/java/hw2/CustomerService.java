package ru.otus.java.hw2;


import java.util.*;

public class CustomerService {

    private final NavigableMap customersMap = new TreeMap<Customer, String>((c1, c2) -> (int)(c1.getScores() - c2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        return copy(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copy(customersMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> entry) {
        return Objects.nonNull(entry) ? new AbstractMap.SimpleEntry<>(new Customer(entry.getKey()), entry.getValue()) : null;
    }

}

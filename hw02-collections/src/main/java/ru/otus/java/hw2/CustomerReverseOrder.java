package ru.otus.java.hw2;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private Deque<Customer> customerDeque = new ArrayDeque<>();

    public void add(Customer customer) {
        customerDeque.add(customer);
    }

    public Customer take() {
        return customerDeque.pollLast();
    }

}

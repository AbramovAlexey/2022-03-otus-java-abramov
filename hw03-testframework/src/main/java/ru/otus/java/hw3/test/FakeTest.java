package ru.otus.java.hw3.test;

import ru.otus.java.hw3.annotation.After;
import ru.otus.java.hw3.annotation.Before;
import ru.otus.java.hw3.annotation.Test;

import java.util.Random;

public class FakeTest {

    @Before
    public void beforeEach() {
        Random random = new Random();
        if (random.nextBoolean()) {
            throw new RuntimeException("Before method failed");
        }
        System.out.println("before");
    }

    @After
    public void afterEach() {
        System.out.println("after");
    }

    @Test
    public void shouldRun() {
        System.out.println("test");
    }

    @Test
    public void shouldRunSecond() {
        System.out.println("test2");
    }

    @Test
    public void shouldRunThird() {
        System.out.println("test3");
    }

    @Test
    public void shouldFailed() {
        throw new RuntimeException("Test broken");
    }

}
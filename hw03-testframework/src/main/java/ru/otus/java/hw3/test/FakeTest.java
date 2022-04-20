package ru.otus.java.hw3.test;

import ru.otus.java.hw3.annotation.After;
import ru.otus.java.hw3.annotation.Before;
import ru.otus.java.hw3.annotation.Test;

public class FakeTest {

    @Before
    public void beforeEach() {
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
    public void shouldFailed() {
        throw new RuntimeException("Test broken");
    }

}
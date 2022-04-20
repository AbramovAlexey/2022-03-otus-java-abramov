package ru.otus.java.hw3;


import ru.otus.java.hw3.runner.TestRunner;
import ru.otus.java.hw3.runner.TestRunnerImpl;
import ru.otus.java.hw3.test.FakeTest;

public class Main {

    public static void main(String[] args) {
        TestRunner testRunner = new TestRunnerImpl();
        testRunner.run(FakeTest.class);
    }

}

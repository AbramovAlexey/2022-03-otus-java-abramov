package ru.otus.java.hw3.runner;

import ru.otus.java.hw3.annotation.After;
import ru.otus.java.hw3.annotation.Before;
import ru.otus.java.hw3.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunnerImpl implements TestRunner {

    private static final String TEST_KEY = "test";
    private static final String BEFORE_KEY = "before";
    private static final String AFTER_KEY = "after";

    public void run(Class<?> testClazz) {
        if (Objects.isNull(testClazz)) {
            throw new IllegalArgumentException("Not null class expected");
        }
        System.out.println(String.format("Try run test class %s", testClazz.getName()));
        Long startMs = System.currentTimeMillis();
        Map<String, List<Method>> methodsMap = prepareMethods(testClazz);
        if (methodsMap.get(TEST_KEY).size() == 0) {
            System.out.println("No test methods found");
        } else {
            List<Method> tests = methodsMap.get(TEST_KEY);
            int testsCount = tests.size();
            int currentIndex = 1;
            int passed = 0;
            int failed = 0;
            Method afterMethod = extractFirstOrNull(methodsMap.get(AFTER_KEY));
            Method beforeMethod = extractFirstOrNull(methodsMap.get(BEFORE_KEY));
            System.out.println(String.format("Tests founded - %d", testsCount));
            for (Method test : tests) {
                System.out.println(String.format("Running test <%s>, %d of %d", test.getName(), currentIndex, testsCount));
                if (executeTest(testClazz, test, beforeMethod, afterMethod)) {
                    passed++;
                    System.out.println("---PASSED");
                } else {
                    failed++;
                    System.out.println("---FAILED");
                }
                currentIndex++;
            }
            Long endMs = System.currentTimeMillis();
            System.out.println(String.format("Passed - %d, failed - %d. Took %dms", passed, failed, endMs - startMs));
        }
    }

    private Method extractFirstOrNull(List<Method> methods) {
        return methods.isEmpty() ? null : methods.get(0);
    }

    private boolean executeTest(Class<?> testClazz, Method test, Method beforeMethod, Method afterMethod) {
        var instance = ReflectionHelper.instantiate(testClazz);
        if (Objects.nonNull(beforeMethod)) {
            try {
                beforeMethod.invoke(instance);
            } catch (Exception e) {
                printException(e);
                return false;
            }
        }
        boolean passed = true;
        try {
            test.invoke(instance);
        } catch (Exception e) {
            printException(e);
            passed = false;
        } finally {
            try {
                afterMethod.invoke(instance);
            } catch (Exception e) {
                printException(e);
                passed = false;
            }
        }
        return passed;
    }

    private void printException(Exception e) {
        if (e instanceof InvocationTargetException) {
            System.out.println(((InvocationTargetException) e).getTargetException().getMessage());
        } else {
            System.out.println(e.getMessage());
        }
    }

    private Map<String, List<Method>> prepareMethods(Class<?> testClazz) {
        Map<String, List<Method>> methodsMap = new HashMap<>();
        List<Method> testMethods = new ArrayList<>();
        methodsMap.put(TEST_KEY, testMethods);
        methodsMap.put(BEFORE_KEY, Collections.emptyList());
        methodsMap.put(AFTER_KEY, Collections.emptyList());
        Arrays.stream(testClazz.getMethods())
                .toList()
                .forEach(method -> {
                    if (method.isAnnotationPresent(Test.class)) {
                        testMethods.add(method);
                    } else if (method.isAnnotationPresent(Before.class)) {
                        methodsMap.put(BEFORE_KEY, Collections.singletonList(method));
                    } else if (method.isAnnotationPresent(After.class)) {
                        methodsMap.put(AFTER_KEY, Collections.singletonList(method));
                    }
                });
        return methodsMap;
    }

}

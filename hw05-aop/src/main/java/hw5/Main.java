package hw5;

import hw5.proxy.LoggingInvocationHandler;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        var handler = new LoggingInvocationHandler(new TestLoggingImpl());
        var testLogging = (TestLogging)Proxy.newProxyInstance(Main.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
        testLogging.calculation(1);
        testLogging.calculation(1,2);
        testLogging.calculation(1,2,3);
    }

}

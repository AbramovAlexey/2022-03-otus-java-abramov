package hw5.proxy;

import hw5.TestLogging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {

    private final TestLogging testLogging;

    public LoggingInvocationHandler(TestLogging testLogging) {
        this.testLogging = testLogging;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("test");
        return method.invoke(testLogging, args);
    }

}

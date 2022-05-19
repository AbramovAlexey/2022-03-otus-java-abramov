package hw5.proxy;

import hw5.TestLogging;
import hw5.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LoggingInvocationHandler implements InvocationHandler {

    private static final String MESSAGE = "executed method: %s, param: %s";
    private final TestLogging testLogging;

    public LoggingInvocationHandler(TestLogging testLogging) {
        this.testLogging = testLogging;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            System.out.println(String.format(MESSAGE, method.getName(), argsToStr(args)));
        }
        return method.invoke(testLogging, args);
    }

    private String argsToStr(Object[] args) {
       return Arrays.stream(args)
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
    }

}

package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        var beanMethods = Arrays.stream(configClass.getMethods())
                                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                                .collect(Collectors.toList());
        try {
            var configInstance = configClass.getDeclaredConstructor().newInstance();
            for (var method : beanMethods) {
                var beanName = method.getAnnotation(AppComponent.class).name();
                var bean = method.invoke(configInstance);
                appComponentsByName.put(beanName, bean);
                appComponents.add(bean);
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}

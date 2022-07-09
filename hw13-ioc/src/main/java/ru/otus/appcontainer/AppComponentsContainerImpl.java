package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> ...initialConfigClasses) {
        var configs = Arrays.stream(initialConfigClasses)
                                  .filter(cls -> cls.isAnnotationPresent(AppComponentsContainerConfig.class))
                                  .collect(Collectors.toSet());
        processConfigs(configs);
    }

    public AppComponentsContainerImpl(String packageName) {
        Reflections reflections = new Reflections(packageName);
        var configs = reflections.get(TypesAnnotated.with(AppComponentsContainerConfig.class).asClass());
        processConfigs(configs);
    }

    private void processConfigs(Set<Class<?>> configs) {
        configs.stream()
               .sorted(Comparator.comparing(cls -> cls.getAnnotation(AppComponentsContainerConfig.class).order()))
               .forEach(this::processConfig);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        var beanMethods = Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();
        try {
            var configInstance = configClass.getDeclaredConstructor().newInstance();
            for (var method : beanMethods) {
                var beanName = method.getAnnotation(AppComponent.class).name();
                var args = Arrays.stream(method.getParameterTypes())
                                          .map(this::getAppComponent).toArray();
                var bean = method.invoke(configInstance, args);
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
        var beans = appComponents.stream()
                                             .filter(bean -> componentClass.isAssignableFrom(bean.getClass()))
                                             .toList();
        if (beans.size() == 1) {
            return (C) beans.get(0);
        } else {
            throw new RuntimeException("Fail to determine bean for class - %s".formatted(componentClass.getName()));
        }
    }
    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

}

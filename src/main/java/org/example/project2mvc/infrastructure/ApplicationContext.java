package org.example.project2mvc.infrastructure;
import org.example.project2mvc.annotations.Autowired;
import org.example.project2mvc.annotations.Component;
import org.example.project2mvc.annotations.Controller;
import org.example.project2mvc.refction.PackageScanner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ApplicationContext {

    Map<Class,Object> beans = new HashMap<>();

    private final PackageScanner packageScanner = new PackageScanner();


    public ApplicationContext() throws IllegalAccessException {
        createBeans();
    }


    private void createBeans() throws IllegalAccessException {
        List<Class> componetsClasses = getComponentsClasses();

        for (Class componetsClass : componetsClasses) {
            createBean(componetsClass);
        }
        for (Class componetsClass : componetsClasses) {
            postProcessBean(beans.get(componetsClass));
        }
    }


    private void postProcessBean(Object bean) throws IllegalAccessException {

        List<Field> fields = Arrays.stream(beans.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            Class type = field.getType();
            Object value = getBeanByType(type);
            field.set(bean, value);
        }
    }


    public Object getBeanByType(Class type) {
        Object value = beans.keySet().stream()
                .filter(type::isAssignableFrom)
                .findFirst()
                .map(clc -> beans.get(clc))
                .orElse(null);
        return value;
    }


    private void createBean(Class componetsClass) {
        try {
            Object object = componetsClass.getConstructor().newInstance();
            beans.put(componetsClass,object);

        } catch (Exception e) {
            throw new RuntimeException("Error Create Bean"+componetsClass.getSimpleName(),e);
        }
    }


    private List<Class> getComponentsClasses() {

        String packege = "org.example";
        List<Class<?>> list1 = packageScanner.findClassesWithAnnotation(Controller.class,packege);
        List<Class<?>> list2 =packageScanner.findClassesWithAnnotation(Component.class,packege);

        return Stream
                .concat(list1.stream(),list2.stream())
                .collect(Collectors.toList());
    }



}

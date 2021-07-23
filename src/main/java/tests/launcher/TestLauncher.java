package tests.launcher;

import lombok.extern.slf4j.Slf4j;
import tests.annotations.After;
import tests.annotations.Before;
import tests.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;


@Slf4j
public class TestLauncher {

    private final HashMap<Class<? extends Annotation>, Integer> success = new HashMap<>();
    private final HashMap<Class<? extends Annotation>, Integer> total = new HashMap<>();

    private Class<?> clazz;
    private Method[] methods;

    public TestLauncher(String className) {

        success.putIfAbsent(Test.class, 0);
        total.putIfAbsent(Test.class, 0);

        try {
            clazz = Class.forName(className);
            methods = clazz.getMethods();
        } catch (ClassNotFoundException e) {
            log.error("class with name {} not found", className);
        }
    }

    private void launch(Class<? extends Annotation> annotationToLaunch, Object instanceOfObjectToTesting) throws IllegalAccessException, InstantiationException {
        for (java.lang.reflect.Method method : methods) {
            if (method.isAnnotationPresent(annotationToLaunch)) {
                if(annotationToLaunch.equals(Test.class))
                    total.put(annotationToLaunch, total.getOrDefault(annotationToLaunch, 0) + 1);
                try {
                    method.invoke(instanceOfObjectToTesting);
                    if(annotationToLaunch.equals(Test.class))
                        success.put(annotationToLaunch, success.getOrDefault(annotationToLaunch, 0) + 1);
                } catch (Exception ex) {
                    // log.error("method {} of class {} failed to run due to exception", method.getName(), clazz.getSimpleName());
                }
            }
        }
    }

    public void launch() throws IllegalAccessException, InstantiationException {
        if(clazz == null)
            return;

        Object newInstance = clazz.newInstance();

        launch(Before.class, newInstance);
        launch(Test.class, newInstance);
        launch(After.class, newInstance);
    }

    public void showStats() {
        log.info("=== Results ===");
        log.info("Tests run: {}, Failures: {}", total.get(Test.class), total.get(Test.class) - success.get(Test.class));
    }

}

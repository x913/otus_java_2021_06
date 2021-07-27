package testingframework.launcher;

import lombok.extern.slf4j.Slf4j;
import testingframework.annotations.After;
import testingframework.annotations.Before;
import testingframework.annotations.Test;
import testingframework.exceptions.TestException;
import testingframework.results.Results;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TestLauncher {

    private final Results results;
    private Class<?> clazz;
    private List<Method> annotatedMethods;

    public TestLauncher(String className, Results results) {
        this.results = results;
        try {
            clazz = Class.forName(className);
            annotatedMethods = getAnnotatedMethods();
        } catch (ClassNotFoundException e) {
            log.error("class name {} not found", className);
        }
    }

    public void launch() {
        if(clazz == null)
            return;

        try {
            var constructor = clazz.getDeclaredConstructor(null);
            var targetTestInstance = constructor.newInstance();
            for(var currentTestMethod: getAnnotatedMethod(Test.class)) {
                try {
                    launchTestMethod(currentTestMethod, targetTestInstance);
                    results.addPassed(currentTestMethod);
                } catch (TestException e) {
                    results.addFailed(currentTestMethod);
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void launchTestMethod(Method testMethod, Object targetInstance)   {
        var beforeMethods = getAnnotatedMethod(Before.class);
        var afterMethods = getAnnotatedMethod(After.class);

        for(var before: beforeMethods) {
            try {
                invoke(before, targetInstance);
            } catch (TestException e) {
                throw new TestException(String.format("invocation of %s failed", before.getName()), e);
            }
        }

        try {
            invoke(testMethod, targetInstance);
        } catch (TestException e) {
            throw new TestException(String.format("invocation of %s failed", testMethod.getName()), e);
        } finally {
            for(var after: afterMethods) {
                try {
                    invoke(after, targetInstance);
                } catch (TestException e) {
                    log.error(e.toString());
                }
            }
        }
    }

    private List<Method> getAnnotatedMethod(Class<? extends Annotation> annotation) {
        return annotatedMethods.stream().filter(method -> method.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    private List<Method> getAnnotatedMethods() {
        var methods = clazz.getMethods();
        var retVal = new ArrayList<Method>();
        var annotations = List.of(Test.class, After.class, Before.class);
        for (java.lang.reflect.Method method : methods) {
            for(var annotation : annotations) {
                if(method.isAnnotationPresent(annotation) && method.getParameterCount() == 0) {
                    retVal.add(method);
                }
            }
        }
        return retVal;
    }

    private void invoke(Method method, Object targetInstance)   {
        try {
            method.invoke(targetInstance);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new TestException("error invoking method " + method.getName(), e);
        }
    }

}


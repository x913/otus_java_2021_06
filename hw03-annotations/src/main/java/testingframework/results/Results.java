package testingframework.results;

import testingframework.results.enums.TestResult;

import java.lang.reflect.Method;
import java.util.HashMap;

public interface Results {
    void addPassed(Method method);
    void addFailed(Method method);
    HashMap<Method, TestResult> get();
    void show();
}

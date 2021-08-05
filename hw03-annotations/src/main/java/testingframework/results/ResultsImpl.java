package testingframework.results;

import lombok.extern.slf4j.Slf4j;
import testingframework.results.enums.TestResult;

import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
public class ResultsImpl implements Results {

    private HashMap<Method, TestResult> results = new HashMap<Method, TestResult>();

    @Override
    public void addPassed(Method method) {

        results.putIfAbsent(method, TestResult.PASSED);
    }

    public void addFailed(Method method) {
        results.putIfAbsent(method, TestResult.FAILED);
    }

    @Override
    public HashMap<Method, TestResult> get() {
        return results;
    }

    @Override
    public void show() {
        log.info("TOTAL: {}, FAILED: {}, PASSED: {}",
                results.values().stream().count(),
                results.values().stream().filter(x -> x == TestResult.FAILED).count(),
                results.values().stream().filter(x -> x == TestResult.PASSED).count()
        );

        results.keySet().forEach(x -> {
            log.info("{}, {}", x.getName(), results.get(x));
        });

    }
}

import testingframework.launcher.TestLauncher;
import testingframework.results.Results;
import testingframework.results.ResultsImpl;

public class Main {
    public static void main(String[] args) {
        Results results = new ResultsImpl();
        var launcher = new TestLauncher("ClassForTesting", results);
        launcher.launch();
        results.show();
    }
}

import tests.launcher.TestLauncher;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        TestLauncher testLauncher = new TestLauncher("ClassForTesting");
        testLauncher.launch();
        testLauncher.showStats();
    }
}

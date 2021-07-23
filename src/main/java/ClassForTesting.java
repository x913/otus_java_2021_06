import tests.annotations.After;
import tests.annotations.Before;
import tests.annotations.Test;

public class ClassForTesting {
    @Before
    public void runBefore()  {
    }

    @Test
    public void testMethod() {

    }

    @Test
    public void testMethod1() throws Exception {
        throw new Exception("hello");
    }

    @Test
    public void testMethod2() {

    }

    @Test
    public void testMethod3() {

    }

    @After
    public void runAfter() throws Exception {
        throw new Exception("hello");
    }

}

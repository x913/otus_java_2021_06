import lombok.extern.slf4j.Slf4j;
import testingframework.annotations.After;
import testingframework.annotations.Before;
import testingframework.annotations.Test;

@Slf4j
public class ClassForTesting {
    @Before
    public void runBefore() throws Exception {

    }

    @Test
    public void testMethod1() {

    }

    @Test
    public void testMethod2() throws Exception {
        throw new Exception("failed");
    }

    @Test
    public void testMethod3() throws Exception {
        throw new Exception("failed");
    }

    @Test
    public void testMethod4() {

    }

    @After
    public void runAfter2() throws Exception {

    }

}


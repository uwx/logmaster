
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest195 {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        LogmasterSettings.c().outputToFile(false).apply();
        System.setOut(new PrintStream(new NullOutputStream()));
        System.setErr(new PrintStream(new NullOutputStream()));
    }

    @After
    public void tearDown() throws Exception {
    }
    
    //! $CHALK_START
    
    
    @Test
    public final void test_black_int_Object() {
        HLogger.black(0, new Object());
        HLogger.black(0, new Dummy());
        HLogger.black(0, new Dummy2());
        HLogger.black(0, (Object) null);
        HLogger.black(1, new Object());
        HLogger.black(1, new Dummy());
        HLogger.black(1, new Dummy2());
        HLogger.black(1, (Object) null);
        HLogger.black(2, new Object());
        HLogger.black(2, new Dummy());
        HLogger.black(2, new Dummy2());
        HLogger.black(2, (Object) null);
        HLogger.black(3, new Object());
        HLogger.black(3, new Dummy());
        HLogger.black(3, new Dummy2());
        HLogger.black(3, (Object) null);
        HLogger.black(4, new Object());
        HLogger.black(4, new Dummy());
        HLogger.black(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
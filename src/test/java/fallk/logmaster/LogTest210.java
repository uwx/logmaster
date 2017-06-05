
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest210 {

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
    public final void test_red_int_Object() {
        HLogger.red(0, new Object());
        HLogger.red(0, new Dummy());
        HLogger.red(0, new Dummy2());
        HLogger.red(0, (Object) null);
        HLogger.red(1, new Object());
        HLogger.red(1, new Dummy());
        HLogger.red(1, new Dummy2());
        HLogger.red(1, (Object) null);
        HLogger.red(2, new Object());
        HLogger.red(2, new Dummy());
        HLogger.red(2, new Dummy2());
        HLogger.red(2, (Object) null);
        HLogger.red(3, new Object());
        HLogger.red(3, new Dummy());
        HLogger.red(3, new Dummy2());
        HLogger.red(3, (Object) null);
        HLogger.red(4, new Object());
        HLogger.red(4, new Dummy());
        HLogger.red(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
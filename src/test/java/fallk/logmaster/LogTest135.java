
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest135 {

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
    public final void test_blue_int_Object() {
        HLogger.blue(0, new Object());
        HLogger.blue(0, new Dummy());
        HLogger.blue(0, new Dummy2());
        HLogger.blue(0, (Object) null);
        HLogger.blue(1, new Object());
        HLogger.blue(1, new Dummy());
        HLogger.blue(1, new Dummy2());
        HLogger.blue(1, (Object) null);
        HLogger.blue(2, new Object());
        HLogger.blue(2, new Dummy());
        HLogger.blue(2, new Dummy2());
        HLogger.blue(2, (Object) null);
        HLogger.blue(3, new Object());
        HLogger.blue(3, new Dummy());
        HLogger.blue(3, new Dummy2());
        HLogger.blue(3, (Object) null);
        HLogger.blue(4, new Object());
        HLogger.blue(4, new Dummy());
        HLogger.blue(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
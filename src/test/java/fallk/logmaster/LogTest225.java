
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest225 {

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
    public final void test_brightRed_int_Object() {
        HLogger.brightRed(0, new Object());
        HLogger.brightRed(0, new Dummy());
        HLogger.brightRed(0, new Dummy2());
        HLogger.brightRed(0, (Object) null);
        HLogger.brightRed(1, new Object());
        HLogger.brightRed(1, new Dummy());
        HLogger.brightRed(1, new Dummy2());
        HLogger.brightRed(1, (Object) null);
        HLogger.brightRed(2, new Object());
        HLogger.brightRed(2, new Dummy());
        HLogger.brightRed(2, new Dummy2());
        HLogger.brightRed(2, (Object) null);
        HLogger.brightRed(3, new Object());
        HLogger.brightRed(3, new Dummy());
        HLogger.brightRed(3, new Dummy2());
        HLogger.brightRed(3, (Object) null);
        HLogger.brightRed(4, new Object());
        HLogger.brightRed(4, new Dummy());
        HLogger.brightRed(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
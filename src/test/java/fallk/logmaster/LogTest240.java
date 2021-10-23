
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest240 {

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
    public final void test_brightGreen_int_Object() {
        HLogger.brightGreen(0, new Object());
        HLogger.brightGreen(0, new Dummy());
        HLogger.brightGreen(0, new Dummy2());
        HLogger.brightGreen(0, (Object) null);
        HLogger.brightGreen(1, new Object());
        HLogger.brightGreen(1, new Dummy());
        HLogger.brightGreen(1, new Dummy2());
        HLogger.brightGreen(1, (Object) null);
        HLogger.brightGreen(2, new Object());
        HLogger.brightGreen(2, new Dummy());
        HLogger.brightGreen(2, new Dummy2());
        HLogger.brightGreen(2, (Object) null);
        HLogger.brightGreen(3, new Object());
        HLogger.brightGreen(3, new Dummy());
        HLogger.brightGreen(3, new Dummy2());
        HLogger.brightGreen(3, (Object) null);
        HLogger.brightGreen(4, new Object());
        HLogger.brightGreen(4, new Dummy());
        HLogger.brightGreen(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
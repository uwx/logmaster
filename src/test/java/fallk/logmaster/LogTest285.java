
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest285 {

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
    public final void test_brightMagenta_int_Object() {
        HLogger.brightMagenta(0, new Object());
        HLogger.brightMagenta(0, new Dummy());
        HLogger.brightMagenta(0, new Dummy2());
        HLogger.brightMagenta(0, (Object) null);
        HLogger.brightMagenta(1, new Object());
        HLogger.brightMagenta(1, new Dummy());
        HLogger.brightMagenta(1, new Dummy2());
        HLogger.brightMagenta(1, (Object) null);
        HLogger.brightMagenta(2, new Object());
        HLogger.brightMagenta(2, new Dummy());
        HLogger.brightMagenta(2, new Dummy2());
        HLogger.brightMagenta(2, (Object) null);
        HLogger.brightMagenta(3, new Object());
        HLogger.brightMagenta(3, new Dummy());
        HLogger.brightMagenta(3, new Dummy2());
        HLogger.brightMagenta(3, (Object) null);
        HLogger.brightMagenta(4, new Object());
        HLogger.brightMagenta(4, new Dummy());
        HLogger.brightMagenta(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
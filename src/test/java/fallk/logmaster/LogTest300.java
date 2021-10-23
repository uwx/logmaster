
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest300 {

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
    public final void test_brightCyan_int_Object() {
        HLogger.brightCyan(0, new Object());
        HLogger.brightCyan(0, new Dummy());
        HLogger.brightCyan(0, new Dummy2());
        HLogger.brightCyan(0, (Object) null);
        HLogger.brightCyan(1, new Object());
        HLogger.brightCyan(1, new Dummy());
        HLogger.brightCyan(1, new Dummy2());
        HLogger.brightCyan(1, (Object) null);
        HLogger.brightCyan(2, new Object());
        HLogger.brightCyan(2, new Dummy());
        HLogger.brightCyan(2, new Dummy2());
        HLogger.brightCyan(2, (Object) null);
        HLogger.brightCyan(3, new Object());
        HLogger.brightCyan(3, new Dummy());
        HLogger.brightCyan(3, new Dummy2());
        HLogger.brightCyan(3, (Object) null);
        HLogger.brightCyan(4, new Object());
        HLogger.brightCyan(4, new Dummy());
        HLogger.brightCyan(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
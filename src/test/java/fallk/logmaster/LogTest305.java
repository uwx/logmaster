
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest305 {

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
    public final void test_brightCyan_Object_long() {
        HLogger.brightCyan(new Object(), System.currentTimeMillis());
        HLogger.brightCyan(new Object(), System.nanoTime());
        HLogger.brightCyan(new Object(), Long.MAX_VALUE);
        HLogger.brightCyan(new Object(), Long.MIN_VALUE);
        HLogger.brightCyan(new Object(), 0);
        HLogger.brightCyan(new Dummy(), System.currentTimeMillis());
        HLogger.brightCyan(new Dummy(), System.nanoTime());
        HLogger.brightCyan(new Dummy(), Long.MAX_VALUE);
        HLogger.brightCyan(new Dummy(), Long.MIN_VALUE);
        HLogger.brightCyan(new Dummy(), 0);
        HLogger.brightCyan(new Dummy2(), System.currentTimeMillis());
        HLogger.brightCyan(new Dummy2(), System.nanoTime());
        HLogger.brightCyan(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightCyan(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightCyan(new Dummy2(), 0);
        HLogger.brightCyan((Object) null, System.currentTimeMillis());
        HLogger.brightCyan((Object) null, System.nanoTime());
        HLogger.brightCyan((Object) null, Long.MAX_VALUE);
        HLogger.brightCyan((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
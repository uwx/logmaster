
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest575 {

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
    public final void test_bgCyan_Object_long() {
        HLogger.bgCyan(new Object(), System.currentTimeMillis());
        HLogger.bgCyan(new Object(), System.nanoTime());
        HLogger.bgCyan(new Object(), Long.MAX_VALUE);
        HLogger.bgCyan(new Object(), Long.MIN_VALUE);
        HLogger.bgCyan(new Object(), 0);
        HLogger.bgCyan(new Dummy(), System.currentTimeMillis());
        HLogger.bgCyan(new Dummy(), System.nanoTime());
        HLogger.bgCyan(new Dummy(), Long.MAX_VALUE);
        HLogger.bgCyan(new Dummy(), Long.MIN_VALUE);
        HLogger.bgCyan(new Dummy(), 0);
        HLogger.bgCyan(new Dummy2(), System.currentTimeMillis());
        HLogger.bgCyan(new Dummy2(), System.nanoTime());
        HLogger.bgCyan(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgCyan(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgCyan(new Dummy2(), 0);
        HLogger.bgCyan((Object) null, System.currentTimeMillis());
        HLogger.bgCyan((Object) null, System.nanoTime());
        HLogger.bgCyan((Object) null, Long.MAX_VALUE);
        HLogger.bgCyan((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
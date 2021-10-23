
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest307 {

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
    public final void test_brightCyan_Exception_long() {
        HLogger.brightCyan(new Exception(), System.currentTimeMillis());
        HLogger.brightCyan(new Exception(), System.nanoTime());
        HLogger.brightCyan(new Exception(), Long.MAX_VALUE);
        HLogger.brightCyan(new Exception(), Long.MIN_VALUE);
        HLogger.brightCyan(new Exception(), 0);
        HLogger.brightCyan((Exception) null, System.currentTimeMillis());
        HLogger.brightCyan((Exception) null, System.nanoTime());
        HLogger.brightCyan((Exception) null, Long.MAX_VALUE);
        HLogger.brightCyan((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
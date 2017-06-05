
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest577 {

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
    public final void test_bgCyan_Exception_long() {
        HLogger.bgCyan(new Exception(), System.currentTimeMillis());
        HLogger.bgCyan(new Exception(), System.nanoTime());
        HLogger.bgCyan(new Exception(), Long.MAX_VALUE);
        HLogger.bgCyan(new Exception(), Long.MIN_VALUE);
        HLogger.bgCyan(new Exception(), 0);
        HLogger.bgCyan((Exception) null, System.currentTimeMillis());
        HLogger.bgCyan((Exception) null, System.nanoTime());
        HLogger.bgCyan((Exception) null, Long.MAX_VALUE);
        HLogger.bgCyan((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
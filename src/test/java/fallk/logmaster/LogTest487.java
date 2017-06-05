
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest487 {

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
    public final void test_bgBlack_Exception_long() {
        HLogger.bgBlack(new Exception(), System.currentTimeMillis());
        HLogger.bgBlack(new Exception(), System.nanoTime());
        HLogger.bgBlack(new Exception(), Long.MAX_VALUE);
        HLogger.bgBlack(new Exception(), Long.MIN_VALUE);
        HLogger.bgBlack(new Exception(), 0);
        HLogger.bgBlack((Exception) null, System.currentTimeMillis());
        HLogger.bgBlack((Exception) null, System.nanoTime());
        HLogger.bgBlack((Exception) null, Long.MAX_VALUE);
        HLogger.bgBlack((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
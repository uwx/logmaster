
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest13 {

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
    public final void test_log_Exception_long() {
        HLogger.log(new Exception(), System.currentTimeMillis());
        HLogger.log(new Exception(), System.nanoTime());
        HLogger.log(new Exception(), Long.MAX_VALUE);
        HLogger.log(new Exception(), Long.MIN_VALUE);
        HLogger.log(new Exception(), 0);
        HLogger.log((Exception) null, System.currentTimeMillis());
        HLogger.log((Exception) null, System.nanoTime());
        HLogger.log((Exception) null, Long.MAX_VALUE);
        HLogger.log((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
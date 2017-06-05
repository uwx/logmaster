
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest42 {

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
    public final void test_warn_Exception_long_boolean() {
        HLogger.warn(new Exception(), System.currentTimeMillis(), true);
        HLogger.warn(new Exception(), System.currentTimeMillis(), false);
        HLogger.warn(new Exception(), System.nanoTime(), true);
        HLogger.warn(new Exception(), System.nanoTime(), false);
        HLogger.warn(new Exception(), Long.MAX_VALUE, true);
        HLogger.warn(new Exception(), Long.MAX_VALUE, false);
        HLogger.warn(new Exception(), Long.MIN_VALUE, true);
        HLogger.warn(new Exception(), Long.MIN_VALUE, false);
        HLogger.warn(new Exception(), 0, true);
        HLogger.warn(new Exception(), 0, false);
        HLogger.warn((Exception) null, System.currentTimeMillis(), true);
        HLogger.warn((Exception) null, System.currentTimeMillis(), false);
        HLogger.warn((Exception) null, System.nanoTime(), true);
        HLogger.warn((Exception) null, System.nanoTime(), false);
        HLogger.warn((Exception) null, Long.MAX_VALUE, true);
        HLogger.warn((Exception) null, Long.MAX_VALUE, false);
        HLogger.warn((Exception) null, Long.MIN_VALUE, true);
        HLogger.warn((Exception) null, Long.MIN_VALUE, false);
        HLogger.warn((Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
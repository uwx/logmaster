
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest15 {

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
    public final void test_error_Exception_long_boolean() {
        HLogger.error(new Exception(), System.currentTimeMillis(), true);
        HLogger.error(new Exception(), System.currentTimeMillis(), false);
        HLogger.error(new Exception(), System.nanoTime(), true);
        HLogger.error(new Exception(), System.nanoTime(), false);
        HLogger.error(new Exception(), Long.MAX_VALUE, true);
        HLogger.error(new Exception(), Long.MAX_VALUE, false);
        HLogger.error(new Exception(), Long.MIN_VALUE, true);
        HLogger.error(new Exception(), Long.MIN_VALUE, false);
        HLogger.error(new Exception(), 0, true);
        HLogger.error(new Exception(), 0, false);
        HLogger.error((Exception) null, System.currentTimeMillis(), true);
        HLogger.error((Exception) null, System.currentTimeMillis(), false);
        HLogger.error((Exception) null, System.nanoTime(), true);
        HLogger.error((Exception) null, System.nanoTime(), false);
        HLogger.error((Exception) null, Long.MAX_VALUE, true);
        HLogger.error((Exception) null, Long.MAX_VALUE, false);
        HLogger.error((Exception) null, Long.MIN_VALUE, true);
        HLogger.error((Exception) null, Long.MIN_VALUE, false);
        HLogger.error((Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
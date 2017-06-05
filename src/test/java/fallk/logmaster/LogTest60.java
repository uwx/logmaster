
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest60 {

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
    public final void test_debug_Exception_long_boolean() {
        HLogger.debug(new Exception(), System.currentTimeMillis(), true);
        HLogger.debug(new Exception(), System.currentTimeMillis(), false);
        HLogger.debug(new Exception(), System.nanoTime(), true);
        HLogger.debug(new Exception(), System.nanoTime(), false);
        HLogger.debug(new Exception(), Long.MAX_VALUE, true);
        HLogger.debug(new Exception(), Long.MAX_VALUE, false);
        HLogger.debug(new Exception(), Long.MIN_VALUE, true);
        HLogger.debug(new Exception(), Long.MIN_VALUE, false);
        HLogger.debug(new Exception(), 0, true);
        HLogger.debug(new Exception(), 0, false);
        HLogger.debug((Exception) null, System.currentTimeMillis(), true);
        HLogger.debug((Exception) null, System.currentTimeMillis(), false);
        HLogger.debug((Exception) null, System.nanoTime(), true);
        HLogger.debug((Exception) null, System.nanoTime(), false);
        HLogger.debug((Exception) null, Long.MAX_VALUE, true);
        HLogger.debug((Exception) null, Long.MAX_VALUE, false);
        HLogger.debug((Exception) null, Long.MIN_VALUE, true);
        HLogger.debug((Exception) null, Long.MIN_VALUE, false);
        HLogger.debug((Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
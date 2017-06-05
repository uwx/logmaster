
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest33 {

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
    public final void test_fatal_Exception_long_boolean() {
        HLogger.fatal(new Exception(), System.currentTimeMillis(), true);
        HLogger.fatal(new Exception(), System.currentTimeMillis(), false);
        HLogger.fatal(new Exception(), System.nanoTime(), true);
        HLogger.fatal(new Exception(), System.nanoTime(), false);
        HLogger.fatal(new Exception(), Long.MAX_VALUE, true);
        HLogger.fatal(new Exception(), Long.MAX_VALUE, false);
        HLogger.fatal(new Exception(), Long.MIN_VALUE, true);
        HLogger.fatal(new Exception(), Long.MIN_VALUE, false);
        HLogger.fatal(new Exception(), 0, true);
        HLogger.fatal(new Exception(), 0, false);
        HLogger.fatal((Exception) null, System.currentTimeMillis(), true);
        HLogger.fatal((Exception) null, System.currentTimeMillis(), false);
        HLogger.fatal((Exception) null, System.nanoTime(), true);
        HLogger.fatal((Exception) null, System.nanoTime(), false);
        HLogger.fatal((Exception) null, Long.MAX_VALUE, true);
        HLogger.fatal((Exception) null, Long.MAX_VALUE, false);
        HLogger.fatal((Exception) null, Long.MIN_VALUE, true);
        HLogger.fatal((Exception) null, Long.MIN_VALUE, false);
        HLogger.fatal((Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
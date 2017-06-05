
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest67 {

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
    public final void test_debug_Exception_long() {
        HLogger.debug(new Exception(), System.currentTimeMillis());
        HLogger.debug(new Exception(), System.nanoTime());
        HLogger.debug(new Exception(), Long.MAX_VALUE);
        HLogger.debug(new Exception(), Long.MIN_VALUE);
        HLogger.debug(new Exception(), 0);
        HLogger.debug((Exception) null, System.currentTimeMillis());
        HLogger.debug((Exception) null, System.nanoTime());
        HLogger.debug((Exception) null, Long.MAX_VALUE);
        HLogger.debug((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
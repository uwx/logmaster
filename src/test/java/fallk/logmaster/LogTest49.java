
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest49 {

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
    public final void test_warn_Exception_long() {
        HLogger.warn(new Exception(), System.currentTimeMillis());
        HLogger.warn(new Exception(), System.nanoTime());
        HLogger.warn(new Exception(), Long.MAX_VALUE);
        HLogger.warn(new Exception(), Long.MIN_VALUE);
        HLogger.warn(new Exception(), 0);
        HLogger.warn((Exception) null, System.currentTimeMillis());
        HLogger.warn((Exception) null, System.nanoTime());
        HLogger.warn((Exception) null, Long.MAX_VALUE);
        HLogger.warn((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
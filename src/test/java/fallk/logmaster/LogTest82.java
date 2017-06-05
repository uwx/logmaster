
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest82 {

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
    public final void test_reset_Exception_long() {
        HLogger.reset(new Exception(), System.currentTimeMillis());
        HLogger.reset(new Exception(), System.nanoTime());
        HLogger.reset(new Exception(), Long.MAX_VALUE);
        HLogger.reset(new Exception(), Long.MIN_VALUE);
        HLogger.reset(new Exception(), 0);
        HLogger.reset((Exception) null, System.currentTimeMillis());
        HLogger.reset((Exception) null, System.nanoTime());
        HLogger.reset((Exception) null, Long.MAX_VALUE);
        HLogger.reset((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest277 {

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
    public final void test_brightBlue_Exception_long() {
        HLogger.brightBlue(new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(new Exception(), System.nanoTime());
        HLogger.brightBlue(new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(new Exception(), 0);
        HLogger.brightBlue((Exception) null, System.currentTimeMillis());
        HLogger.brightBlue((Exception) null, System.nanoTime());
        HLogger.brightBlue((Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
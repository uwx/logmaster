
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest232 {

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
    public final void test_green_Exception_long() {
        HLogger.green(new Exception(), System.currentTimeMillis());
        HLogger.green(new Exception(), System.nanoTime());
        HLogger.green(new Exception(), Long.MAX_VALUE);
        HLogger.green(new Exception(), Long.MIN_VALUE);
        HLogger.green(new Exception(), 0);
        HLogger.green((Exception) null, System.currentTimeMillis());
        HLogger.green((Exception) null, System.nanoTime());
        HLogger.green((Exception) null, Long.MAX_VALUE);
        HLogger.green((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
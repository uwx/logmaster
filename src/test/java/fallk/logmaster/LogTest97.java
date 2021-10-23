
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest97 {

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
    public final void test_red_Exception_long() {
        HLogger.red(new Exception(), System.currentTimeMillis());
        HLogger.red(new Exception(), System.nanoTime());
        HLogger.red(new Exception(), Long.MAX_VALUE);
        HLogger.red(new Exception(), Long.MIN_VALUE);
        HLogger.red(new Exception(), 0);
        HLogger.red((Exception) null, System.currentTimeMillis());
        HLogger.red((Exception) null, System.nanoTime());
        HLogger.red((Exception) null, Long.MAX_VALUE);
        HLogger.red((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
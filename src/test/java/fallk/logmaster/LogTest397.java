
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest397 {

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
    public final void test_bgRed_Exception_long() {
        HLogger.bgRed(new Exception(), System.currentTimeMillis());
        HLogger.bgRed(new Exception(), System.nanoTime());
        HLogger.bgRed(new Exception(), Long.MAX_VALUE);
        HLogger.bgRed(new Exception(), Long.MIN_VALUE);
        HLogger.bgRed(new Exception(), 0);
        HLogger.bgRed((Exception) null, System.currentTimeMillis());
        HLogger.bgRed((Exception) null, System.nanoTime());
        HLogger.bgRed((Exception) null, Long.MAX_VALUE);
        HLogger.bgRed((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
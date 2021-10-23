
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest247 {

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
    public final void test_brightGreen_Exception_long() {
        HLogger.brightGreen(new Exception(), System.currentTimeMillis());
        HLogger.brightGreen(new Exception(), System.nanoTime());
        HLogger.brightGreen(new Exception(), Long.MAX_VALUE);
        HLogger.brightGreen(new Exception(), Long.MIN_VALUE);
        HLogger.brightGreen(new Exception(), 0);
        HLogger.brightGreen((Exception) null, System.currentTimeMillis());
        HLogger.brightGreen((Exception) null, System.nanoTime());
        HLogger.brightGreen((Exception) null, Long.MAX_VALUE);
        HLogger.brightGreen((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
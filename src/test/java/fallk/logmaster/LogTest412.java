
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest412 {

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
    public final void test_bgGreen_Exception_long() {
        HLogger.bgGreen(new Exception(), System.currentTimeMillis());
        HLogger.bgGreen(new Exception(), System.nanoTime());
        HLogger.bgGreen(new Exception(), Long.MAX_VALUE);
        HLogger.bgGreen(new Exception(), Long.MIN_VALUE);
        HLogger.bgGreen(new Exception(), 0);
        HLogger.bgGreen((Exception) null, System.currentTimeMillis());
        HLogger.bgGreen((Exception) null, System.nanoTime());
        HLogger.bgGreen((Exception) null, Long.MAX_VALUE);
        HLogger.bgGreen((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
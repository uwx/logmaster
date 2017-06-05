
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest547 {

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
    public final void test_bgBlue_Exception_long() {
        HLogger.bgBlue(new Exception(), System.currentTimeMillis());
        HLogger.bgBlue(new Exception(), System.nanoTime());
        HLogger.bgBlue(new Exception(), Long.MAX_VALUE);
        HLogger.bgBlue(new Exception(), Long.MIN_VALUE);
        HLogger.bgBlue(new Exception(), 0);
        HLogger.bgBlue((Exception) null, System.currentTimeMillis());
        HLogger.bgBlue((Exception) null, System.nanoTime());
        HLogger.bgBlue((Exception) null, Long.MAX_VALUE);
        HLogger.bgBlue((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
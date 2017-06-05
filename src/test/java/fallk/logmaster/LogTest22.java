
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest22 {

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
    public final void test_error_Exception_long() {
        HLogger.error(new Exception(), System.currentTimeMillis());
        HLogger.error(new Exception(), System.nanoTime());
        HLogger.error(new Exception(), Long.MAX_VALUE);
        HLogger.error(new Exception(), Long.MIN_VALUE);
        HLogger.error(new Exception(), 0);
        HLogger.error((Exception) null, System.currentTimeMillis());
        HLogger.error((Exception) null, System.nanoTime());
        HLogger.error((Exception) null, Long.MAX_VALUE);
        HLogger.error((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
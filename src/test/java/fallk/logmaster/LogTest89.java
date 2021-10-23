
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest89 {

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
    public final void test_red_int_Exception_long() {
        HLogger.red(0, new Exception(), System.currentTimeMillis());
        HLogger.red(0, new Exception(), System.nanoTime());
        HLogger.red(0, new Exception(), Long.MAX_VALUE);
        HLogger.red(0, new Exception(), Long.MIN_VALUE);
        HLogger.red(0, new Exception(), 0);
        HLogger.red(0, (Exception) null, System.currentTimeMillis());
        HLogger.red(0, (Exception) null, System.nanoTime());
        HLogger.red(0, (Exception) null, Long.MAX_VALUE);
        HLogger.red(0, (Exception) null, Long.MIN_VALUE);
        HLogger.red(0, (Exception) null, 0);
        HLogger.red(1, new Exception(), System.currentTimeMillis());
        HLogger.red(1, new Exception(), System.nanoTime());
        HLogger.red(1, new Exception(), Long.MAX_VALUE);
        HLogger.red(1, new Exception(), Long.MIN_VALUE);
        HLogger.red(1, new Exception(), 0);
        HLogger.red(1, (Exception) null, System.currentTimeMillis());
        HLogger.red(1, (Exception) null, System.nanoTime());
        HLogger.red(1, (Exception) null, Long.MAX_VALUE);
        HLogger.red(1, (Exception) null, Long.MIN_VALUE);
        HLogger.red(1, (Exception) null, 0);
        HLogger.red(2, new Exception(), System.currentTimeMillis());
        HLogger.red(2, new Exception(), System.nanoTime());
        HLogger.red(2, new Exception(), Long.MAX_VALUE);
        HLogger.red(2, new Exception(), Long.MIN_VALUE);
        HLogger.red(2, new Exception(), 0);
        HLogger.red(2, (Exception) null, System.currentTimeMillis());
        HLogger.red(2, (Exception) null, System.nanoTime());
        HLogger.red(2, (Exception) null, Long.MAX_VALUE);
        HLogger.red(2, (Exception) null, Long.MIN_VALUE);
        HLogger.red(2, (Exception) null, 0);
        HLogger.red(3, new Exception(), System.currentTimeMillis());
        HLogger.red(3, new Exception(), System.nanoTime());
        HLogger.red(3, new Exception(), Long.MAX_VALUE);
        HLogger.red(3, new Exception(), Long.MIN_VALUE);
        HLogger.red(3, new Exception(), 0);
        HLogger.red(3, (Exception) null, System.currentTimeMillis());
        HLogger.red(3, (Exception) null, System.nanoTime());
        HLogger.red(3, (Exception) null, Long.MAX_VALUE);
        HLogger.red(3, (Exception) null, Long.MIN_VALUE);
        HLogger.red(3, (Exception) null, 0);
        HLogger.red(4, new Exception(), System.currentTimeMillis());
        HLogger.red(4, new Exception(), System.nanoTime());
        HLogger.red(4, new Exception(), Long.MAX_VALUE);
        HLogger.red(4, new Exception(), Long.MIN_VALUE);
        HLogger.red(4, new Exception(), 0);
        HLogger.red(4, (Exception) null, System.currentTimeMillis());
        HLogger.red(4, (Exception) null, System.nanoTime());
        HLogger.red(4, (Exception) null, Long.MAX_VALUE);
        HLogger.red(4, (Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
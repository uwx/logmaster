
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest269 {

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
    public final void test_brightBlue_int_Exception_long() {
        HLogger.brightBlue(0, new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(0, new Exception(), System.nanoTime());
        HLogger.brightBlue(0, new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(0, new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(0, new Exception(), 0);
        HLogger.brightBlue(0, (Exception) null, System.currentTimeMillis());
        HLogger.brightBlue(0, (Exception) null, System.nanoTime());
        HLogger.brightBlue(0, (Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue(0, (Exception) null, Long.MIN_VALUE);
        HLogger.brightBlue(0, (Exception) null, 0);
        HLogger.brightBlue(1, new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(1, new Exception(), System.nanoTime());
        HLogger.brightBlue(1, new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(1, new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(1, new Exception(), 0);
        HLogger.brightBlue(1, (Exception) null, System.currentTimeMillis());
        HLogger.brightBlue(1, (Exception) null, System.nanoTime());
        HLogger.brightBlue(1, (Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue(1, (Exception) null, Long.MIN_VALUE);
        HLogger.brightBlue(1, (Exception) null, 0);
        HLogger.brightBlue(2, new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(2, new Exception(), System.nanoTime());
        HLogger.brightBlue(2, new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(2, new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(2, new Exception(), 0);
        HLogger.brightBlue(2, (Exception) null, System.currentTimeMillis());
        HLogger.brightBlue(2, (Exception) null, System.nanoTime());
        HLogger.brightBlue(2, (Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue(2, (Exception) null, Long.MIN_VALUE);
        HLogger.brightBlue(2, (Exception) null, 0);
        HLogger.brightBlue(3, new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(3, new Exception(), System.nanoTime());
        HLogger.brightBlue(3, new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(3, new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(3, new Exception(), 0);
        HLogger.brightBlue(3, (Exception) null, System.currentTimeMillis());
        HLogger.brightBlue(3, (Exception) null, System.nanoTime());
        HLogger.brightBlue(3, (Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue(3, (Exception) null, Long.MIN_VALUE);
        HLogger.brightBlue(3, (Exception) null, 0);
        HLogger.brightBlue(4, new Exception(), System.currentTimeMillis());
        HLogger.brightBlue(4, new Exception(), System.nanoTime());
        HLogger.brightBlue(4, new Exception(), Long.MAX_VALUE);
        HLogger.brightBlue(4, new Exception(), Long.MIN_VALUE);
        HLogger.brightBlue(4, new Exception(), 0);
        HLogger.brightBlue(4, (Exception) null, System.currentTimeMillis());
        HLogger.brightBlue(4, (Exception) null, System.nanoTime());
        HLogger.brightBlue(4, (Exception) null, Long.MAX_VALUE);
        HLogger.brightBlue(4, (Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
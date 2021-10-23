
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest384 {

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
    public final void test_bgRed_int_Exception_long_boolean() {
        HLogger.bgRed(0, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgRed(0, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgRed(0, new Exception(), System.nanoTime(), true);
        HLogger.bgRed(0, new Exception(), System.nanoTime(), false);
        HLogger.bgRed(0, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgRed(0, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgRed(0, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgRed(0, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgRed(0, new Exception(), 0, true);
        HLogger.bgRed(0, new Exception(), 0, false);
        HLogger.bgRed(0, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgRed(0, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgRed(0, (Exception) null, System.nanoTime(), true);
        HLogger.bgRed(0, (Exception) null, System.nanoTime(), false);
        HLogger.bgRed(0, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgRed(0, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgRed(0, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgRed(0, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgRed(0, (Exception) null, 0, true);
        HLogger.bgRed(0, (Exception) null, 0, false);
        HLogger.bgRed(1, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgRed(1, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgRed(1, new Exception(), System.nanoTime(), true);
        HLogger.bgRed(1, new Exception(), System.nanoTime(), false);
        HLogger.bgRed(1, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgRed(1, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgRed(1, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgRed(1, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgRed(1, new Exception(), 0, true);
        HLogger.bgRed(1, new Exception(), 0, false);
        HLogger.bgRed(1, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgRed(1, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgRed(1, (Exception) null, System.nanoTime(), true);
        HLogger.bgRed(1, (Exception) null, System.nanoTime(), false);
        HLogger.bgRed(1, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgRed(1, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgRed(1, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgRed(1, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgRed(1, (Exception) null, 0, true);
        HLogger.bgRed(1, (Exception) null, 0, false);
        HLogger.bgRed(2, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgRed(2, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgRed(2, new Exception(), System.nanoTime(), true);
        HLogger.bgRed(2, new Exception(), System.nanoTime(), false);
        HLogger.bgRed(2, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgRed(2, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgRed(2, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgRed(2, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgRed(2, new Exception(), 0, true);
        HLogger.bgRed(2, new Exception(), 0, false);
        HLogger.bgRed(2, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgRed(2, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgRed(2, (Exception) null, System.nanoTime(), true);
        HLogger.bgRed(2, (Exception) null, System.nanoTime(), false);
        HLogger.bgRed(2, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgRed(2, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgRed(2, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgRed(2, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgRed(2, (Exception) null, 0, true);
        HLogger.bgRed(2, (Exception) null, 0, false);
        HLogger.bgRed(3, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgRed(3, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgRed(3, new Exception(), System.nanoTime(), true);
        HLogger.bgRed(3, new Exception(), System.nanoTime(), false);
        HLogger.bgRed(3, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgRed(3, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgRed(3, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgRed(3, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgRed(3, new Exception(), 0, true);
        HLogger.bgRed(3, new Exception(), 0, false);
        HLogger.bgRed(3, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgRed(3, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgRed(3, (Exception) null, System.nanoTime(), true);
        HLogger.bgRed(3, (Exception) null, System.nanoTime(), false);
        HLogger.bgRed(3, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgRed(3, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgRed(3, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgRed(3, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgRed(3, (Exception) null, 0, true);
        HLogger.bgRed(3, (Exception) null, 0, false);
        HLogger.bgRed(4, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgRed(4, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgRed(4, new Exception(), System.nanoTime(), true);
        HLogger.bgRed(4, new Exception(), System.nanoTime(), false);
        HLogger.bgRed(4, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgRed(4, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgRed(4, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgRed(4, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgRed(4, new Exception(), 0, true);
        HLogger.bgRed(4, new Exception(), 0, false);
        HLogger.bgRed(4, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgRed(4, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgRed(4, (Exception) null, System.nanoTime(), true);
        HLogger.bgRed(4, (Exception) null, System.nanoTime(), false);
        HLogger.bgRed(4, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgRed(4, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgRed(4, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgRed(4, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgRed(4, (Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
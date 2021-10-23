
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest414 {

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
    public final void test_bgYellow_int_Exception_long_boolean() {
        HLogger.bgYellow(0, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgYellow(0, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgYellow(0, new Exception(), System.nanoTime(), true);
        HLogger.bgYellow(0, new Exception(), System.nanoTime(), false);
        HLogger.bgYellow(0, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgYellow(0, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgYellow(0, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgYellow(0, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgYellow(0, new Exception(), 0, true);
        HLogger.bgYellow(0, new Exception(), 0, false);
        HLogger.bgYellow(0, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgYellow(0, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgYellow(0, (Exception) null, System.nanoTime(), true);
        HLogger.bgYellow(0, (Exception) null, System.nanoTime(), false);
        HLogger.bgYellow(0, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgYellow(0, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgYellow(0, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgYellow(0, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgYellow(0, (Exception) null, 0, true);
        HLogger.bgYellow(0, (Exception) null, 0, false);
        HLogger.bgYellow(1, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgYellow(1, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgYellow(1, new Exception(), System.nanoTime(), true);
        HLogger.bgYellow(1, new Exception(), System.nanoTime(), false);
        HLogger.bgYellow(1, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgYellow(1, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgYellow(1, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgYellow(1, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgYellow(1, new Exception(), 0, true);
        HLogger.bgYellow(1, new Exception(), 0, false);
        HLogger.bgYellow(1, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgYellow(1, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgYellow(1, (Exception) null, System.nanoTime(), true);
        HLogger.bgYellow(1, (Exception) null, System.nanoTime(), false);
        HLogger.bgYellow(1, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgYellow(1, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgYellow(1, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgYellow(1, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgYellow(1, (Exception) null, 0, true);
        HLogger.bgYellow(1, (Exception) null, 0, false);
        HLogger.bgYellow(2, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgYellow(2, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgYellow(2, new Exception(), System.nanoTime(), true);
        HLogger.bgYellow(2, new Exception(), System.nanoTime(), false);
        HLogger.bgYellow(2, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgYellow(2, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgYellow(2, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgYellow(2, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgYellow(2, new Exception(), 0, true);
        HLogger.bgYellow(2, new Exception(), 0, false);
        HLogger.bgYellow(2, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgYellow(2, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgYellow(2, (Exception) null, System.nanoTime(), true);
        HLogger.bgYellow(2, (Exception) null, System.nanoTime(), false);
        HLogger.bgYellow(2, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgYellow(2, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgYellow(2, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgYellow(2, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgYellow(2, (Exception) null, 0, true);
        HLogger.bgYellow(2, (Exception) null, 0, false);
        HLogger.bgYellow(3, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgYellow(3, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgYellow(3, new Exception(), System.nanoTime(), true);
        HLogger.bgYellow(3, new Exception(), System.nanoTime(), false);
        HLogger.bgYellow(3, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgYellow(3, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgYellow(3, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgYellow(3, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgYellow(3, new Exception(), 0, true);
        HLogger.bgYellow(3, new Exception(), 0, false);
        HLogger.bgYellow(3, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgYellow(3, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgYellow(3, (Exception) null, System.nanoTime(), true);
        HLogger.bgYellow(3, (Exception) null, System.nanoTime(), false);
        HLogger.bgYellow(3, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgYellow(3, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgYellow(3, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgYellow(3, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgYellow(3, (Exception) null, 0, true);
        HLogger.bgYellow(3, (Exception) null, 0, false);
        HLogger.bgYellow(4, new Exception(), System.currentTimeMillis(), true);
        HLogger.bgYellow(4, new Exception(), System.currentTimeMillis(), false);
        HLogger.bgYellow(4, new Exception(), System.nanoTime(), true);
        HLogger.bgYellow(4, new Exception(), System.nanoTime(), false);
        HLogger.bgYellow(4, new Exception(), Long.MAX_VALUE, true);
        HLogger.bgYellow(4, new Exception(), Long.MAX_VALUE, false);
        HLogger.bgYellow(4, new Exception(), Long.MIN_VALUE, true);
        HLogger.bgYellow(4, new Exception(), Long.MIN_VALUE, false);
        HLogger.bgYellow(4, new Exception(), 0, true);
        HLogger.bgYellow(4, new Exception(), 0, false);
        HLogger.bgYellow(4, (Exception) null, System.currentTimeMillis(), true);
        HLogger.bgYellow(4, (Exception) null, System.currentTimeMillis(), false);
        HLogger.bgYellow(4, (Exception) null, System.nanoTime(), true);
        HLogger.bgYellow(4, (Exception) null, System.nanoTime(), false);
        HLogger.bgYellow(4, (Exception) null, Long.MAX_VALUE, true);
        HLogger.bgYellow(4, (Exception) null, Long.MAX_VALUE, false);
        HLogger.bgYellow(4, (Exception) null, Long.MIN_VALUE, true);
        HLogger.bgYellow(4, (Exception) null, Long.MIN_VALUE, false);
        HLogger.bgYellow(4, (Exception) null, 0, true);
    }

    
    //! $CHALK_END
}
    
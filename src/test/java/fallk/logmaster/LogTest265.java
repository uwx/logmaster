
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest265 {

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
    public final void test_magenta_int_String_long_boolean() {
        HLogger.magenta(0, "Test string", System.currentTimeMillis(), true);
        HLogger.magenta(0, "Test string", System.currentTimeMillis(), false);
        HLogger.magenta(0, "Test string", System.nanoTime(), true);
        HLogger.magenta(0, "Test string", System.nanoTime(), false);
        HLogger.magenta(0, "Test string", Long.MAX_VALUE, true);
        HLogger.magenta(0, "Test string", Long.MAX_VALUE, false);
        HLogger.magenta(0, "Test string", Long.MIN_VALUE, true);
        HLogger.magenta(0, "Test string", Long.MIN_VALUE, false);
        HLogger.magenta(0, "Test string", 0, true);
        HLogger.magenta(0, "Test string", 0, false);
        HLogger.magenta(0, (String) null, System.currentTimeMillis(), true);
        HLogger.magenta(0, (String) null, System.currentTimeMillis(), false);
        HLogger.magenta(0, (String) null, System.nanoTime(), true);
        HLogger.magenta(0, (String) null, System.nanoTime(), false);
        HLogger.magenta(0, (String) null, Long.MAX_VALUE, true);
        HLogger.magenta(0, (String) null, Long.MAX_VALUE, false);
        HLogger.magenta(0, (String) null, Long.MIN_VALUE, true);
        HLogger.magenta(0, (String) null, Long.MIN_VALUE, false);
        HLogger.magenta(0, (String) null, 0, true);
        HLogger.magenta(0, (String) null, 0, false);
        HLogger.magenta(1, "Test string", System.currentTimeMillis(), true);
        HLogger.magenta(1, "Test string", System.currentTimeMillis(), false);
        HLogger.magenta(1, "Test string", System.nanoTime(), true);
        HLogger.magenta(1, "Test string", System.nanoTime(), false);
        HLogger.magenta(1, "Test string", Long.MAX_VALUE, true);
        HLogger.magenta(1, "Test string", Long.MAX_VALUE, false);
        HLogger.magenta(1, "Test string", Long.MIN_VALUE, true);
        HLogger.magenta(1, "Test string", Long.MIN_VALUE, false);
        HLogger.magenta(1, "Test string", 0, true);
        HLogger.magenta(1, "Test string", 0, false);
        HLogger.magenta(1, (String) null, System.currentTimeMillis(), true);
        HLogger.magenta(1, (String) null, System.currentTimeMillis(), false);
        HLogger.magenta(1, (String) null, System.nanoTime(), true);
        HLogger.magenta(1, (String) null, System.nanoTime(), false);
        HLogger.magenta(1, (String) null, Long.MAX_VALUE, true);
        HLogger.magenta(1, (String) null, Long.MAX_VALUE, false);
        HLogger.magenta(1, (String) null, Long.MIN_VALUE, true);
        HLogger.magenta(1, (String) null, Long.MIN_VALUE, false);
        HLogger.magenta(1, (String) null, 0, true);
        HLogger.magenta(1, (String) null, 0, false);
        HLogger.magenta(2, "Test string", System.currentTimeMillis(), true);
        HLogger.magenta(2, "Test string", System.currentTimeMillis(), false);
        HLogger.magenta(2, "Test string", System.nanoTime(), true);
        HLogger.magenta(2, "Test string", System.nanoTime(), false);
        HLogger.magenta(2, "Test string", Long.MAX_VALUE, true);
        HLogger.magenta(2, "Test string", Long.MAX_VALUE, false);
        HLogger.magenta(2, "Test string", Long.MIN_VALUE, true);
        HLogger.magenta(2, "Test string", Long.MIN_VALUE, false);
        HLogger.magenta(2, "Test string", 0, true);
        HLogger.magenta(2, "Test string", 0, false);
        HLogger.magenta(2, (String) null, System.currentTimeMillis(), true);
        HLogger.magenta(2, (String) null, System.currentTimeMillis(), false);
        HLogger.magenta(2, (String) null, System.nanoTime(), true);
        HLogger.magenta(2, (String) null, System.nanoTime(), false);
        HLogger.magenta(2, (String) null, Long.MAX_VALUE, true);
        HLogger.magenta(2, (String) null, Long.MAX_VALUE, false);
        HLogger.magenta(2, (String) null, Long.MIN_VALUE, true);
        HLogger.magenta(2, (String) null, Long.MIN_VALUE, false);
        HLogger.magenta(2, (String) null, 0, true);
        HLogger.magenta(2, (String) null, 0, false);
        HLogger.magenta(3, "Test string", System.currentTimeMillis(), true);
        HLogger.magenta(3, "Test string", System.currentTimeMillis(), false);
        HLogger.magenta(3, "Test string", System.nanoTime(), true);
        HLogger.magenta(3, "Test string", System.nanoTime(), false);
        HLogger.magenta(3, "Test string", Long.MAX_VALUE, true);
        HLogger.magenta(3, "Test string", Long.MAX_VALUE, false);
        HLogger.magenta(3, "Test string", Long.MIN_VALUE, true);
        HLogger.magenta(3, "Test string", Long.MIN_VALUE, false);
        HLogger.magenta(3, "Test string", 0, true);
        HLogger.magenta(3, "Test string", 0, false);
        HLogger.magenta(3, (String) null, System.currentTimeMillis(), true);
        HLogger.magenta(3, (String) null, System.currentTimeMillis(), false);
        HLogger.magenta(3, (String) null, System.nanoTime(), true);
        HLogger.magenta(3, (String) null, System.nanoTime(), false);
        HLogger.magenta(3, (String) null, Long.MAX_VALUE, true);
        HLogger.magenta(3, (String) null, Long.MAX_VALUE, false);
        HLogger.magenta(3, (String) null, Long.MIN_VALUE, true);
        HLogger.magenta(3, (String) null, Long.MIN_VALUE, false);
        HLogger.magenta(3, (String) null, 0, true);
        HLogger.magenta(3, (String) null, 0, false);
        HLogger.magenta(4, "Test string", System.currentTimeMillis(), true);
        HLogger.magenta(4, "Test string", System.currentTimeMillis(), false);
        HLogger.magenta(4, "Test string", System.nanoTime(), true);
        HLogger.magenta(4, "Test string", System.nanoTime(), false);
        HLogger.magenta(4, "Test string", Long.MAX_VALUE, true);
        HLogger.magenta(4, "Test string", Long.MAX_VALUE, false);
        HLogger.magenta(4, "Test string", Long.MIN_VALUE, true);
        HLogger.magenta(4, "Test string", Long.MIN_VALUE, false);
        HLogger.magenta(4, "Test string", 0, true);
        HLogger.magenta(4, "Test string", 0, false);
        HLogger.magenta(4, (String) null, System.currentTimeMillis(), true);
        HLogger.magenta(4, (String) null, System.currentTimeMillis(), false);
        HLogger.magenta(4, (String) null, System.nanoTime(), true);
        HLogger.magenta(4, (String) null, System.nanoTime(), false);
        HLogger.magenta(4, (String) null, Long.MAX_VALUE, true);
        HLogger.magenta(4, (String) null, Long.MAX_VALUE, false);
        HLogger.magenta(4, (String) null, Long.MIN_VALUE, true);
        HLogger.magenta(4, (String) null, Long.MIN_VALUE, false);
        HLogger.magenta(4, (String) null, 0, true);
    }

    
    //! $CHALK_END
}
    
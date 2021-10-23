
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest430 {

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
    public final void test_bgBlue_int_String_long_boolean() {
        HLogger.bgBlue(0, "Test string", System.currentTimeMillis(), true);
        HLogger.bgBlue(0, "Test string", System.currentTimeMillis(), false);
        HLogger.bgBlue(0, "Test string", System.nanoTime(), true);
        HLogger.bgBlue(0, "Test string", System.nanoTime(), false);
        HLogger.bgBlue(0, "Test string", Long.MAX_VALUE, true);
        HLogger.bgBlue(0, "Test string", Long.MAX_VALUE, false);
        HLogger.bgBlue(0, "Test string", Long.MIN_VALUE, true);
        HLogger.bgBlue(0, "Test string", Long.MIN_VALUE, false);
        HLogger.bgBlue(0, "Test string", 0, true);
        HLogger.bgBlue(0, "Test string", 0, false);
        HLogger.bgBlue(0, (String) null, System.currentTimeMillis(), true);
        HLogger.bgBlue(0, (String) null, System.currentTimeMillis(), false);
        HLogger.bgBlue(0, (String) null, System.nanoTime(), true);
        HLogger.bgBlue(0, (String) null, System.nanoTime(), false);
        HLogger.bgBlue(0, (String) null, Long.MAX_VALUE, true);
        HLogger.bgBlue(0, (String) null, Long.MAX_VALUE, false);
        HLogger.bgBlue(0, (String) null, Long.MIN_VALUE, true);
        HLogger.bgBlue(0, (String) null, Long.MIN_VALUE, false);
        HLogger.bgBlue(0, (String) null, 0, true);
        HLogger.bgBlue(0, (String) null, 0, false);
        HLogger.bgBlue(1, "Test string", System.currentTimeMillis(), true);
        HLogger.bgBlue(1, "Test string", System.currentTimeMillis(), false);
        HLogger.bgBlue(1, "Test string", System.nanoTime(), true);
        HLogger.bgBlue(1, "Test string", System.nanoTime(), false);
        HLogger.bgBlue(1, "Test string", Long.MAX_VALUE, true);
        HLogger.bgBlue(1, "Test string", Long.MAX_VALUE, false);
        HLogger.bgBlue(1, "Test string", Long.MIN_VALUE, true);
        HLogger.bgBlue(1, "Test string", Long.MIN_VALUE, false);
        HLogger.bgBlue(1, "Test string", 0, true);
        HLogger.bgBlue(1, "Test string", 0, false);
        HLogger.bgBlue(1, (String) null, System.currentTimeMillis(), true);
        HLogger.bgBlue(1, (String) null, System.currentTimeMillis(), false);
        HLogger.bgBlue(1, (String) null, System.nanoTime(), true);
        HLogger.bgBlue(1, (String) null, System.nanoTime(), false);
        HLogger.bgBlue(1, (String) null, Long.MAX_VALUE, true);
        HLogger.bgBlue(1, (String) null, Long.MAX_VALUE, false);
        HLogger.bgBlue(1, (String) null, Long.MIN_VALUE, true);
        HLogger.bgBlue(1, (String) null, Long.MIN_VALUE, false);
        HLogger.bgBlue(1, (String) null, 0, true);
        HLogger.bgBlue(1, (String) null, 0, false);
        HLogger.bgBlue(2, "Test string", System.currentTimeMillis(), true);
        HLogger.bgBlue(2, "Test string", System.currentTimeMillis(), false);
        HLogger.bgBlue(2, "Test string", System.nanoTime(), true);
        HLogger.bgBlue(2, "Test string", System.nanoTime(), false);
        HLogger.bgBlue(2, "Test string", Long.MAX_VALUE, true);
        HLogger.bgBlue(2, "Test string", Long.MAX_VALUE, false);
        HLogger.bgBlue(2, "Test string", Long.MIN_VALUE, true);
        HLogger.bgBlue(2, "Test string", Long.MIN_VALUE, false);
        HLogger.bgBlue(2, "Test string", 0, true);
        HLogger.bgBlue(2, "Test string", 0, false);
        HLogger.bgBlue(2, (String) null, System.currentTimeMillis(), true);
        HLogger.bgBlue(2, (String) null, System.currentTimeMillis(), false);
        HLogger.bgBlue(2, (String) null, System.nanoTime(), true);
        HLogger.bgBlue(2, (String) null, System.nanoTime(), false);
        HLogger.bgBlue(2, (String) null, Long.MAX_VALUE, true);
        HLogger.bgBlue(2, (String) null, Long.MAX_VALUE, false);
        HLogger.bgBlue(2, (String) null, Long.MIN_VALUE, true);
        HLogger.bgBlue(2, (String) null, Long.MIN_VALUE, false);
        HLogger.bgBlue(2, (String) null, 0, true);
        HLogger.bgBlue(2, (String) null, 0, false);
        HLogger.bgBlue(3, "Test string", System.currentTimeMillis(), true);
        HLogger.bgBlue(3, "Test string", System.currentTimeMillis(), false);
        HLogger.bgBlue(3, "Test string", System.nanoTime(), true);
        HLogger.bgBlue(3, "Test string", System.nanoTime(), false);
        HLogger.bgBlue(3, "Test string", Long.MAX_VALUE, true);
        HLogger.bgBlue(3, "Test string", Long.MAX_VALUE, false);
        HLogger.bgBlue(3, "Test string", Long.MIN_VALUE, true);
        HLogger.bgBlue(3, "Test string", Long.MIN_VALUE, false);
        HLogger.bgBlue(3, "Test string", 0, true);
        HLogger.bgBlue(3, "Test string", 0, false);
        HLogger.bgBlue(3, (String) null, System.currentTimeMillis(), true);
        HLogger.bgBlue(3, (String) null, System.currentTimeMillis(), false);
        HLogger.bgBlue(3, (String) null, System.nanoTime(), true);
        HLogger.bgBlue(3, (String) null, System.nanoTime(), false);
        HLogger.bgBlue(3, (String) null, Long.MAX_VALUE, true);
        HLogger.bgBlue(3, (String) null, Long.MAX_VALUE, false);
        HLogger.bgBlue(3, (String) null, Long.MIN_VALUE, true);
        HLogger.bgBlue(3, (String) null, Long.MIN_VALUE, false);
        HLogger.bgBlue(3, (String) null, 0, true);
        HLogger.bgBlue(3, (String) null, 0, false);
        HLogger.bgBlue(4, "Test string", System.currentTimeMillis(), true);
        HLogger.bgBlue(4, "Test string", System.currentTimeMillis(), false);
        HLogger.bgBlue(4, "Test string", System.nanoTime(), true);
        HLogger.bgBlue(4, "Test string", System.nanoTime(), false);
        HLogger.bgBlue(4, "Test string", Long.MAX_VALUE, true);
        HLogger.bgBlue(4, "Test string", Long.MAX_VALUE, false);
        HLogger.bgBlue(4, "Test string", Long.MIN_VALUE, true);
        HLogger.bgBlue(4, "Test string", Long.MIN_VALUE, false);
        HLogger.bgBlue(4, "Test string", 0, true);
        HLogger.bgBlue(4, "Test string", 0, false);
        HLogger.bgBlue(4, (String) null, System.currentTimeMillis(), true);
        HLogger.bgBlue(4, (String) null, System.currentTimeMillis(), false);
        HLogger.bgBlue(4, (String) null, System.nanoTime(), true);
        HLogger.bgBlue(4, (String) null, System.nanoTime(), false);
        HLogger.bgBlue(4, (String) null, Long.MAX_VALUE, true);
        HLogger.bgBlue(4, (String) null, Long.MAX_VALUE, false);
        HLogger.bgBlue(4, (String) null, Long.MIN_VALUE, true);
        HLogger.bgBlue(4, (String) null, Long.MIN_VALUE, false);
        HLogger.bgBlue(4, (String) null, 0, true);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest281 {

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
    public final void test_cyan_int_Object_long_boolean() {
        HLogger.cyan(0, new Object(), System.currentTimeMillis(), true);
        HLogger.cyan(0, new Object(), System.currentTimeMillis(), false);
        HLogger.cyan(0, new Object(), System.nanoTime(), true);
        HLogger.cyan(0, new Object(), System.nanoTime(), false);
        HLogger.cyan(0, new Object(), Long.MAX_VALUE, true);
        HLogger.cyan(0, new Object(), Long.MAX_VALUE, false);
        HLogger.cyan(0, new Object(), Long.MIN_VALUE, true);
        HLogger.cyan(0, new Object(), Long.MIN_VALUE, false);
        HLogger.cyan(0, new Object(), 0, true);
        HLogger.cyan(0, new Object(), 0, false);
        HLogger.cyan(0, new Dummy(), System.currentTimeMillis(), true);
        HLogger.cyan(0, new Dummy(), System.currentTimeMillis(), false);
        HLogger.cyan(0, new Dummy(), System.nanoTime(), true);
        HLogger.cyan(0, new Dummy(), System.nanoTime(), false);
        HLogger.cyan(0, new Dummy(), Long.MAX_VALUE, true);
        HLogger.cyan(0, new Dummy(), Long.MAX_VALUE, false);
        HLogger.cyan(0, new Dummy(), Long.MIN_VALUE, true);
        HLogger.cyan(0, new Dummy(), Long.MIN_VALUE, false);
        HLogger.cyan(0, new Dummy(), 0, true);
        HLogger.cyan(0, new Dummy(), 0, false);
        HLogger.cyan(0, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.cyan(0, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.cyan(0, new Dummy2(), System.nanoTime(), true);
        HLogger.cyan(0, new Dummy2(), System.nanoTime(), false);
        HLogger.cyan(0, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.cyan(0, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.cyan(0, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.cyan(0, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.cyan(0, new Dummy2(), 0, true);
        HLogger.cyan(0, new Dummy2(), 0, false);
        HLogger.cyan(0, (Object) null, System.currentTimeMillis(), true);
        HLogger.cyan(0, (Object) null, System.currentTimeMillis(), false);
        HLogger.cyan(0, (Object) null, System.nanoTime(), true);
        HLogger.cyan(0, (Object) null, System.nanoTime(), false);
        HLogger.cyan(0, (Object) null, Long.MAX_VALUE, true);
        HLogger.cyan(0, (Object) null, Long.MAX_VALUE, false);
        HLogger.cyan(0, (Object) null, Long.MIN_VALUE, true);
        HLogger.cyan(0, (Object) null, Long.MIN_VALUE, false);
        HLogger.cyan(0, (Object) null, 0, true);
        HLogger.cyan(0, (Object) null, 0, false);
        HLogger.cyan(1, new Object(), System.currentTimeMillis(), true);
        HLogger.cyan(1, new Object(), System.currentTimeMillis(), false);
        HLogger.cyan(1, new Object(), System.nanoTime(), true);
        HLogger.cyan(1, new Object(), System.nanoTime(), false);
        HLogger.cyan(1, new Object(), Long.MAX_VALUE, true);
        HLogger.cyan(1, new Object(), Long.MAX_VALUE, false);
        HLogger.cyan(1, new Object(), Long.MIN_VALUE, true);
        HLogger.cyan(1, new Object(), Long.MIN_VALUE, false);
        HLogger.cyan(1, new Object(), 0, true);
        HLogger.cyan(1, new Object(), 0, false);
        HLogger.cyan(1, new Dummy(), System.currentTimeMillis(), true);
        HLogger.cyan(1, new Dummy(), System.currentTimeMillis(), false);
        HLogger.cyan(1, new Dummy(), System.nanoTime(), true);
        HLogger.cyan(1, new Dummy(), System.nanoTime(), false);
        HLogger.cyan(1, new Dummy(), Long.MAX_VALUE, true);
        HLogger.cyan(1, new Dummy(), Long.MAX_VALUE, false);
        HLogger.cyan(1, new Dummy(), Long.MIN_VALUE, true);
        HLogger.cyan(1, new Dummy(), Long.MIN_VALUE, false);
        HLogger.cyan(1, new Dummy(), 0, true);
        HLogger.cyan(1, new Dummy(), 0, false);
        HLogger.cyan(1, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.cyan(1, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.cyan(1, new Dummy2(), System.nanoTime(), true);
        HLogger.cyan(1, new Dummy2(), System.nanoTime(), false);
        HLogger.cyan(1, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.cyan(1, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.cyan(1, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.cyan(1, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.cyan(1, new Dummy2(), 0, true);
        HLogger.cyan(1, new Dummy2(), 0, false);
        HLogger.cyan(1, (Object) null, System.currentTimeMillis(), true);
        HLogger.cyan(1, (Object) null, System.currentTimeMillis(), false);
        HLogger.cyan(1, (Object) null, System.nanoTime(), true);
        HLogger.cyan(1, (Object) null, System.nanoTime(), false);
        HLogger.cyan(1, (Object) null, Long.MAX_VALUE, true);
        HLogger.cyan(1, (Object) null, Long.MAX_VALUE, false);
        HLogger.cyan(1, (Object) null, Long.MIN_VALUE, true);
        HLogger.cyan(1, (Object) null, Long.MIN_VALUE, false);
        HLogger.cyan(1, (Object) null, 0, true);
        HLogger.cyan(1, (Object) null, 0, false);
        HLogger.cyan(2, new Object(), System.currentTimeMillis(), true);
        HLogger.cyan(2, new Object(), System.currentTimeMillis(), false);
        HLogger.cyan(2, new Object(), System.nanoTime(), true);
        HLogger.cyan(2, new Object(), System.nanoTime(), false);
        HLogger.cyan(2, new Object(), Long.MAX_VALUE, true);
        HLogger.cyan(2, new Object(), Long.MAX_VALUE, false);
        HLogger.cyan(2, new Object(), Long.MIN_VALUE, true);
        HLogger.cyan(2, new Object(), Long.MIN_VALUE, false);
        HLogger.cyan(2, new Object(), 0, true);
        HLogger.cyan(2, new Object(), 0, false);
        HLogger.cyan(2, new Dummy(), System.currentTimeMillis(), true);
        HLogger.cyan(2, new Dummy(), System.currentTimeMillis(), false);
        HLogger.cyan(2, new Dummy(), System.nanoTime(), true);
        HLogger.cyan(2, new Dummy(), System.nanoTime(), false);
        HLogger.cyan(2, new Dummy(), Long.MAX_VALUE, true);
        HLogger.cyan(2, new Dummy(), Long.MAX_VALUE, false);
        HLogger.cyan(2, new Dummy(), Long.MIN_VALUE, true);
        HLogger.cyan(2, new Dummy(), Long.MIN_VALUE, false);
        HLogger.cyan(2, new Dummy(), 0, true);
        HLogger.cyan(2, new Dummy(), 0, false);
        HLogger.cyan(2, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.cyan(2, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.cyan(2, new Dummy2(), System.nanoTime(), true);
        HLogger.cyan(2, new Dummy2(), System.nanoTime(), false);
        HLogger.cyan(2, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.cyan(2, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.cyan(2, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.cyan(2, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.cyan(2, new Dummy2(), 0, true);
        HLogger.cyan(2, new Dummy2(), 0, false);
        HLogger.cyan(2, (Object) null, System.currentTimeMillis(), true);
        HLogger.cyan(2, (Object) null, System.currentTimeMillis(), false);
        HLogger.cyan(2, (Object) null, System.nanoTime(), true);
        HLogger.cyan(2, (Object) null, System.nanoTime(), false);
        HLogger.cyan(2, (Object) null, Long.MAX_VALUE, true);
        HLogger.cyan(2, (Object) null, Long.MAX_VALUE, false);
        HLogger.cyan(2, (Object) null, Long.MIN_VALUE, true);
        HLogger.cyan(2, (Object) null, Long.MIN_VALUE, false);
        HLogger.cyan(2, (Object) null, 0, true);
        HLogger.cyan(2, (Object) null, 0, false);
        HLogger.cyan(3, new Object(), System.currentTimeMillis(), true);
        HLogger.cyan(3, new Object(), System.currentTimeMillis(), false);
        HLogger.cyan(3, new Object(), System.nanoTime(), true);
        HLogger.cyan(3, new Object(), System.nanoTime(), false);
        HLogger.cyan(3, new Object(), Long.MAX_VALUE, true);
        HLogger.cyan(3, new Object(), Long.MAX_VALUE, false);
        HLogger.cyan(3, new Object(), Long.MIN_VALUE, true);
        HLogger.cyan(3, new Object(), Long.MIN_VALUE, false);
        HLogger.cyan(3, new Object(), 0, true);
        HLogger.cyan(3, new Object(), 0, false);
        HLogger.cyan(3, new Dummy(), System.currentTimeMillis(), true);
        HLogger.cyan(3, new Dummy(), System.currentTimeMillis(), false);
        HLogger.cyan(3, new Dummy(), System.nanoTime(), true);
        HLogger.cyan(3, new Dummy(), System.nanoTime(), false);
        HLogger.cyan(3, new Dummy(), Long.MAX_VALUE, true);
        HLogger.cyan(3, new Dummy(), Long.MAX_VALUE, false);
        HLogger.cyan(3, new Dummy(), Long.MIN_VALUE, true);
        HLogger.cyan(3, new Dummy(), Long.MIN_VALUE, false);
        HLogger.cyan(3, new Dummy(), 0, true);
        HLogger.cyan(3, new Dummy(), 0, false);
        HLogger.cyan(3, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.cyan(3, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.cyan(3, new Dummy2(), System.nanoTime(), true);
        HLogger.cyan(3, new Dummy2(), System.nanoTime(), false);
        HLogger.cyan(3, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.cyan(3, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.cyan(3, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.cyan(3, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.cyan(3, new Dummy2(), 0, true);
        HLogger.cyan(3, new Dummy2(), 0, false);
        HLogger.cyan(3, (Object) null, System.currentTimeMillis(), true);
        HLogger.cyan(3, (Object) null, System.currentTimeMillis(), false);
        HLogger.cyan(3, (Object) null, System.nanoTime(), true);
        HLogger.cyan(3, (Object) null, System.nanoTime(), false);
        HLogger.cyan(3, (Object) null, Long.MAX_VALUE, true);
        HLogger.cyan(3, (Object) null, Long.MAX_VALUE, false);
        HLogger.cyan(3, (Object) null, Long.MIN_VALUE, true);
        HLogger.cyan(3, (Object) null, Long.MIN_VALUE, false);
        HLogger.cyan(3, (Object) null, 0, true);
        HLogger.cyan(3, (Object) null, 0, false);
        HLogger.cyan(4, new Object(), System.currentTimeMillis(), true);
        HLogger.cyan(4, new Object(), System.currentTimeMillis(), false);
        HLogger.cyan(4, new Object(), System.nanoTime(), true);
        HLogger.cyan(4, new Object(), System.nanoTime(), false);
        HLogger.cyan(4, new Object(), Long.MAX_VALUE, true);
        HLogger.cyan(4, new Object(), Long.MAX_VALUE, false);
        HLogger.cyan(4, new Object(), Long.MIN_VALUE, true);
        HLogger.cyan(4, new Object(), Long.MIN_VALUE, false);
        HLogger.cyan(4, new Object(), 0, true);
        HLogger.cyan(4, new Object(), 0, false);
        HLogger.cyan(4, new Dummy(), System.currentTimeMillis(), true);
        HLogger.cyan(4, new Dummy(), System.currentTimeMillis(), false);
        HLogger.cyan(4, new Dummy(), System.nanoTime(), true);
        HLogger.cyan(4, new Dummy(), System.nanoTime(), false);
        HLogger.cyan(4, new Dummy(), Long.MAX_VALUE, true);
        HLogger.cyan(4, new Dummy(), Long.MAX_VALUE, false);
        HLogger.cyan(4, new Dummy(), Long.MIN_VALUE, true);
        HLogger.cyan(4, new Dummy(), Long.MIN_VALUE, false);
        HLogger.cyan(4, new Dummy(), 0, true);
        HLogger.cyan(4, new Dummy(), 0, false);
        HLogger.cyan(4, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.cyan(4, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.cyan(4, new Dummy2(), System.nanoTime(), true);
        HLogger.cyan(4, new Dummy2(), System.nanoTime(), false);
        HLogger.cyan(4, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.cyan(4, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.cyan(4, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.cyan(4, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.cyan(4, new Dummy2(), 0, true);
        HLogger.cyan(4, new Dummy2(), 0, false);
        HLogger.cyan(4, (Object) null, System.currentTimeMillis(), true);
        HLogger.cyan(4, (Object) null, System.currentTimeMillis(), false);
        HLogger.cyan(4, (Object) null, System.nanoTime(), true);
        HLogger.cyan(4, (Object) null, System.nanoTime(), false);
        HLogger.cyan(4, (Object) null, Long.MAX_VALUE, true);
        HLogger.cyan(4, (Object) null, Long.MAX_VALUE, false);
        HLogger.cyan(4, (Object) null, Long.MIN_VALUE, true);
        HLogger.cyan(4, (Object) null, Long.MIN_VALUE, false);
        HLogger.cyan(4, (Object) null, 0, true);
    }

    
    //! $CHALK_END
}
    
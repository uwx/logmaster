
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest266 {

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
    public final void test_brightBlue_int_Object_long_boolean() {
        HLogger.brightBlue(0, new Object(), System.currentTimeMillis(), true);
        HLogger.brightBlue(0, new Object(), System.currentTimeMillis(), false);
        HLogger.brightBlue(0, new Object(), System.nanoTime(), true);
        HLogger.brightBlue(0, new Object(), System.nanoTime(), false);
        HLogger.brightBlue(0, new Object(), Long.MAX_VALUE, true);
        HLogger.brightBlue(0, new Object(), Long.MAX_VALUE, false);
        HLogger.brightBlue(0, new Object(), Long.MIN_VALUE, true);
        HLogger.brightBlue(0, new Object(), Long.MIN_VALUE, false);
        HLogger.brightBlue(0, new Object(), 0, true);
        HLogger.brightBlue(0, new Object(), 0, false);
        HLogger.brightBlue(0, new Dummy(), System.currentTimeMillis(), true);
        HLogger.brightBlue(0, new Dummy(), System.currentTimeMillis(), false);
        HLogger.brightBlue(0, new Dummy(), System.nanoTime(), true);
        HLogger.brightBlue(0, new Dummy(), System.nanoTime(), false);
        HLogger.brightBlue(0, new Dummy(), Long.MAX_VALUE, true);
        HLogger.brightBlue(0, new Dummy(), Long.MAX_VALUE, false);
        HLogger.brightBlue(0, new Dummy(), Long.MIN_VALUE, true);
        HLogger.brightBlue(0, new Dummy(), Long.MIN_VALUE, false);
        HLogger.brightBlue(0, new Dummy(), 0, true);
        HLogger.brightBlue(0, new Dummy(), 0, false);
        HLogger.brightBlue(0, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.brightBlue(0, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.brightBlue(0, new Dummy2(), System.nanoTime(), true);
        HLogger.brightBlue(0, new Dummy2(), System.nanoTime(), false);
        HLogger.brightBlue(0, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.brightBlue(0, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.brightBlue(0, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.brightBlue(0, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.brightBlue(0, new Dummy2(), 0, true);
        HLogger.brightBlue(0, new Dummy2(), 0, false);
        HLogger.brightBlue(0, (Object) null, System.currentTimeMillis(), true);
        HLogger.brightBlue(0, (Object) null, System.currentTimeMillis(), false);
        HLogger.brightBlue(0, (Object) null, System.nanoTime(), true);
        HLogger.brightBlue(0, (Object) null, System.nanoTime(), false);
        HLogger.brightBlue(0, (Object) null, Long.MAX_VALUE, true);
        HLogger.brightBlue(0, (Object) null, Long.MAX_VALUE, false);
        HLogger.brightBlue(0, (Object) null, Long.MIN_VALUE, true);
        HLogger.brightBlue(0, (Object) null, Long.MIN_VALUE, false);
        HLogger.brightBlue(0, (Object) null, 0, true);
        HLogger.brightBlue(0, (Object) null, 0, false);
        HLogger.brightBlue(1, new Object(), System.currentTimeMillis(), true);
        HLogger.brightBlue(1, new Object(), System.currentTimeMillis(), false);
        HLogger.brightBlue(1, new Object(), System.nanoTime(), true);
        HLogger.brightBlue(1, new Object(), System.nanoTime(), false);
        HLogger.brightBlue(1, new Object(), Long.MAX_VALUE, true);
        HLogger.brightBlue(1, new Object(), Long.MAX_VALUE, false);
        HLogger.brightBlue(1, new Object(), Long.MIN_VALUE, true);
        HLogger.brightBlue(1, new Object(), Long.MIN_VALUE, false);
        HLogger.brightBlue(1, new Object(), 0, true);
        HLogger.brightBlue(1, new Object(), 0, false);
        HLogger.brightBlue(1, new Dummy(), System.currentTimeMillis(), true);
        HLogger.brightBlue(1, new Dummy(), System.currentTimeMillis(), false);
        HLogger.brightBlue(1, new Dummy(), System.nanoTime(), true);
        HLogger.brightBlue(1, new Dummy(), System.nanoTime(), false);
        HLogger.brightBlue(1, new Dummy(), Long.MAX_VALUE, true);
        HLogger.brightBlue(1, new Dummy(), Long.MAX_VALUE, false);
        HLogger.brightBlue(1, new Dummy(), Long.MIN_VALUE, true);
        HLogger.brightBlue(1, new Dummy(), Long.MIN_VALUE, false);
        HLogger.brightBlue(1, new Dummy(), 0, true);
        HLogger.brightBlue(1, new Dummy(), 0, false);
        HLogger.brightBlue(1, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.brightBlue(1, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.brightBlue(1, new Dummy2(), System.nanoTime(), true);
        HLogger.brightBlue(1, new Dummy2(), System.nanoTime(), false);
        HLogger.brightBlue(1, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.brightBlue(1, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.brightBlue(1, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.brightBlue(1, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.brightBlue(1, new Dummy2(), 0, true);
        HLogger.brightBlue(1, new Dummy2(), 0, false);
        HLogger.brightBlue(1, (Object) null, System.currentTimeMillis(), true);
        HLogger.brightBlue(1, (Object) null, System.currentTimeMillis(), false);
        HLogger.brightBlue(1, (Object) null, System.nanoTime(), true);
        HLogger.brightBlue(1, (Object) null, System.nanoTime(), false);
        HLogger.brightBlue(1, (Object) null, Long.MAX_VALUE, true);
        HLogger.brightBlue(1, (Object) null, Long.MAX_VALUE, false);
        HLogger.brightBlue(1, (Object) null, Long.MIN_VALUE, true);
        HLogger.brightBlue(1, (Object) null, Long.MIN_VALUE, false);
        HLogger.brightBlue(1, (Object) null, 0, true);
        HLogger.brightBlue(1, (Object) null, 0, false);
        HLogger.brightBlue(2, new Object(), System.currentTimeMillis(), true);
        HLogger.brightBlue(2, new Object(), System.currentTimeMillis(), false);
        HLogger.brightBlue(2, new Object(), System.nanoTime(), true);
        HLogger.brightBlue(2, new Object(), System.nanoTime(), false);
        HLogger.brightBlue(2, new Object(), Long.MAX_VALUE, true);
        HLogger.brightBlue(2, new Object(), Long.MAX_VALUE, false);
        HLogger.brightBlue(2, new Object(), Long.MIN_VALUE, true);
        HLogger.brightBlue(2, new Object(), Long.MIN_VALUE, false);
        HLogger.brightBlue(2, new Object(), 0, true);
        HLogger.brightBlue(2, new Object(), 0, false);
        HLogger.brightBlue(2, new Dummy(), System.currentTimeMillis(), true);
        HLogger.brightBlue(2, new Dummy(), System.currentTimeMillis(), false);
        HLogger.brightBlue(2, new Dummy(), System.nanoTime(), true);
        HLogger.brightBlue(2, new Dummy(), System.nanoTime(), false);
        HLogger.brightBlue(2, new Dummy(), Long.MAX_VALUE, true);
        HLogger.brightBlue(2, new Dummy(), Long.MAX_VALUE, false);
        HLogger.brightBlue(2, new Dummy(), Long.MIN_VALUE, true);
        HLogger.brightBlue(2, new Dummy(), Long.MIN_VALUE, false);
        HLogger.brightBlue(2, new Dummy(), 0, true);
        HLogger.brightBlue(2, new Dummy(), 0, false);
        HLogger.brightBlue(2, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.brightBlue(2, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.brightBlue(2, new Dummy2(), System.nanoTime(), true);
        HLogger.brightBlue(2, new Dummy2(), System.nanoTime(), false);
        HLogger.brightBlue(2, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.brightBlue(2, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.brightBlue(2, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.brightBlue(2, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.brightBlue(2, new Dummy2(), 0, true);
        HLogger.brightBlue(2, new Dummy2(), 0, false);
        HLogger.brightBlue(2, (Object) null, System.currentTimeMillis(), true);
        HLogger.brightBlue(2, (Object) null, System.currentTimeMillis(), false);
        HLogger.brightBlue(2, (Object) null, System.nanoTime(), true);
        HLogger.brightBlue(2, (Object) null, System.nanoTime(), false);
        HLogger.brightBlue(2, (Object) null, Long.MAX_VALUE, true);
        HLogger.brightBlue(2, (Object) null, Long.MAX_VALUE, false);
        HLogger.brightBlue(2, (Object) null, Long.MIN_VALUE, true);
        HLogger.brightBlue(2, (Object) null, Long.MIN_VALUE, false);
        HLogger.brightBlue(2, (Object) null, 0, true);
        HLogger.brightBlue(2, (Object) null, 0, false);
        HLogger.brightBlue(3, new Object(), System.currentTimeMillis(), true);
        HLogger.brightBlue(3, new Object(), System.currentTimeMillis(), false);
        HLogger.brightBlue(3, new Object(), System.nanoTime(), true);
        HLogger.brightBlue(3, new Object(), System.nanoTime(), false);
        HLogger.brightBlue(3, new Object(), Long.MAX_VALUE, true);
        HLogger.brightBlue(3, new Object(), Long.MAX_VALUE, false);
        HLogger.brightBlue(3, new Object(), Long.MIN_VALUE, true);
        HLogger.brightBlue(3, new Object(), Long.MIN_VALUE, false);
        HLogger.brightBlue(3, new Object(), 0, true);
        HLogger.brightBlue(3, new Object(), 0, false);
        HLogger.brightBlue(3, new Dummy(), System.currentTimeMillis(), true);
        HLogger.brightBlue(3, new Dummy(), System.currentTimeMillis(), false);
        HLogger.brightBlue(3, new Dummy(), System.nanoTime(), true);
        HLogger.brightBlue(3, new Dummy(), System.nanoTime(), false);
        HLogger.brightBlue(3, new Dummy(), Long.MAX_VALUE, true);
        HLogger.brightBlue(3, new Dummy(), Long.MAX_VALUE, false);
        HLogger.brightBlue(3, new Dummy(), Long.MIN_VALUE, true);
        HLogger.brightBlue(3, new Dummy(), Long.MIN_VALUE, false);
        HLogger.brightBlue(3, new Dummy(), 0, true);
        HLogger.brightBlue(3, new Dummy(), 0, false);
        HLogger.brightBlue(3, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.brightBlue(3, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.brightBlue(3, new Dummy2(), System.nanoTime(), true);
        HLogger.brightBlue(3, new Dummy2(), System.nanoTime(), false);
        HLogger.brightBlue(3, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.brightBlue(3, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.brightBlue(3, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.brightBlue(3, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.brightBlue(3, new Dummy2(), 0, true);
        HLogger.brightBlue(3, new Dummy2(), 0, false);
        HLogger.brightBlue(3, (Object) null, System.currentTimeMillis(), true);
        HLogger.brightBlue(3, (Object) null, System.currentTimeMillis(), false);
        HLogger.brightBlue(3, (Object) null, System.nanoTime(), true);
        HLogger.brightBlue(3, (Object) null, System.nanoTime(), false);
        HLogger.brightBlue(3, (Object) null, Long.MAX_VALUE, true);
        HLogger.brightBlue(3, (Object) null, Long.MAX_VALUE, false);
        HLogger.brightBlue(3, (Object) null, Long.MIN_VALUE, true);
        HLogger.brightBlue(3, (Object) null, Long.MIN_VALUE, false);
        HLogger.brightBlue(3, (Object) null, 0, true);
        HLogger.brightBlue(3, (Object) null, 0, false);
        HLogger.brightBlue(4, new Object(), System.currentTimeMillis(), true);
        HLogger.brightBlue(4, new Object(), System.currentTimeMillis(), false);
        HLogger.brightBlue(4, new Object(), System.nanoTime(), true);
        HLogger.brightBlue(4, new Object(), System.nanoTime(), false);
        HLogger.brightBlue(4, new Object(), Long.MAX_VALUE, true);
        HLogger.brightBlue(4, new Object(), Long.MAX_VALUE, false);
        HLogger.brightBlue(4, new Object(), Long.MIN_VALUE, true);
        HLogger.brightBlue(4, new Object(), Long.MIN_VALUE, false);
        HLogger.brightBlue(4, new Object(), 0, true);
        HLogger.brightBlue(4, new Object(), 0, false);
        HLogger.brightBlue(4, new Dummy(), System.currentTimeMillis(), true);
        HLogger.brightBlue(4, new Dummy(), System.currentTimeMillis(), false);
        HLogger.brightBlue(4, new Dummy(), System.nanoTime(), true);
        HLogger.brightBlue(4, new Dummy(), System.nanoTime(), false);
        HLogger.brightBlue(4, new Dummy(), Long.MAX_VALUE, true);
        HLogger.brightBlue(4, new Dummy(), Long.MAX_VALUE, false);
        HLogger.brightBlue(4, new Dummy(), Long.MIN_VALUE, true);
        HLogger.brightBlue(4, new Dummy(), Long.MIN_VALUE, false);
        HLogger.brightBlue(4, new Dummy(), 0, true);
        HLogger.brightBlue(4, new Dummy(), 0, false);
        HLogger.brightBlue(4, new Dummy2(), System.currentTimeMillis(), true);
        HLogger.brightBlue(4, new Dummy2(), System.currentTimeMillis(), false);
        HLogger.brightBlue(4, new Dummy2(), System.nanoTime(), true);
        HLogger.brightBlue(4, new Dummy2(), System.nanoTime(), false);
        HLogger.brightBlue(4, new Dummy2(), Long.MAX_VALUE, true);
        HLogger.brightBlue(4, new Dummy2(), Long.MAX_VALUE, false);
        HLogger.brightBlue(4, new Dummy2(), Long.MIN_VALUE, true);
        HLogger.brightBlue(4, new Dummy2(), Long.MIN_VALUE, false);
        HLogger.brightBlue(4, new Dummy2(), 0, true);
        HLogger.brightBlue(4, new Dummy2(), 0, false);
        HLogger.brightBlue(4, (Object) null, System.currentTimeMillis(), true);
        HLogger.brightBlue(4, (Object) null, System.currentTimeMillis(), false);
        HLogger.brightBlue(4, (Object) null, System.nanoTime(), true);
        HLogger.brightBlue(4, (Object) null, System.nanoTime(), false);
        HLogger.brightBlue(4, (Object) null, Long.MAX_VALUE, true);
        HLogger.brightBlue(4, (Object) null, Long.MAX_VALUE, false);
        HLogger.brightBlue(4, (Object) null, Long.MIN_VALUE, true);
        HLogger.brightBlue(4, (Object) null, Long.MIN_VALUE, false);
        HLogger.brightBlue(4, (Object) null, 0, true);
    }

    
    //! $CHALK_END
}
    
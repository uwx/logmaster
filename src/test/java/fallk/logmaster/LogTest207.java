
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest207 {

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
    public final void test_red_int_Object_long() {
        HLogger.red(0, new Object(), System.currentTimeMillis());
        HLogger.red(0, new Object(), System.nanoTime());
        HLogger.red(0, new Object(), Long.MAX_VALUE);
        HLogger.red(0, new Object(), Long.MIN_VALUE);
        HLogger.red(0, new Object(), 0);
        HLogger.red(0, new Dummy(), System.currentTimeMillis());
        HLogger.red(0, new Dummy(), System.nanoTime());
        HLogger.red(0, new Dummy(), Long.MAX_VALUE);
        HLogger.red(0, new Dummy(), Long.MIN_VALUE);
        HLogger.red(0, new Dummy(), 0);
        HLogger.red(0, new Dummy2(), System.currentTimeMillis());
        HLogger.red(0, new Dummy2(), System.nanoTime());
        HLogger.red(0, new Dummy2(), Long.MAX_VALUE);
        HLogger.red(0, new Dummy2(), Long.MIN_VALUE);
        HLogger.red(0, new Dummy2(), 0);
        HLogger.red(0, (Object) null, System.currentTimeMillis());
        HLogger.red(0, (Object) null, System.nanoTime());
        HLogger.red(0, (Object) null, Long.MAX_VALUE);
        HLogger.red(0, (Object) null, Long.MIN_VALUE);
        HLogger.red(0, (Object) null, 0);
        HLogger.red(1, new Object(), System.currentTimeMillis());
        HLogger.red(1, new Object(), System.nanoTime());
        HLogger.red(1, new Object(), Long.MAX_VALUE);
        HLogger.red(1, new Object(), Long.MIN_VALUE);
        HLogger.red(1, new Object(), 0);
        HLogger.red(1, new Dummy(), System.currentTimeMillis());
        HLogger.red(1, new Dummy(), System.nanoTime());
        HLogger.red(1, new Dummy(), Long.MAX_VALUE);
        HLogger.red(1, new Dummy(), Long.MIN_VALUE);
        HLogger.red(1, new Dummy(), 0);
        HLogger.red(1, new Dummy2(), System.currentTimeMillis());
        HLogger.red(1, new Dummy2(), System.nanoTime());
        HLogger.red(1, new Dummy2(), Long.MAX_VALUE);
        HLogger.red(1, new Dummy2(), Long.MIN_VALUE);
        HLogger.red(1, new Dummy2(), 0);
        HLogger.red(1, (Object) null, System.currentTimeMillis());
        HLogger.red(1, (Object) null, System.nanoTime());
        HLogger.red(1, (Object) null, Long.MAX_VALUE);
        HLogger.red(1, (Object) null, Long.MIN_VALUE);
        HLogger.red(1, (Object) null, 0);
        HLogger.red(2, new Object(), System.currentTimeMillis());
        HLogger.red(2, new Object(), System.nanoTime());
        HLogger.red(2, new Object(), Long.MAX_VALUE);
        HLogger.red(2, new Object(), Long.MIN_VALUE);
        HLogger.red(2, new Object(), 0);
        HLogger.red(2, new Dummy(), System.currentTimeMillis());
        HLogger.red(2, new Dummy(), System.nanoTime());
        HLogger.red(2, new Dummy(), Long.MAX_VALUE);
        HLogger.red(2, new Dummy(), Long.MIN_VALUE);
        HLogger.red(2, new Dummy(), 0);
        HLogger.red(2, new Dummy2(), System.currentTimeMillis());
        HLogger.red(2, new Dummy2(), System.nanoTime());
        HLogger.red(2, new Dummy2(), Long.MAX_VALUE);
        HLogger.red(2, new Dummy2(), Long.MIN_VALUE);
        HLogger.red(2, new Dummy2(), 0);
        HLogger.red(2, (Object) null, System.currentTimeMillis());
        HLogger.red(2, (Object) null, System.nanoTime());
        HLogger.red(2, (Object) null, Long.MAX_VALUE);
        HLogger.red(2, (Object) null, Long.MIN_VALUE);
        HLogger.red(2, (Object) null, 0);
        HLogger.red(3, new Object(), System.currentTimeMillis());
        HLogger.red(3, new Object(), System.nanoTime());
        HLogger.red(3, new Object(), Long.MAX_VALUE);
        HLogger.red(3, new Object(), Long.MIN_VALUE);
        HLogger.red(3, new Object(), 0);
        HLogger.red(3, new Dummy(), System.currentTimeMillis());
        HLogger.red(3, new Dummy(), System.nanoTime());
        HLogger.red(3, new Dummy(), Long.MAX_VALUE);
        HLogger.red(3, new Dummy(), Long.MIN_VALUE);
        HLogger.red(3, new Dummy(), 0);
        HLogger.red(3, new Dummy2(), System.currentTimeMillis());
        HLogger.red(3, new Dummy2(), System.nanoTime());
        HLogger.red(3, new Dummy2(), Long.MAX_VALUE);
        HLogger.red(3, new Dummy2(), Long.MIN_VALUE);
        HLogger.red(3, new Dummy2(), 0);
        HLogger.red(3, (Object) null, System.currentTimeMillis());
        HLogger.red(3, (Object) null, System.nanoTime());
        HLogger.red(3, (Object) null, Long.MAX_VALUE);
        HLogger.red(3, (Object) null, Long.MIN_VALUE);
        HLogger.red(3, (Object) null, 0);
        HLogger.red(4, new Object(), System.currentTimeMillis());
        HLogger.red(4, new Object(), System.nanoTime());
        HLogger.red(4, new Object(), Long.MAX_VALUE);
        HLogger.red(4, new Object(), Long.MIN_VALUE);
        HLogger.red(4, new Object(), 0);
        HLogger.red(4, new Dummy(), System.currentTimeMillis());
        HLogger.red(4, new Dummy(), System.nanoTime());
        HLogger.red(4, new Dummy(), Long.MAX_VALUE);
        HLogger.red(4, new Dummy(), Long.MIN_VALUE);
        HLogger.red(4, new Dummy(), 0);
        HLogger.red(4, new Dummy2(), System.currentTimeMillis());
        HLogger.red(4, new Dummy2(), System.nanoTime());
        HLogger.red(4, new Dummy2(), Long.MAX_VALUE);
        HLogger.red(4, new Dummy2(), Long.MIN_VALUE);
        HLogger.red(4, new Dummy2(), 0);
        HLogger.red(4, (Object) null, System.currentTimeMillis());
        HLogger.red(4, (Object) null, System.nanoTime());
        HLogger.red(4, (Object) null, Long.MAX_VALUE);
        HLogger.red(4, (Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
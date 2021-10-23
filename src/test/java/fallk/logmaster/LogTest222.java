
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest222 {

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
    public final void test_brightRed_int_Object_long() {
        HLogger.brightRed(0, new Object(), System.currentTimeMillis());
        HLogger.brightRed(0, new Object(), System.nanoTime());
        HLogger.brightRed(0, new Object(), Long.MAX_VALUE);
        HLogger.brightRed(0, new Object(), Long.MIN_VALUE);
        HLogger.brightRed(0, new Object(), 0);
        HLogger.brightRed(0, new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(0, new Dummy(), System.nanoTime());
        HLogger.brightRed(0, new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(0, new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(0, new Dummy(), 0);
        HLogger.brightRed(0, new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(0, new Dummy2(), System.nanoTime());
        HLogger.brightRed(0, new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(0, new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(0, new Dummy2(), 0);
        HLogger.brightRed(0, (Object) null, System.currentTimeMillis());
        HLogger.brightRed(0, (Object) null, System.nanoTime());
        HLogger.brightRed(0, (Object) null, Long.MAX_VALUE);
        HLogger.brightRed(0, (Object) null, Long.MIN_VALUE);
        HLogger.brightRed(0, (Object) null, 0);
        HLogger.brightRed(1, new Object(), System.currentTimeMillis());
        HLogger.brightRed(1, new Object(), System.nanoTime());
        HLogger.brightRed(1, new Object(), Long.MAX_VALUE);
        HLogger.brightRed(1, new Object(), Long.MIN_VALUE);
        HLogger.brightRed(1, new Object(), 0);
        HLogger.brightRed(1, new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(1, new Dummy(), System.nanoTime());
        HLogger.brightRed(1, new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(1, new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(1, new Dummy(), 0);
        HLogger.brightRed(1, new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(1, new Dummy2(), System.nanoTime());
        HLogger.brightRed(1, new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(1, new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(1, new Dummy2(), 0);
        HLogger.brightRed(1, (Object) null, System.currentTimeMillis());
        HLogger.brightRed(1, (Object) null, System.nanoTime());
        HLogger.brightRed(1, (Object) null, Long.MAX_VALUE);
        HLogger.brightRed(1, (Object) null, Long.MIN_VALUE);
        HLogger.brightRed(1, (Object) null, 0);
        HLogger.brightRed(2, new Object(), System.currentTimeMillis());
        HLogger.brightRed(2, new Object(), System.nanoTime());
        HLogger.brightRed(2, new Object(), Long.MAX_VALUE);
        HLogger.brightRed(2, new Object(), Long.MIN_VALUE);
        HLogger.brightRed(2, new Object(), 0);
        HLogger.brightRed(2, new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(2, new Dummy(), System.nanoTime());
        HLogger.brightRed(2, new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(2, new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(2, new Dummy(), 0);
        HLogger.brightRed(2, new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(2, new Dummy2(), System.nanoTime());
        HLogger.brightRed(2, new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(2, new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(2, new Dummy2(), 0);
        HLogger.brightRed(2, (Object) null, System.currentTimeMillis());
        HLogger.brightRed(2, (Object) null, System.nanoTime());
        HLogger.brightRed(2, (Object) null, Long.MAX_VALUE);
        HLogger.brightRed(2, (Object) null, Long.MIN_VALUE);
        HLogger.brightRed(2, (Object) null, 0);
        HLogger.brightRed(3, new Object(), System.currentTimeMillis());
        HLogger.brightRed(3, new Object(), System.nanoTime());
        HLogger.brightRed(3, new Object(), Long.MAX_VALUE);
        HLogger.brightRed(3, new Object(), Long.MIN_VALUE);
        HLogger.brightRed(3, new Object(), 0);
        HLogger.brightRed(3, new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(3, new Dummy(), System.nanoTime());
        HLogger.brightRed(3, new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(3, new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(3, new Dummy(), 0);
        HLogger.brightRed(3, new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(3, new Dummy2(), System.nanoTime());
        HLogger.brightRed(3, new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(3, new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(3, new Dummy2(), 0);
        HLogger.brightRed(3, (Object) null, System.currentTimeMillis());
        HLogger.brightRed(3, (Object) null, System.nanoTime());
        HLogger.brightRed(3, (Object) null, Long.MAX_VALUE);
        HLogger.brightRed(3, (Object) null, Long.MIN_VALUE);
        HLogger.brightRed(3, (Object) null, 0);
        HLogger.brightRed(4, new Object(), System.currentTimeMillis());
        HLogger.brightRed(4, new Object(), System.nanoTime());
        HLogger.brightRed(4, new Object(), Long.MAX_VALUE);
        HLogger.brightRed(4, new Object(), Long.MIN_VALUE);
        HLogger.brightRed(4, new Object(), 0);
        HLogger.brightRed(4, new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(4, new Dummy(), System.nanoTime());
        HLogger.brightRed(4, new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(4, new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(4, new Dummy(), 0);
        HLogger.brightRed(4, new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(4, new Dummy2(), System.nanoTime());
        HLogger.brightRed(4, new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(4, new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(4, new Dummy2(), 0);
        HLogger.brightRed(4, (Object) null, System.currentTimeMillis());
        HLogger.brightRed(4, (Object) null, System.nanoTime());
        HLogger.brightRed(4, (Object) null, Long.MAX_VALUE);
        HLogger.brightRed(4, (Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest132 {

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
    public final void test_blue_int_Object_long() {
        HLogger.blue(0, new Object(), System.currentTimeMillis());
        HLogger.blue(0, new Object(), System.nanoTime());
        HLogger.blue(0, new Object(), Long.MAX_VALUE);
        HLogger.blue(0, new Object(), Long.MIN_VALUE);
        HLogger.blue(0, new Object(), 0);
        HLogger.blue(0, new Dummy(), System.currentTimeMillis());
        HLogger.blue(0, new Dummy(), System.nanoTime());
        HLogger.blue(0, new Dummy(), Long.MAX_VALUE);
        HLogger.blue(0, new Dummy(), Long.MIN_VALUE);
        HLogger.blue(0, new Dummy(), 0);
        HLogger.blue(0, new Dummy2(), System.currentTimeMillis());
        HLogger.blue(0, new Dummy2(), System.nanoTime());
        HLogger.blue(0, new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(0, new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(0, new Dummy2(), 0);
        HLogger.blue(0, (Object) null, System.currentTimeMillis());
        HLogger.blue(0, (Object) null, System.nanoTime());
        HLogger.blue(0, (Object) null, Long.MAX_VALUE);
        HLogger.blue(0, (Object) null, Long.MIN_VALUE);
        HLogger.blue(0, (Object) null, 0);
        HLogger.blue(1, new Object(), System.currentTimeMillis());
        HLogger.blue(1, new Object(), System.nanoTime());
        HLogger.blue(1, new Object(), Long.MAX_VALUE);
        HLogger.blue(1, new Object(), Long.MIN_VALUE);
        HLogger.blue(1, new Object(), 0);
        HLogger.blue(1, new Dummy(), System.currentTimeMillis());
        HLogger.blue(1, new Dummy(), System.nanoTime());
        HLogger.blue(1, new Dummy(), Long.MAX_VALUE);
        HLogger.blue(1, new Dummy(), Long.MIN_VALUE);
        HLogger.blue(1, new Dummy(), 0);
        HLogger.blue(1, new Dummy2(), System.currentTimeMillis());
        HLogger.blue(1, new Dummy2(), System.nanoTime());
        HLogger.blue(1, new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(1, new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(1, new Dummy2(), 0);
        HLogger.blue(1, (Object) null, System.currentTimeMillis());
        HLogger.blue(1, (Object) null, System.nanoTime());
        HLogger.blue(1, (Object) null, Long.MAX_VALUE);
        HLogger.blue(1, (Object) null, Long.MIN_VALUE);
        HLogger.blue(1, (Object) null, 0);
        HLogger.blue(2, new Object(), System.currentTimeMillis());
        HLogger.blue(2, new Object(), System.nanoTime());
        HLogger.blue(2, new Object(), Long.MAX_VALUE);
        HLogger.blue(2, new Object(), Long.MIN_VALUE);
        HLogger.blue(2, new Object(), 0);
        HLogger.blue(2, new Dummy(), System.currentTimeMillis());
        HLogger.blue(2, new Dummy(), System.nanoTime());
        HLogger.blue(2, new Dummy(), Long.MAX_VALUE);
        HLogger.blue(2, new Dummy(), Long.MIN_VALUE);
        HLogger.blue(2, new Dummy(), 0);
        HLogger.blue(2, new Dummy2(), System.currentTimeMillis());
        HLogger.blue(2, new Dummy2(), System.nanoTime());
        HLogger.blue(2, new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(2, new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(2, new Dummy2(), 0);
        HLogger.blue(2, (Object) null, System.currentTimeMillis());
        HLogger.blue(2, (Object) null, System.nanoTime());
        HLogger.blue(2, (Object) null, Long.MAX_VALUE);
        HLogger.blue(2, (Object) null, Long.MIN_VALUE);
        HLogger.blue(2, (Object) null, 0);
        HLogger.blue(3, new Object(), System.currentTimeMillis());
        HLogger.blue(3, new Object(), System.nanoTime());
        HLogger.blue(3, new Object(), Long.MAX_VALUE);
        HLogger.blue(3, new Object(), Long.MIN_VALUE);
        HLogger.blue(3, new Object(), 0);
        HLogger.blue(3, new Dummy(), System.currentTimeMillis());
        HLogger.blue(3, new Dummy(), System.nanoTime());
        HLogger.blue(3, new Dummy(), Long.MAX_VALUE);
        HLogger.blue(3, new Dummy(), Long.MIN_VALUE);
        HLogger.blue(3, new Dummy(), 0);
        HLogger.blue(3, new Dummy2(), System.currentTimeMillis());
        HLogger.blue(3, new Dummy2(), System.nanoTime());
        HLogger.blue(3, new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(3, new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(3, new Dummy2(), 0);
        HLogger.blue(3, (Object) null, System.currentTimeMillis());
        HLogger.blue(3, (Object) null, System.nanoTime());
        HLogger.blue(3, (Object) null, Long.MAX_VALUE);
        HLogger.blue(3, (Object) null, Long.MIN_VALUE);
        HLogger.blue(3, (Object) null, 0);
        HLogger.blue(4, new Object(), System.currentTimeMillis());
        HLogger.blue(4, new Object(), System.nanoTime());
        HLogger.blue(4, new Object(), Long.MAX_VALUE);
        HLogger.blue(4, new Object(), Long.MIN_VALUE);
        HLogger.blue(4, new Object(), 0);
        HLogger.blue(4, new Dummy(), System.currentTimeMillis());
        HLogger.blue(4, new Dummy(), System.nanoTime());
        HLogger.blue(4, new Dummy(), Long.MAX_VALUE);
        HLogger.blue(4, new Dummy(), Long.MIN_VALUE);
        HLogger.blue(4, new Dummy(), 0);
        HLogger.blue(4, new Dummy2(), System.currentTimeMillis());
        HLogger.blue(4, new Dummy2(), System.nanoTime());
        HLogger.blue(4, new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(4, new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(4, new Dummy2(), 0);
        HLogger.blue(4, (Object) null, System.currentTimeMillis());
        HLogger.blue(4, (Object) null, System.nanoTime());
        HLogger.blue(4, (Object) null, Long.MAX_VALUE);
        HLogger.blue(4, (Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
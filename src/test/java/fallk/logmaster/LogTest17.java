
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest17 {

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
    public final void test_error_Object_long_boolean() {
        HLogger.error(new Object(), System.currentTimeMillis(), true);
        HLogger.error(new Object(), System.currentTimeMillis(), false);
        HLogger.error(new Object(), System.nanoTime(), true);
        HLogger.error(new Object(), System.nanoTime(), false);
        HLogger.error(new Object(), Long.MAX_VALUE, true);
        HLogger.error(new Object(), Long.MAX_VALUE, false);
        HLogger.error(new Object(), Long.MIN_VALUE, true);
        HLogger.error(new Object(), Long.MIN_VALUE, false);
        HLogger.error(new Object(), 0, true);
        HLogger.error(new Object(), 0, false);
        HLogger.error(new Dummy(), System.currentTimeMillis(), true);
        HLogger.error(new Dummy(), System.currentTimeMillis(), false);
        HLogger.error(new Dummy(), System.nanoTime(), true);
        HLogger.error(new Dummy(), System.nanoTime(), false);
        HLogger.error(new Dummy(), Long.MAX_VALUE, true);
        HLogger.error(new Dummy(), Long.MAX_VALUE, false);
        HLogger.error(new Dummy(), Long.MIN_VALUE, true);
        HLogger.error(new Dummy(), Long.MIN_VALUE, false);
        HLogger.error(new Dummy(), 0, true);
        HLogger.error(new Dummy(), 0, false);
        HLogger.error(new Dummy2(), System.currentTimeMillis(), true);
        HLogger.error(new Dummy2(), System.currentTimeMillis(), false);
        HLogger.error(new Dummy2(), System.nanoTime(), true);
        HLogger.error(new Dummy2(), System.nanoTime(), false);
        HLogger.error(new Dummy2(), Long.MAX_VALUE, true);
        HLogger.error(new Dummy2(), Long.MAX_VALUE, false);
        HLogger.error(new Dummy2(), Long.MIN_VALUE, true);
        HLogger.error(new Dummy2(), Long.MIN_VALUE, false);
        HLogger.error(new Dummy2(), 0, true);
        HLogger.error(new Dummy2(), 0, false);
        HLogger.error((Object) null, System.currentTimeMillis(), true);
        HLogger.error((Object) null, System.currentTimeMillis(), false);
        HLogger.error((Object) null, System.nanoTime(), true);
        HLogger.error((Object) null, System.nanoTime(), false);
        HLogger.error((Object) null, Long.MAX_VALUE, true);
        HLogger.error((Object) null, Long.MAX_VALUE, false);
        HLogger.error((Object) null, Long.MIN_VALUE, true);
        HLogger.error((Object) null, Long.MIN_VALUE, false);
        HLogger.error((Object) null, 0, true);
    }

    
    //! $CHALK_END
}
    
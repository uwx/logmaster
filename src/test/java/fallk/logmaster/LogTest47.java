
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest47 {

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
    public final void test_warn_Object_long() {
        HLogger.warn(new Object(), System.currentTimeMillis());
        HLogger.warn(new Object(), System.nanoTime());
        HLogger.warn(new Object(), Long.MAX_VALUE);
        HLogger.warn(new Object(), Long.MIN_VALUE);
        HLogger.warn(new Object(), 0);
        HLogger.warn(new Dummy(), System.currentTimeMillis());
        HLogger.warn(new Dummy(), System.nanoTime());
        HLogger.warn(new Dummy(), Long.MAX_VALUE);
        HLogger.warn(new Dummy(), Long.MIN_VALUE);
        HLogger.warn(new Dummy(), 0);
        HLogger.warn(new Dummy2(), System.currentTimeMillis());
        HLogger.warn(new Dummy2(), System.nanoTime());
        HLogger.warn(new Dummy2(), Long.MAX_VALUE);
        HLogger.warn(new Dummy2(), Long.MIN_VALUE);
        HLogger.warn(new Dummy2(), 0);
        HLogger.warn((Object) null, System.currentTimeMillis());
        HLogger.warn((Object) null, System.nanoTime());
        HLogger.warn((Object) null, Long.MAX_VALUE);
        HLogger.warn((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
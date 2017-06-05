
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest80 {

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
    public final void test_reset_Object_long() {
        HLogger.reset(new Object(), System.currentTimeMillis());
        HLogger.reset(new Object(), System.nanoTime());
        HLogger.reset(new Object(), Long.MAX_VALUE);
        HLogger.reset(new Object(), Long.MIN_VALUE);
        HLogger.reset(new Object(), 0);
        HLogger.reset(new Dummy(), System.currentTimeMillis());
        HLogger.reset(new Dummy(), System.nanoTime());
        HLogger.reset(new Dummy(), Long.MAX_VALUE);
        HLogger.reset(new Dummy(), Long.MIN_VALUE);
        HLogger.reset(new Dummy(), 0);
        HLogger.reset(new Dummy2(), System.currentTimeMillis());
        HLogger.reset(new Dummy2(), System.nanoTime());
        HLogger.reset(new Dummy2(), Long.MAX_VALUE);
        HLogger.reset(new Dummy2(), Long.MIN_VALUE);
        HLogger.reset(new Dummy2(), 0);
        HLogger.reset((Object) null, System.currentTimeMillis());
        HLogger.reset((Object) null, System.nanoTime());
        HLogger.reset((Object) null, Long.MAX_VALUE);
        HLogger.reset((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
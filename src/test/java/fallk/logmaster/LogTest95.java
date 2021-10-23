
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest95 {

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
    public final void test_red_Object_long() {
        HLogger.red(new Object(), System.currentTimeMillis());
        HLogger.red(new Object(), System.nanoTime());
        HLogger.red(new Object(), Long.MAX_VALUE);
        HLogger.red(new Object(), Long.MIN_VALUE);
        HLogger.red(new Object(), 0);
        HLogger.red(new Dummy(), System.currentTimeMillis());
        HLogger.red(new Dummy(), System.nanoTime());
        HLogger.red(new Dummy(), Long.MAX_VALUE);
        HLogger.red(new Dummy(), Long.MIN_VALUE);
        HLogger.red(new Dummy(), 0);
        HLogger.red(new Dummy2(), System.currentTimeMillis());
        HLogger.red(new Dummy2(), System.nanoTime());
        HLogger.red(new Dummy2(), Long.MAX_VALUE);
        HLogger.red(new Dummy2(), Long.MIN_VALUE);
        HLogger.red(new Dummy2(), 0);
        HLogger.red((Object) null, System.currentTimeMillis());
        HLogger.red((Object) null, System.nanoTime());
        HLogger.red((Object) null, Long.MAX_VALUE);
        HLogger.red((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
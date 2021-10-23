
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest185 {

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
    public final void test_white_Object_long() {
        HLogger.white(new Object(), System.currentTimeMillis());
        HLogger.white(new Object(), System.nanoTime());
        HLogger.white(new Object(), Long.MAX_VALUE);
        HLogger.white(new Object(), Long.MIN_VALUE);
        HLogger.white(new Object(), 0);
        HLogger.white(new Dummy(), System.currentTimeMillis());
        HLogger.white(new Dummy(), System.nanoTime());
        HLogger.white(new Dummy(), Long.MAX_VALUE);
        HLogger.white(new Dummy(), Long.MIN_VALUE);
        HLogger.white(new Dummy(), 0);
        HLogger.white(new Dummy2(), System.currentTimeMillis());
        HLogger.white(new Dummy2(), System.nanoTime());
        HLogger.white(new Dummy2(), Long.MAX_VALUE);
        HLogger.white(new Dummy2(), Long.MIN_VALUE);
        HLogger.white(new Dummy2(), 0);
        HLogger.white((Object) null, System.currentTimeMillis());
        HLogger.white((Object) null, System.nanoTime());
        HLogger.white((Object) null, Long.MAX_VALUE);
        HLogger.white((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
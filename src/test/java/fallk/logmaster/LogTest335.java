
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest335 {

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
    public final void test_brightBlack_Object_long() {
        HLogger.brightBlack(new Object(), System.currentTimeMillis());
        HLogger.brightBlack(new Object(), System.nanoTime());
        HLogger.brightBlack(new Object(), Long.MAX_VALUE);
        HLogger.brightBlack(new Object(), Long.MIN_VALUE);
        HLogger.brightBlack(new Object(), 0);
        HLogger.brightBlack(new Dummy(), System.currentTimeMillis());
        HLogger.brightBlack(new Dummy(), System.nanoTime());
        HLogger.brightBlack(new Dummy(), Long.MAX_VALUE);
        HLogger.brightBlack(new Dummy(), Long.MIN_VALUE);
        HLogger.brightBlack(new Dummy(), 0);
        HLogger.brightBlack(new Dummy2(), System.currentTimeMillis());
        HLogger.brightBlack(new Dummy2(), System.nanoTime());
        HLogger.brightBlack(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightBlack(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightBlack(new Dummy2(), 0);
        HLogger.brightBlack((Object) null, System.currentTimeMillis());
        HLogger.brightBlack((Object) null, System.nanoTime());
        HLogger.brightBlack((Object) null, Long.MAX_VALUE);
        HLogger.brightBlack((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
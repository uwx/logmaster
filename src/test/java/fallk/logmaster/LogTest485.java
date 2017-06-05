
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest485 {

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
    public final void test_bgBlack_Object_long() {
        HLogger.bgBlack(new Object(), System.currentTimeMillis());
        HLogger.bgBlack(new Object(), System.nanoTime());
        HLogger.bgBlack(new Object(), Long.MAX_VALUE);
        HLogger.bgBlack(new Object(), Long.MIN_VALUE);
        HLogger.bgBlack(new Object(), 0);
        HLogger.bgBlack(new Dummy(), System.currentTimeMillis());
        HLogger.bgBlack(new Dummy(), System.nanoTime());
        HLogger.bgBlack(new Dummy(), Long.MAX_VALUE);
        HLogger.bgBlack(new Dummy(), Long.MIN_VALUE);
        HLogger.bgBlack(new Dummy(), 0);
        HLogger.bgBlack(new Dummy2(), System.currentTimeMillis());
        HLogger.bgBlack(new Dummy2(), System.nanoTime());
        HLogger.bgBlack(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgBlack(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgBlack(new Dummy2(), 0);
        HLogger.bgBlack((Object) null, System.currentTimeMillis());
        HLogger.bgBlack((Object) null, System.nanoTime());
        HLogger.bgBlack((Object) null, Long.MAX_VALUE);
        HLogger.bgBlack((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
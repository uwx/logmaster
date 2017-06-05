
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest500 {

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
    public final void test_bgRed_Object_long() {
        HLogger.bgRed(new Object(), System.currentTimeMillis());
        HLogger.bgRed(new Object(), System.nanoTime());
        HLogger.bgRed(new Object(), Long.MAX_VALUE);
        HLogger.bgRed(new Object(), Long.MIN_VALUE);
        HLogger.bgRed(new Object(), 0);
        HLogger.bgRed(new Dummy(), System.currentTimeMillis());
        HLogger.bgRed(new Dummy(), System.nanoTime());
        HLogger.bgRed(new Dummy(), Long.MAX_VALUE);
        HLogger.bgRed(new Dummy(), Long.MIN_VALUE);
        HLogger.bgRed(new Dummy(), 0);
        HLogger.bgRed(new Dummy2(), System.currentTimeMillis());
        HLogger.bgRed(new Dummy2(), System.nanoTime());
        HLogger.bgRed(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgRed(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgRed(new Dummy2(), 0);
        HLogger.bgRed((Object) null, System.currentTimeMillis());
        HLogger.bgRed((Object) null, System.nanoTime());
        HLogger.bgRed((Object) null, Long.MAX_VALUE);
        HLogger.bgRed((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
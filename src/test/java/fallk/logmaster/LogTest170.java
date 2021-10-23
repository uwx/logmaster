
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest170 {

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
    public final void test_cyan_Object_long() {
        HLogger.cyan(new Object(), System.currentTimeMillis());
        HLogger.cyan(new Object(), System.nanoTime());
        HLogger.cyan(new Object(), Long.MAX_VALUE);
        HLogger.cyan(new Object(), Long.MIN_VALUE);
        HLogger.cyan(new Object(), 0);
        HLogger.cyan(new Dummy(), System.currentTimeMillis());
        HLogger.cyan(new Dummy(), System.nanoTime());
        HLogger.cyan(new Dummy(), Long.MAX_VALUE);
        HLogger.cyan(new Dummy(), Long.MIN_VALUE);
        HLogger.cyan(new Dummy(), 0);
        HLogger.cyan(new Dummy2(), System.currentTimeMillis());
        HLogger.cyan(new Dummy2(), System.nanoTime());
        HLogger.cyan(new Dummy2(), Long.MAX_VALUE);
        HLogger.cyan(new Dummy2(), Long.MIN_VALUE);
        HLogger.cyan(new Dummy2(), 0);
        HLogger.cyan((Object) null, System.currentTimeMillis());
        HLogger.cyan((Object) null, System.nanoTime());
        HLogger.cyan((Object) null, Long.MAX_VALUE);
        HLogger.cyan((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
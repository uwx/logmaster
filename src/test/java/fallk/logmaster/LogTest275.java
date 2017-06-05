
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest275 {

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
    public final void test_magenta_Object_long() {
        HLogger.magenta(new Object(), System.currentTimeMillis());
        HLogger.magenta(new Object(), System.nanoTime());
        HLogger.magenta(new Object(), Long.MAX_VALUE);
        HLogger.magenta(new Object(), Long.MIN_VALUE);
        HLogger.magenta(new Object(), 0);
        HLogger.magenta(new Dummy(), System.currentTimeMillis());
        HLogger.magenta(new Dummy(), System.nanoTime());
        HLogger.magenta(new Dummy(), Long.MAX_VALUE);
        HLogger.magenta(new Dummy(), Long.MIN_VALUE);
        HLogger.magenta(new Dummy(), 0);
        HLogger.magenta(new Dummy2(), System.currentTimeMillis());
        HLogger.magenta(new Dummy2(), System.nanoTime());
        HLogger.magenta(new Dummy2(), Long.MAX_VALUE);
        HLogger.magenta(new Dummy2(), Long.MIN_VALUE);
        HLogger.magenta(new Dummy2(), 0);
        HLogger.magenta((Object) null, System.currentTimeMillis());
        HLogger.magenta((Object) null, System.nanoTime());
        HLogger.magenta((Object) null, Long.MAX_VALUE);
        HLogger.magenta((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
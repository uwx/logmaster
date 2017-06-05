
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest20 {

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
    public final void test_error_Object_long() {
        HLogger.error(new Object(), System.currentTimeMillis());
        HLogger.error(new Object(), System.nanoTime());
        HLogger.error(new Object(), Long.MAX_VALUE);
        HLogger.error(new Object(), Long.MIN_VALUE);
        HLogger.error(new Object(), 0);
        HLogger.error(new Dummy(), System.currentTimeMillis());
        HLogger.error(new Dummy(), System.nanoTime());
        HLogger.error(new Dummy(), Long.MAX_VALUE);
        HLogger.error(new Dummy(), Long.MIN_VALUE);
        HLogger.error(new Dummy(), 0);
        HLogger.error(new Dummy2(), System.currentTimeMillis());
        HLogger.error(new Dummy2(), System.nanoTime());
        HLogger.error(new Dummy2(), Long.MAX_VALUE);
        HLogger.error(new Dummy2(), Long.MIN_VALUE);
        HLogger.error(new Dummy2(), 0);
        HLogger.error((Object) null, System.currentTimeMillis());
        HLogger.error((Object) null, System.nanoTime());
        HLogger.error((Object) null, Long.MAX_VALUE);
        HLogger.error((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
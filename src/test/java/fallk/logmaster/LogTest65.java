
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest65 {

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
    public final void test_debug_Object_long() {
        HLogger.debug(new Object(), System.currentTimeMillis());
        HLogger.debug(new Object(), System.nanoTime());
        HLogger.debug(new Object(), Long.MAX_VALUE);
        HLogger.debug(new Object(), Long.MIN_VALUE);
        HLogger.debug(new Object(), 0);
        HLogger.debug(new Dummy(), System.currentTimeMillis());
        HLogger.debug(new Dummy(), System.nanoTime());
        HLogger.debug(new Dummy(), Long.MAX_VALUE);
        HLogger.debug(new Dummy(), Long.MIN_VALUE);
        HLogger.debug(new Dummy(), 0);
        HLogger.debug(new Dummy2(), System.currentTimeMillis());
        HLogger.debug(new Dummy2(), System.nanoTime());
        HLogger.debug(new Dummy2(), Long.MAX_VALUE);
        HLogger.debug(new Dummy2(), Long.MIN_VALUE);
        HLogger.debug(new Dummy2(), 0);
        HLogger.debug((Object) null, System.currentTimeMillis());
        HLogger.debug((Object) null, System.nanoTime());
        HLogger.debug((Object) null, Long.MAX_VALUE);
        HLogger.debug((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
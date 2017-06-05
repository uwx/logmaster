
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest62 {

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
    public final void test_debug_Object_long_boolean() {
        HLogger.debug(new Object(), System.currentTimeMillis(), true);
        HLogger.debug(new Object(), System.currentTimeMillis(), false);
        HLogger.debug(new Object(), System.nanoTime(), true);
        HLogger.debug(new Object(), System.nanoTime(), false);
        HLogger.debug(new Object(), Long.MAX_VALUE, true);
        HLogger.debug(new Object(), Long.MAX_VALUE, false);
        HLogger.debug(new Object(), Long.MIN_VALUE, true);
        HLogger.debug(new Object(), Long.MIN_VALUE, false);
        HLogger.debug(new Object(), 0, true);
        HLogger.debug(new Object(), 0, false);
        HLogger.debug(new Dummy(), System.currentTimeMillis(), true);
        HLogger.debug(new Dummy(), System.currentTimeMillis(), false);
        HLogger.debug(new Dummy(), System.nanoTime(), true);
        HLogger.debug(new Dummy(), System.nanoTime(), false);
        HLogger.debug(new Dummy(), Long.MAX_VALUE, true);
        HLogger.debug(new Dummy(), Long.MAX_VALUE, false);
        HLogger.debug(new Dummy(), Long.MIN_VALUE, true);
        HLogger.debug(new Dummy(), Long.MIN_VALUE, false);
        HLogger.debug(new Dummy(), 0, true);
        HLogger.debug(new Dummy(), 0, false);
        HLogger.debug(new Dummy2(), System.currentTimeMillis(), true);
        HLogger.debug(new Dummy2(), System.currentTimeMillis(), false);
        HLogger.debug(new Dummy2(), System.nanoTime(), true);
        HLogger.debug(new Dummy2(), System.nanoTime(), false);
        HLogger.debug(new Dummy2(), Long.MAX_VALUE, true);
        HLogger.debug(new Dummy2(), Long.MAX_VALUE, false);
        HLogger.debug(new Dummy2(), Long.MIN_VALUE, true);
        HLogger.debug(new Dummy2(), Long.MIN_VALUE, false);
        HLogger.debug(new Dummy2(), 0, true);
        HLogger.debug(new Dummy2(), 0, false);
        HLogger.debug((Object) null, System.currentTimeMillis(), true);
        HLogger.debug((Object) null, System.currentTimeMillis(), false);
        HLogger.debug((Object) null, System.nanoTime(), true);
        HLogger.debug((Object) null, System.nanoTime(), false);
        HLogger.debug((Object) null, Long.MAX_VALUE, true);
        HLogger.debug((Object) null, Long.MAX_VALUE, false);
        HLogger.debug((Object) null, Long.MIN_VALUE, true);
        HLogger.debug((Object) null, Long.MIN_VALUE, false);
        HLogger.debug((Object) null, 0, true);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest61 {

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
    public final void test_debug_String_long_boolean() {
        HLogger.debug("Test string", System.currentTimeMillis(), true);
        HLogger.debug("Test string", System.currentTimeMillis(), false);
        HLogger.debug("Test string", System.nanoTime(), true);
        HLogger.debug("Test string", System.nanoTime(), false);
        HLogger.debug("Test string", Long.MAX_VALUE, true);
        HLogger.debug("Test string", Long.MAX_VALUE, false);
        HLogger.debug("Test string", Long.MIN_VALUE, true);
        HLogger.debug("Test string", Long.MIN_VALUE, false);
        HLogger.debug("Test string", 0, true);
        HLogger.debug("Test string", 0, false);
        HLogger.debug((String) null, System.currentTimeMillis(), true);
        HLogger.debug((String) null, System.currentTimeMillis(), false);
        HLogger.debug((String) null, System.nanoTime(), true);
        HLogger.debug((String) null, System.nanoTime(), false);
        HLogger.debug((String) null, Long.MAX_VALUE, true);
        HLogger.debug((String) null, Long.MAX_VALUE, false);
        HLogger.debug((String) null, Long.MIN_VALUE, true);
        HLogger.debug((String) null, Long.MIN_VALUE, false);
        HLogger.debug((String) null, 0, true);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest63 {

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
    public final void test_debug_String_long() {
        HLogger.debug("Test string", System.currentTimeMillis());
        HLogger.debug("Test string", System.nanoTime());
        HLogger.debug("Test string", Long.MAX_VALUE);
        HLogger.debug("Test string", Long.MIN_VALUE);
        HLogger.debug("Test string", 0);
        HLogger.debug((String) null, System.currentTimeMillis());
        HLogger.debug((String) null, System.nanoTime());
        HLogger.debug((String) null, Long.MAX_VALUE);
        HLogger.debug((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
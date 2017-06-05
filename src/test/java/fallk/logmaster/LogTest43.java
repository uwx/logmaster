
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest43 {

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
    public final void test_warn_String_long_boolean() {
        HLogger.warn("Test string", System.currentTimeMillis(), true);
        HLogger.warn("Test string", System.currentTimeMillis(), false);
        HLogger.warn("Test string", System.nanoTime(), true);
        HLogger.warn("Test string", System.nanoTime(), false);
        HLogger.warn("Test string", Long.MAX_VALUE, true);
        HLogger.warn("Test string", Long.MAX_VALUE, false);
        HLogger.warn("Test string", Long.MIN_VALUE, true);
        HLogger.warn("Test string", Long.MIN_VALUE, false);
        HLogger.warn("Test string", 0, true);
        HLogger.warn("Test string", 0, false);
        HLogger.warn((String) null, System.currentTimeMillis(), true);
        HLogger.warn((String) null, System.currentTimeMillis(), false);
        HLogger.warn((String) null, System.nanoTime(), true);
        HLogger.warn((String) null, System.nanoTime(), false);
        HLogger.warn((String) null, Long.MAX_VALUE, true);
        HLogger.warn((String) null, Long.MAX_VALUE, false);
        HLogger.warn((String) null, Long.MIN_VALUE, true);
        HLogger.warn((String) null, Long.MIN_VALUE, false);
        HLogger.warn((String) null, 0, true);
    }

    
    //! $CHALK_END
}
    
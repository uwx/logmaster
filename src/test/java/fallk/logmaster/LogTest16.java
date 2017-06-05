
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest16 {

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
    public final void test_error_String_long_boolean() {
        HLogger.error("Test string", System.currentTimeMillis(), true);
        HLogger.error("Test string", System.currentTimeMillis(), false);
        HLogger.error("Test string", System.nanoTime(), true);
        HLogger.error("Test string", System.nanoTime(), false);
        HLogger.error("Test string", Long.MAX_VALUE, true);
        HLogger.error("Test string", Long.MAX_VALUE, false);
        HLogger.error("Test string", Long.MIN_VALUE, true);
        HLogger.error("Test string", Long.MIN_VALUE, false);
        HLogger.error("Test string", 0, true);
        HLogger.error("Test string", 0, false);
        HLogger.error((String) null, System.currentTimeMillis(), true);
        HLogger.error((String) null, System.currentTimeMillis(), false);
        HLogger.error((String) null, System.nanoTime(), true);
        HLogger.error((String) null, System.nanoTime(), false);
        HLogger.error((String) null, Long.MAX_VALUE, true);
        HLogger.error((String) null, Long.MAX_VALUE, false);
        HLogger.error((String) null, Long.MIN_VALUE, true);
        HLogger.error((String) null, Long.MIN_VALUE, false);
        HLogger.error((String) null, 0, true);
    }

    
    //! $CHALK_END
}
    
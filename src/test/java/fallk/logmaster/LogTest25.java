
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest25 {

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
    public final void test_severe_String_long_boolean() {
        HLogger.severe("Test string", System.currentTimeMillis(), true);
        HLogger.severe("Test string", System.currentTimeMillis(), false);
        HLogger.severe("Test string", System.nanoTime(), true);
        HLogger.severe("Test string", System.nanoTime(), false);
        HLogger.severe("Test string", Long.MAX_VALUE, true);
        HLogger.severe("Test string", Long.MAX_VALUE, false);
        HLogger.severe("Test string", Long.MIN_VALUE, true);
        HLogger.severe("Test string", Long.MIN_VALUE, false);
        HLogger.severe("Test string", 0, true);
        HLogger.severe("Test string", 0, false);
        HLogger.severe((String) null, System.currentTimeMillis(), true);
        HLogger.severe((String) null, System.currentTimeMillis(), false);
        HLogger.severe((String) null, System.nanoTime(), true);
        HLogger.severe((String) null, System.nanoTime(), false);
        HLogger.severe((String) null, Long.MAX_VALUE, true);
        HLogger.severe((String) null, Long.MAX_VALUE, false);
        HLogger.severe((String) null, Long.MIN_VALUE, true);
        HLogger.severe((String) null, Long.MIN_VALUE, false);
        HLogger.severe((String) null, 0, true);
    }

    
    //! $CHALK_END
}
    
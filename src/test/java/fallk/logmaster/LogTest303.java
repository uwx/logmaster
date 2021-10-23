
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest303 {

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
    public final void test_brightCyan_String_long() {
        HLogger.brightCyan("Test string", System.currentTimeMillis());
        HLogger.brightCyan("Test string", System.nanoTime());
        HLogger.brightCyan("Test string", Long.MAX_VALUE);
        HLogger.brightCyan("Test string", Long.MIN_VALUE);
        HLogger.brightCyan("Test string", 0);
        HLogger.brightCyan((String) null, System.currentTimeMillis());
        HLogger.brightCyan((String) null, System.nanoTime());
        HLogger.brightCyan((String) null, Long.MAX_VALUE);
        HLogger.brightCyan((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
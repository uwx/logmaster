
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest273 {

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
    public final void test_magenta_String_long() {
        HLogger.magenta("Test string", System.currentTimeMillis());
        HLogger.magenta("Test string", System.nanoTime());
        HLogger.magenta("Test string", Long.MAX_VALUE);
        HLogger.magenta("Test string", Long.MIN_VALUE);
        HLogger.magenta("Test string", 0);
        HLogger.magenta((String) null, System.currentTimeMillis());
        HLogger.magenta((String) null, System.nanoTime());
        HLogger.magenta((String) null, Long.MAX_VALUE);
        HLogger.magenta((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
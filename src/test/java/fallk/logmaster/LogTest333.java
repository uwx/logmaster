
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest333 {

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
    public final void test_brightBlack_String_long() {
        HLogger.brightBlack("Test string", System.currentTimeMillis());
        HLogger.brightBlack("Test string", System.nanoTime());
        HLogger.brightBlack("Test string", Long.MAX_VALUE);
        HLogger.brightBlack("Test string", Long.MIN_VALUE);
        HLogger.brightBlack("Test string", 0);
        HLogger.brightBlack((String) null, System.currentTimeMillis());
        HLogger.brightBlack((String) null, System.nanoTime());
        HLogger.brightBlack((String) null, Long.MAX_VALUE);
        HLogger.brightBlack((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
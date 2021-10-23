
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest243 {

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
    public final void test_brightGreen_String_long() {
        HLogger.brightGreen("Test string", System.currentTimeMillis());
        HLogger.brightGreen("Test string", System.nanoTime());
        HLogger.brightGreen("Test string", Long.MAX_VALUE);
        HLogger.brightGreen("Test string", Long.MIN_VALUE);
        HLogger.brightGreen("Test string", 0);
        HLogger.brightGreen((String) null, System.currentTimeMillis());
        HLogger.brightGreen((String) null, System.nanoTime());
        HLogger.brightGreen((String) null, Long.MAX_VALUE);
        HLogger.brightGreen((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
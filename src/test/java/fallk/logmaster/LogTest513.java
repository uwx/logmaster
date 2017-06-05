
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest513 {

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
    public final void test_bgGreen_String_long() {
        HLogger.bgGreen("Test string", System.currentTimeMillis());
        HLogger.bgGreen("Test string", System.nanoTime());
        HLogger.bgGreen("Test string", Long.MAX_VALUE);
        HLogger.bgGreen("Test string", Long.MIN_VALUE);
        HLogger.bgGreen("Test string", 0);
        HLogger.bgGreen((String) null, System.currentTimeMillis());
        HLogger.bgGreen((String) null, System.nanoTime());
        HLogger.bgGreen((String) null, Long.MAX_VALUE);
        HLogger.bgGreen((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
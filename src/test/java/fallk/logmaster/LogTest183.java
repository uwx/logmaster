
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest183 {

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
    public final void test_white_String_long() {
        HLogger.white("Test string", System.currentTimeMillis());
        HLogger.white("Test string", System.nanoTime());
        HLogger.white("Test string", Long.MAX_VALUE);
        HLogger.white("Test string", Long.MIN_VALUE);
        HLogger.white("Test string", 0);
        HLogger.white((String) null, System.currentTimeMillis());
        HLogger.white((String) null, System.nanoTime());
        HLogger.white((String) null, Long.MAX_VALUE);
        HLogger.white((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
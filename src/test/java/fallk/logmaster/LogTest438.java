
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest438 {

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
    public final void test_bgBlue_String_long() {
        HLogger.bgBlue("Test string", System.currentTimeMillis());
        HLogger.bgBlue("Test string", System.nanoTime());
        HLogger.bgBlue("Test string", Long.MAX_VALUE);
        HLogger.bgBlue("Test string", Long.MIN_VALUE);
        HLogger.bgBlue("Test string", 0);
        HLogger.bgBlue((String) null, System.currentTimeMillis());
        HLogger.bgBlue((String) null, System.nanoTime());
        HLogger.bgBlue((String) null, Long.MAX_VALUE);
        HLogger.bgBlue((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
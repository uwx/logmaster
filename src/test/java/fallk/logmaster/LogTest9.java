
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest9 {

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
    public final void test_log_String_long() {
        HLogger.log("Test string", System.currentTimeMillis());
        HLogger.log("Test string", System.nanoTime());
        HLogger.log("Test string", Long.MAX_VALUE);
        HLogger.log("Test string", Long.MIN_VALUE);
        HLogger.log("Test string", 0);
        HLogger.log((String) null, System.currentTimeMillis());
        HLogger.log((String) null, System.nanoTime());
        HLogger.log((String) null, Long.MAX_VALUE);
        HLogger.log((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

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
    public final void test_yellow_String_long() {
        HLogger.yellow("Test string", System.currentTimeMillis());
        HLogger.yellow("Test string", System.nanoTime());
        HLogger.yellow("Test string", Long.MAX_VALUE);
        HLogger.yellow("Test string", Long.MIN_VALUE);
        HLogger.yellow("Test string", 0);
        HLogger.yellow((String) null, System.currentTimeMillis());
        HLogger.yellow((String) null, System.nanoTime());
        HLogger.yellow((String) null, Long.MAX_VALUE);
        HLogger.yellow((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
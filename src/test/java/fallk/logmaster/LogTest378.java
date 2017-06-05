
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest378 {

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
    public final void test_bright_yellow_String_long() {
        HLogger.bright_yellow("Test string", System.currentTimeMillis());
        HLogger.bright_yellow("Test string", System.nanoTime());
        HLogger.bright_yellow("Test string", Long.MAX_VALUE);
        HLogger.bright_yellow("Test string", Long.MIN_VALUE);
        HLogger.bright_yellow("Test string", 0);
        HLogger.bright_yellow((String) null, System.currentTimeMillis());
        HLogger.bright_yellow((String) null, System.nanoTime());
        HLogger.bright_yellow((String) null, Long.MAX_VALUE);
        HLogger.bright_yellow((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
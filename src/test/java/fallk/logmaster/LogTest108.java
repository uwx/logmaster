
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest108 {

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
    public final void test_green_String_long() {
        HLogger.green("Test string", System.currentTimeMillis());
        HLogger.green("Test string", System.nanoTime());
        HLogger.green("Test string", Long.MAX_VALUE);
        HLogger.green("Test string", Long.MIN_VALUE);
        HLogger.green("Test string", 0);
        HLogger.green((String) null, System.currentTimeMillis());
        HLogger.green((String) null, System.nanoTime());
        HLogger.green((String) null, Long.MAX_VALUE);
        HLogger.green((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest168 {

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
    public final void test_cyan_String_long() {
        HLogger.cyan("Test string", System.currentTimeMillis());
        HLogger.cyan("Test string", System.nanoTime());
        HLogger.cyan("Test string", Long.MAX_VALUE);
        HLogger.cyan("Test string", Long.MIN_VALUE);
        HLogger.cyan("Test string", 0);
        HLogger.cyan((String) null, System.currentTimeMillis());
        HLogger.cyan((String) null, System.nanoTime());
        HLogger.cyan((String) null, Long.MAX_VALUE);
        HLogger.cyan((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest138 {

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
    public final void test_underline_String_long() {
        HLogger.underline("Test string", System.currentTimeMillis());
        HLogger.underline("Test string", System.nanoTime());
        HLogger.underline("Test string", Long.MAX_VALUE);
        HLogger.underline("Test string", Long.MIN_VALUE);
        HLogger.underline("Test string", 0);
        HLogger.underline((String) null, System.currentTimeMillis());
        HLogger.underline((String) null, System.nanoTime());
        HLogger.underline((String) null, Long.MAX_VALUE);
        HLogger.underline((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
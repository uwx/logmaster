
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest123 {

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
    public final void test_italic_String_long() {
        HLogger.italic("Test string", System.currentTimeMillis());
        HLogger.italic("Test string", System.nanoTime());
        HLogger.italic("Test string", Long.MAX_VALUE);
        HLogger.italic("Test string", Long.MIN_VALUE);
        HLogger.italic("Test string", 0);
        HLogger.italic((String) null, System.currentTimeMillis());
        HLogger.italic((String) null, System.nanoTime());
        HLogger.italic((String) null, Long.MAX_VALUE);
        HLogger.italic((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
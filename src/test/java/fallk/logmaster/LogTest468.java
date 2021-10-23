
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest468 {

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
    public final void test_bgCyan_String_long() {
        HLogger.bgCyan("Test string", System.currentTimeMillis());
        HLogger.bgCyan("Test string", System.nanoTime());
        HLogger.bgCyan("Test string", Long.MAX_VALUE);
        HLogger.bgCyan("Test string", Long.MIN_VALUE);
        HLogger.bgCyan("Test string", 0);
        HLogger.bgCyan((String) null, System.currentTimeMillis());
        HLogger.bgCyan((String) null, System.nanoTime());
        HLogger.bgCyan((String) null, Long.MAX_VALUE);
        HLogger.bgCyan((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
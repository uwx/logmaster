
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest27 {

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
    public final void test_severe_String_long() {
        HLogger.severe("Test string", System.currentTimeMillis());
        HLogger.severe("Test string", System.nanoTime());
        HLogger.severe("Test string", Long.MAX_VALUE);
        HLogger.severe("Test string", Long.MIN_VALUE);
        HLogger.severe("Test string", 0);
        HLogger.severe((String) null, System.currentTimeMillis());
        HLogger.severe((String) null, System.nanoTime());
        HLogger.severe((String) null, Long.MAX_VALUE);
        HLogger.severe((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
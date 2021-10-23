
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest213 {

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
    public final void test_grey_String_long() {
        HLogger.grey("Test string", System.currentTimeMillis());
        HLogger.grey("Test string", System.nanoTime());
        HLogger.grey("Test string", Long.MAX_VALUE);
        HLogger.grey("Test string", Long.MIN_VALUE);
        HLogger.grey("Test string", 0);
        HLogger.grey((String) null, System.currentTimeMillis());
        HLogger.grey((String) null, System.nanoTime());
        HLogger.grey((String) null, Long.MAX_VALUE);
        HLogger.grey((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
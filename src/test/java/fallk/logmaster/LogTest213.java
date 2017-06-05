
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
    public final void test_red_String_long() {
        HLogger.red("Test string", System.currentTimeMillis());
        HLogger.red("Test string", System.nanoTime());
        HLogger.red("Test string", Long.MAX_VALUE);
        HLogger.red("Test string", Long.MIN_VALUE);
        HLogger.red("Test string", 0);
        HLogger.red((String) null, System.currentTimeMillis());
        HLogger.red((String) null, System.nanoTime());
        HLogger.red((String) null, Long.MAX_VALUE);
        HLogger.red((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
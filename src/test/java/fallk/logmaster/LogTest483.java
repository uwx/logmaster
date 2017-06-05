
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest483 {

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
    public final void test_bgBlack_String_long() {
        HLogger.bgBlack("Test string", System.currentTimeMillis());
        HLogger.bgBlack("Test string", System.nanoTime());
        HLogger.bgBlack("Test string", Long.MAX_VALUE);
        HLogger.bgBlack("Test string", Long.MIN_VALUE);
        HLogger.bgBlack("Test string", 0);
        HLogger.bgBlack((String) null, System.currentTimeMillis());
        HLogger.bgBlack((String) null, System.nanoTime());
        HLogger.bgBlack((String) null, Long.MAX_VALUE);
        HLogger.bgBlack((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
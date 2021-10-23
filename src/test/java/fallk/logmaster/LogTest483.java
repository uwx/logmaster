
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
    public final void test_bgWhite_String_long() {
        HLogger.bgWhite("Test string", System.currentTimeMillis());
        HLogger.bgWhite("Test string", System.nanoTime());
        HLogger.bgWhite("Test string", Long.MAX_VALUE);
        HLogger.bgWhite("Test string", Long.MIN_VALUE);
        HLogger.bgWhite("Test string", 0);
        HLogger.bgWhite((String) null, System.currentTimeMillis());
        HLogger.bgWhite((String) null, System.nanoTime());
        HLogger.bgWhite((String) null, Long.MAX_VALUE);
        HLogger.bgWhite((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
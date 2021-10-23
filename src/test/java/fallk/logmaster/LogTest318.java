
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest318 {

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
    public final void test_brightWhite_String_long() {
        HLogger.brightWhite("Test string", System.currentTimeMillis());
        HLogger.brightWhite("Test string", System.nanoTime());
        HLogger.brightWhite("Test string", Long.MAX_VALUE);
        HLogger.brightWhite("Test string", Long.MIN_VALUE);
        HLogger.brightWhite("Test string", 0);
        HLogger.brightWhite((String) null, System.currentTimeMillis());
        HLogger.brightWhite((String) null, System.nanoTime());
        HLogger.brightWhite((String) null, Long.MAX_VALUE);
        HLogger.brightWhite((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
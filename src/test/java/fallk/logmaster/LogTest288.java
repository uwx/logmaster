
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest288 {

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
    public final void test_brightMagenta_String_long() {
        HLogger.brightMagenta("Test string", System.currentTimeMillis());
        HLogger.brightMagenta("Test string", System.nanoTime());
        HLogger.brightMagenta("Test string", Long.MAX_VALUE);
        HLogger.brightMagenta("Test string", Long.MIN_VALUE);
        HLogger.brightMagenta("Test string", 0);
        HLogger.brightMagenta((String) null, System.currentTimeMillis());
        HLogger.brightMagenta((String) null, System.nanoTime());
        HLogger.brightMagenta((String) null, Long.MAX_VALUE);
        HLogger.brightMagenta((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
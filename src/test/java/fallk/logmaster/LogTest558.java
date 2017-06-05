
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest558 {

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
    public final void test_bgMagenta_String_long() {
        HLogger.bgMagenta("Test string", System.currentTimeMillis());
        HLogger.bgMagenta("Test string", System.nanoTime());
        HLogger.bgMagenta("Test string", Long.MAX_VALUE);
        HLogger.bgMagenta("Test string", Long.MIN_VALUE);
        HLogger.bgMagenta("Test string", 0);
        HLogger.bgMagenta((String) null, System.currentTimeMillis());
        HLogger.bgMagenta((String) null, System.nanoTime());
        HLogger.bgMagenta((String) null, Long.MAX_VALUE);
        HLogger.bgMagenta((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
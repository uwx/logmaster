
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest45 {

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
    public final void test_warn_String_long() {
        HLogger.warn("Test string", System.currentTimeMillis());
        HLogger.warn("Test string", System.nanoTime());
        HLogger.warn("Test string", Long.MAX_VALUE);
        HLogger.warn("Test string", Long.MIN_VALUE);
        HLogger.warn("Test string", 0);
        HLogger.warn((String) null, System.currentTimeMillis());
        HLogger.warn((String) null, System.nanoTime());
        HLogger.warn((String) null, Long.MAX_VALUE);
        HLogger.warn((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
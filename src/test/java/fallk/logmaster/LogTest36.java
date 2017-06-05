
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest36 {

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
    public final void test_fatal_String_long() {
        HLogger.fatal("Test string", System.currentTimeMillis());
        HLogger.fatal("Test string", System.nanoTime());
        HLogger.fatal("Test string", Long.MAX_VALUE);
        HLogger.fatal("Test string", Long.MIN_VALUE);
        HLogger.fatal("Test string", 0);
        HLogger.fatal((String) null, System.currentTimeMillis());
        HLogger.fatal((String) null, System.nanoTime());
        HLogger.fatal((String) null, Long.MAX_VALUE);
        HLogger.fatal((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
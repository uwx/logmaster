
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest78 {

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
    public final void test_black_String_long() {
        HLogger.black("Test string", System.currentTimeMillis());
        HLogger.black("Test string", System.nanoTime());
        HLogger.black("Test string", Long.MAX_VALUE);
        HLogger.black("Test string", Long.MIN_VALUE);
        HLogger.black("Test string", 0);
        HLogger.black((String) null, System.currentTimeMillis());
        HLogger.black((String) null, System.nanoTime());
        HLogger.black((String) null, Long.MAX_VALUE);
        HLogger.black((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
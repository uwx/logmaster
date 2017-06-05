
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
    public final void test_reset_String_long() {
        HLogger.reset("Test string", System.currentTimeMillis());
        HLogger.reset("Test string", System.nanoTime());
        HLogger.reset("Test string", Long.MAX_VALUE);
        HLogger.reset("Test string", Long.MIN_VALUE);
        HLogger.reset("Test string", 0);
        HLogger.reset((String) null, System.currentTimeMillis());
        HLogger.reset((String) null, System.nanoTime());
        HLogger.reset((String) null, Long.MAX_VALUE);
        HLogger.reset((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
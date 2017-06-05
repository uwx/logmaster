
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest54 {

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
    public final void test_info_String_long() {
        HLogger.info("Test string", System.currentTimeMillis());
        HLogger.info("Test string", System.nanoTime());
        HLogger.info("Test string", Long.MAX_VALUE);
        HLogger.info("Test string", Long.MIN_VALUE);
        HLogger.info("Test string", 0);
        HLogger.info((String) null, System.currentTimeMillis());
        HLogger.info((String) null, System.nanoTime());
        HLogger.info((String) null, Long.MAX_VALUE);
        HLogger.info((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
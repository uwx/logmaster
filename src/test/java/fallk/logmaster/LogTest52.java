
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest52 {

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
    public final void test_info_String_long_boolean() {
        HLogger.info("Test string", System.currentTimeMillis(), true);
        HLogger.info("Test string", System.currentTimeMillis(), false);
        HLogger.info("Test string", System.nanoTime(), true);
        HLogger.info("Test string", System.nanoTime(), false);
        HLogger.info("Test string", Long.MAX_VALUE, true);
        HLogger.info("Test string", Long.MAX_VALUE, false);
        HLogger.info("Test string", Long.MIN_VALUE, true);
        HLogger.info("Test string", Long.MIN_VALUE, false);
        HLogger.info("Test string", 0, true);
        HLogger.info("Test string", 0, false);
        HLogger.info((String) null, System.currentTimeMillis(), true);
        HLogger.info((String) null, System.currentTimeMillis(), false);
        HLogger.info((String) null, System.nanoTime(), true);
        HLogger.info((String) null, System.nanoTime(), false);
        HLogger.info((String) null, Long.MAX_VALUE, true);
        HLogger.info((String) null, Long.MAX_VALUE, false);
        HLogger.info((String) null, Long.MIN_VALUE, true);
        HLogger.info((String) null, Long.MIN_VALUE, false);
        HLogger.info((String) null, 0, true);
    }

    
    //! $CHALK_END
}
    
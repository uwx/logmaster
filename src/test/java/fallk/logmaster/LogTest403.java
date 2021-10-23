
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest403 {

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
    public final void test_bgGreen_int_String_long() {
        HLogger.bgGreen(0, "Test string", System.currentTimeMillis());
        HLogger.bgGreen(0, "Test string", System.nanoTime());
        HLogger.bgGreen(0, "Test string", Long.MAX_VALUE);
        HLogger.bgGreen(0, "Test string", Long.MIN_VALUE);
        HLogger.bgGreen(0, "Test string", 0);
        HLogger.bgGreen(0, (String) null, System.currentTimeMillis());
        HLogger.bgGreen(0, (String) null, System.nanoTime());
        HLogger.bgGreen(0, (String) null, Long.MAX_VALUE);
        HLogger.bgGreen(0, (String) null, Long.MIN_VALUE);
        HLogger.bgGreen(0, (String) null, 0);
        HLogger.bgGreen(1, "Test string", System.currentTimeMillis());
        HLogger.bgGreen(1, "Test string", System.nanoTime());
        HLogger.bgGreen(1, "Test string", Long.MAX_VALUE);
        HLogger.bgGreen(1, "Test string", Long.MIN_VALUE);
        HLogger.bgGreen(1, "Test string", 0);
        HLogger.bgGreen(1, (String) null, System.currentTimeMillis());
        HLogger.bgGreen(1, (String) null, System.nanoTime());
        HLogger.bgGreen(1, (String) null, Long.MAX_VALUE);
        HLogger.bgGreen(1, (String) null, Long.MIN_VALUE);
        HLogger.bgGreen(1, (String) null, 0);
        HLogger.bgGreen(2, "Test string", System.currentTimeMillis());
        HLogger.bgGreen(2, "Test string", System.nanoTime());
        HLogger.bgGreen(2, "Test string", Long.MAX_VALUE);
        HLogger.bgGreen(2, "Test string", Long.MIN_VALUE);
        HLogger.bgGreen(2, "Test string", 0);
        HLogger.bgGreen(2, (String) null, System.currentTimeMillis());
        HLogger.bgGreen(2, (String) null, System.nanoTime());
        HLogger.bgGreen(2, (String) null, Long.MAX_VALUE);
        HLogger.bgGreen(2, (String) null, Long.MIN_VALUE);
        HLogger.bgGreen(2, (String) null, 0);
        HLogger.bgGreen(3, "Test string", System.currentTimeMillis());
        HLogger.bgGreen(3, "Test string", System.nanoTime());
        HLogger.bgGreen(3, "Test string", Long.MAX_VALUE);
        HLogger.bgGreen(3, "Test string", Long.MIN_VALUE);
        HLogger.bgGreen(3, "Test string", 0);
        HLogger.bgGreen(3, (String) null, System.currentTimeMillis());
        HLogger.bgGreen(3, (String) null, System.nanoTime());
        HLogger.bgGreen(3, (String) null, Long.MAX_VALUE);
        HLogger.bgGreen(3, (String) null, Long.MIN_VALUE);
        HLogger.bgGreen(3, (String) null, 0);
        HLogger.bgGreen(4, "Test string", System.currentTimeMillis());
        HLogger.bgGreen(4, "Test string", System.nanoTime());
        HLogger.bgGreen(4, "Test string", Long.MAX_VALUE);
        HLogger.bgGreen(4, "Test string", Long.MIN_VALUE);
        HLogger.bgGreen(4, "Test string", 0);
        HLogger.bgGreen(4, (String) null, System.currentTimeMillis());
        HLogger.bgGreen(4, (String) null, System.nanoTime());
        HLogger.bgGreen(4, (String) null, Long.MAX_VALUE);
        HLogger.bgGreen(4, (String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
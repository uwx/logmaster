
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest133 {

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
    public final void test_underline_int_String_long() {
        HLogger.underline(0, "Test string", System.currentTimeMillis());
        HLogger.underline(0, "Test string", System.nanoTime());
        HLogger.underline(0, "Test string", Long.MAX_VALUE);
        HLogger.underline(0, "Test string", Long.MIN_VALUE);
        HLogger.underline(0, "Test string", 0);
        HLogger.underline(0, (String) null, System.currentTimeMillis());
        HLogger.underline(0, (String) null, System.nanoTime());
        HLogger.underline(0, (String) null, Long.MAX_VALUE);
        HLogger.underline(0, (String) null, Long.MIN_VALUE);
        HLogger.underline(0, (String) null, 0);
        HLogger.underline(1, "Test string", System.currentTimeMillis());
        HLogger.underline(1, "Test string", System.nanoTime());
        HLogger.underline(1, "Test string", Long.MAX_VALUE);
        HLogger.underline(1, "Test string", Long.MIN_VALUE);
        HLogger.underline(1, "Test string", 0);
        HLogger.underline(1, (String) null, System.currentTimeMillis());
        HLogger.underline(1, (String) null, System.nanoTime());
        HLogger.underline(1, (String) null, Long.MAX_VALUE);
        HLogger.underline(1, (String) null, Long.MIN_VALUE);
        HLogger.underline(1, (String) null, 0);
        HLogger.underline(2, "Test string", System.currentTimeMillis());
        HLogger.underline(2, "Test string", System.nanoTime());
        HLogger.underline(2, "Test string", Long.MAX_VALUE);
        HLogger.underline(2, "Test string", Long.MIN_VALUE);
        HLogger.underline(2, "Test string", 0);
        HLogger.underline(2, (String) null, System.currentTimeMillis());
        HLogger.underline(2, (String) null, System.nanoTime());
        HLogger.underline(2, (String) null, Long.MAX_VALUE);
        HLogger.underline(2, (String) null, Long.MIN_VALUE);
        HLogger.underline(2, (String) null, 0);
        HLogger.underline(3, "Test string", System.currentTimeMillis());
        HLogger.underline(3, "Test string", System.nanoTime());
        HLogger.underline(3, "Test string", Long.MAX_VALUE);
        HLogger.underline(3, "Test string", Long.MIN_VALUE);
        HLogger.underline(3, "Test string", 0);
        HLogger.underline(3, (String) null, System.currentTimeMillis());
        HLogger.underline(3, (String) null, System.nanoTime());
        HLogger.underline(3, (String) null, Long.MAX_VALUE);
        HLogger.underline(3, (String) null, Long.MIN_VALUE);
        HLogger.underline(3, (String) null, 0);
        HLogger.underline(4, "Test string", System.currentTimeMillis());
        HLogger.underline(4, "Test string", System.nanoTime());
        HLogger.underline(4, "Test string", Long.MAX_VALUE);
        HLogger.underline(4, "Test string", Long.MIN_VALUE);
        HLogger.underline(4, "Test string", 0);
        HLogger.underline(4, (String) null, System.currentTimeMillis());
        HLogger.underline(4, (String) null, System.nanoTime());
        HLogger.underline(4, (String) null, Long.MAX_VALUE);
        HLogger.underline(4, (String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
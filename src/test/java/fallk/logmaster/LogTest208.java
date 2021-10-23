
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest208 {

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
    public final void test_grey_int_String_long() {
        HLogger.grey(0, "Test string", System.currentTimeMillis());
        HLogger.grey(0, "Test string", System.nanoTime());
        HLogger.grey(0, "Test string", Long.MAX_VALUE);
        HLogger.grey(0, "Test string", Long.MIN_VALUE);
        HLogger.grey(0, "Test string", 0);
        HLogger.grey(0, (String) null, System.currentTimeMillis());
        HLogger.grey(0, (String) null, System.nanoTime());
        HLogger.grey(0, (String) null, Long.MAX_VALUE);
        HLogger.grey(0, (String) null, Long.MIN_VALUE);
        HLogger.grey(0, (String) null, 0);
        HLogger.grey(1, "Test string", System.currentTimeMillis());
        HLogger.grey(1, "Test string", System.nanoTime());
        HLogger.grey(1, "Test string", Long.MAX_VALUE);
        HLogger.grey(1, "Test string", Long.MIN_VALUE);
        HLogger.grey(1, "Test string", 0);
        HLogger.grey(1, (String) null, System.currentTimeMillis());
        HLogger.grey(1, (String) null, System.nanoTime());
        HLogger.grey(1, (String) null, Long.MAX_VALUE);
        HLogger.grey(1, (String) null, Long.MIN_VALUE);
        HLogger.grey(1, (String) null, 0);
        HLogger.grey(2, "Test string", System.currentTimeMillis());
        HLogger.grey(2, "Test string", System.nanoTime());
        HLogger.grey(2, "Test string", Long.MAX_VALUE);
        HLogger.grey(2, "Test string", Long.MIN_VALUE);
        HLogger.grey(2, "Test string", 0);
        HLogger.grey(2, (String) null, System.currentTimeMillis());
        HLogger.grey(2, (String) null, System.nanoTime());
        HLogger.grey(2, (String) null, Long.MAX_VALUE);
        HLogger.grey(2, (String) null, Long.MIN_VALUE);
        HLogger.grey(2, (String) null, 0);
        HLogger.grey(3, "Test string", System.currentTimeMillis());
        HLogger.grey(3, "Test string", System.nanoTime());
        HLogger.grey(3, "Test string", Long.MAX_VALUE);
        HLogger.grey(3, "Test string", Long.MIN_VALUE);
        HLogger.grey(3, "Test string", 0);
        HLogger.grey(3, (String) null, System.currentTimeMillis());
        HLogger.grey(3, (String) null, System.nanoTime());
        HLogger.grey(3, (String) null, Long.MAX_VALUE);
        HLogger.grey(3, (String) null, Long.MIN_VALUE);
        HLogger.grey(3, (String) null, 0);
        HLogger.grey(4, "Test string", System.currentTimeMillis());
        HLogger.grey(4, "Test string", System.nanoTime());
        HLogger.grey(4, "Test string", Long.MAX_VALUE);
        HLogger.grey(4, "Test string", Long.MIN_VALUE);
        HLogger.grey(4, "Test string", 0);
        HLogger.grey(4, (String) null, System.currentTimeMillis());
        HLogger.grey(4, (String) null, System.nanoTime());
        HLogger.grey(4, (String) null, Long.MAX_VALUE);
        HLogger.grey(4, (String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
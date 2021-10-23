
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest268 {

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
    public final void test_brightBlue_int_String_long() {
        HLogger.brightBlue(0, "Test string", System.currentTimeMillis());
        HLogger.brightBlue(0, "Test string", System.nanoTime());
        HLogger.brightBlue(0, "Test string", Long.MAX_VALUE);
        HLogger.brightBlue(0, "Test string", Long.MIN_VALUE);
        HLogger.brightBlue(0, "Test string", 0);
        HLogger.brightBlue(0, (String) null, System.currentTimeMillis());
        HLogger.brightBlue(0, (String) null, System.nanoTime());
        HLogger.brightBlue(0, (String) null, Long.MAX_VALUE);
        HLogger.brightBlue(0, (String) null, Long.MIN_VALUE);
        HLogger.brightBlue(0, (String) null, 0);
        HLogger.brightBlue(1, "Test string", System.currentTimeMillis());
        HLogger.brightBlue(1, "Test string", System.nanoTime());
        HLogger.brightBlue(1, "Test string", Long.MAX_VALUE);
        HLogger.brightBlue(1, "Test string", Long.MIN_VALUE);
        HLogger.brightBlue(1, "Test string", 0);
        HLogger.brightBlue(1, (String) null, System.currentTimeMillis());
        HLogger.brightBlue(1, (String) null, System.nanoTime());
        HLogger.brightBlue(1, (String) null, Long.MAX_VALUE);
        HLogger.brightBlue(1, (String) null, Long.MIN_VALUE);
        HLogger.brightBlue(1, (String) null, 0);
        HLogger.brightBlue(2, "Test string", System.currentTimeMillis());
        HLogger.brightBlue(2, "Test string", System.nanoTime());
        HLogger.brightBlue(2, "Test string", Long.MAX_VALUE);
        HLogger.brightBlue(2, "Test string", Long.MIN_VALUE);
        HLogger.brightBlue(2, "Test string", 0);
        HLogger.brightBlue(2, (String) null, System.currentTimeMillis());
        HLogger.brightBlue(2, (String) null, System.nanoTime());
        HLogger.brightBlue(2, (String) null, Long.MAX_VALUE);
        HLogger.brightBlue(2, (String) null, Long.MIN_VALUE);
        HLogger.brightBlue(2, (String) null, 0);
        HLogger.brightBlue(3, "Test string", System.currentTimeMillis());
        HLogger.brightBlue(3, "Test string", System.nanoTime());
        HLogger.brightBlue(3, "Test string", Long.MAX_VALUE);
        HLogger.brightBlue(3, "Test string", Long.MIN_VALUE);
        HLogger.brightBlue(3, "Test string", 0);
        HLogger.brightBlue(3, (String) null, System.currentTimeMillis());
        HLogger.brightBlue(3, (String) null, System.nanoTime());
        HLogger.brightBlue(3, (String) null, Long.MAX_VALUE);
        HLogger.brightBlue(3, (String) null, Long.MIN_VALUE);
        HLogger.brightBlue(3, (String) null, 0);
        HLogger.brightBlue(4, "Test string", System.currentTimeMillis());
        HLogger.brightBlue(4, "Test string", System.nanoTime());
        HLogger.brightBlue(4, "Test string", Long.MAX_VALUE);
        HLogger.brightBlue(4, "Test string", Long.MIN_VALUE);
        HLogger.brightBlue(4, "Test string", 0);
        HLogger.brightBlue(4, (String) null, System.currentTimeMillis());
        HLogger.brightBlue(4, (String) null, System.nanoTime());
        HLogger.brightBlue(4, (String) null, Long.MAX_VALUE);
        HLogger.brightBlue(4, (String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
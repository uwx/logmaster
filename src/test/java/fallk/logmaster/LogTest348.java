
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest348 {

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
    public final void test_brightGray_String_long() {
        HLogger.brightGray("Test string", System.currentTimeMillis());
        HLogger.brightGray("Test string", System.nanoTime());
        HLogger.brightGray("Test string", Long.MAX_VALUE);
        HLogger.brightGray("Test string", Long.MIN_VALUE);
        HLogger.brightGray("Test string", 0);
        HLogger.brightGray((String) null, System.currentTimeMillis());
        HLogger.brightGray((String) null, System.nanoTime());
        HLogger.brightGray((String) null, Long.MAX_VALUE);
        HLogger.brightGray((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
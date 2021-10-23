
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest363 {

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
    public final void test_brightGrey_String_long() {
        HLogger.brightGrey("Test string", System.currentTimeMillis());
        HLogger.brightGrey("Test string", System.nanoTime());
        HLogger.brightGrey("Test string", Long.MAX_VALUE);
        HLogger.brightGrey("Test string", Long.MIN_VALUE);
        HLogger.brightGrey("Test string", 0);
        HLogger.brightGrey((String) null, System.currentTimeMillis());
        HLogger.brightGrey((String) null, System.nanoTime());
        HLogger.brightGrey((String) null, Long.MAX_VALUE);
        HLogger.brightGrey((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
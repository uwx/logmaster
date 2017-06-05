
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest168 {

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
    public final void test_hidden_String_long() {
        HLogger.hidden("Test string", System.currentTimeMillis());
        HLogger.hidden("Test string", System.nanoTime());
        HLogger.hidden("Test string", Long.MAX_VALUE);
        HLogger.hidden("Test string", Long.MIN_VALUE);
        HLogger.hidden("Test string", 0);
        HLogger.hidden((String) null, System.currentTimeMillis());
        HLogger.hidden((String) null, System.nanoTime());
        HLogger.hidden((String) null, Long.MAX_VALUE);
        HLogger.hidden((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
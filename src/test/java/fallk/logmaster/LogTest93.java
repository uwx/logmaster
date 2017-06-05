
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest93 {

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
    public final void test_bold_String_long() {
        HLogger.bold("Test string", System.currentTimeMillis());
        HLogger.bold("Test string", System.nanoTime());
        HLogger.bold("Test string", Long.MAX_VALUE);
        HLogger.bold("Test string", Long.MIN_VALUE);
        HLogger.bold("Test string", 0);
        HLogger.bold((String) null, System.currentTimeMillis());
        HLogger.bold((String) null, System.nanoTime());
        HLogger.bold((String) null, Long.MAX_VALUE);
        HLogger.bold((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
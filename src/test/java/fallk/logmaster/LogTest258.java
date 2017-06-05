
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest258 {

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
    public final void test_blue_String_long() {
        HLogger.blue("Test string", System.currentTimeMillis());
        HLogger.blue("Test string", System.nanoTime());
        HLogger.blue("Test string", Long.MAX_VALUE);
        HLogger.blue("Test string", Long.MIN_VALUE);
        HLogger.blue("Test string", 0);
        HLogger.blue((String) null, System.currentTimeMillis());
        HLogger.blue((String) null, System.nanoTime());
        HLogger.blue((String) null, Long.MAX_VALUE);
        HLogger.blue((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
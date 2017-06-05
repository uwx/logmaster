
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest318 {

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
    public final void test_gray_String_long() {
        HLogger.gray("Test string", System.currentTimeMillis());
        HLogger.gray("Test string", System.nanoTime());
        HLogger.gray("Test string", Long.MAX_VALUE);
        HLogger.gray("Test string", Long.MIN_VALUE);
        HLogger.gray("Test string", 0);
        HLogger.gray((String) null, System.currentTimeMillis());
        HLogger.gray((String) null, System.nanoTime());
        HLogger.gray((String) null, Long.MAX_VALUE);
        HLogger.gray((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
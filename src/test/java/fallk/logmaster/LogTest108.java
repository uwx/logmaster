
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest108 {

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
    public final void test_dim_String_long() {
        HLogger.dim("Test string", System.currentTimeMillis());
        HLogger.dim("Test string", System.nanoTime());
        HLogger.dim("Test string", Long.MAX_VALUE);
        HLogger.dim("Test string", Long.MIN_VALUE);
        HLogger.dim("Test string", 0);
        HLogger.dim((String) null, System.currentTimeMillis());
        HLogger.dim((String) null, System.nanoTime());
        HLogger.dim((String) null, Long.MAX_VALUE);
        HLogger.dim((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
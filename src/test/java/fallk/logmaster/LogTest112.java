
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest112 {

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
    public final void test_dim_Exception_long() {
        HLogger.dim(new Exception(), System.currentTimeMillis());
        HLogger.dim(new Exception(), System.nanoTime());
        HLogger.dim(new Exception(), Long.MAX_VALUE);
        HLogger.dim(new Exception(), Long.MIN_VALUE);
        HLogger.dim(new Exception(), 0);
        HLogger.dim((Exception) null, System.currentTimeMillis());
        HLogger.dim((Exception) null, System.nanoTime());
        HLogger.dim((Exception) null, Long.MAX_VALUE);
        HLogger.dim((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
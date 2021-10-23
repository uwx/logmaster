
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest82 {

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
    public final void test_black_Exception_long() {
        HLogger.black(new Exception(), System.currentTimeMillis());
        HLogger.black(new Exception(), System.nanoTime());
        HLogger.black(new Exception(), Long.MAX_VALUE);
        HLogger.black(new Exception(), Long.MIN_VALUE);
        HLogger.black(new Exception(), 0);
        HLogger.black((Exception) null, System.currentTimeMillis());
        HLogger.black((Exception) null, System.nanoTime());
        HLogger.black((Exception) null, Long.MAX_VALUE);
        HLogger.black((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
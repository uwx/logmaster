
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest127 {

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
    public final void test_italic_Exception_long() {
        HLogger.italic(new Exception(), System.currentTimeMillis());
        HLogger.italic(new Exception(), System.nanoTime());
        HLogger.italic(new Exception(), Long.MAX_VALUE);
        HLogger.italic(new Exception(), Long.MIN_VALUE);
        HLogger.italic(new Exception(), 0);
        HLogger.italic((Exception) null, System.currentTimeMillis());
        HLogger.italic((Exception) null, System.nanoTime());
        HLogger.italic((Exception) null, Long.MAX_VALUE);
        HLogger.italic((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
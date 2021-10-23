
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest172 {

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
    public final void test_cyan_Exception_long() {
        HLogger.cyan(new Exception(), System.currentTimeMillis());
        HLogger.cyan(new Exception(), System.nanoTime());
        HLogger.cyan(new Exception(), Long.MAX_VALUE);
        HLogger.cyan(new Exception(), Long.MIN_VALUE);
        HLogger.cyan(new Exception(), 0);
        HLogger.cyan((Exception) null, System.currentTimeMillis());
        HLogger.cyan((Exception) null, System.nanoTime());
        HLogger.cyan((Exception) null, Long.MAX_VALUE);
        HLogger.cyan((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
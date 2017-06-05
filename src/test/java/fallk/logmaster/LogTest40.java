
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest40 {

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
    public final void test_fatal_Exception_long() {
        HLogger.fatal(new Exception(), System.currentTimeMillis());
        HLogger.fatal(new Exception(), System.nanoTime());
        HLogger.fatal(new Exception(), Long.MAX_VALUE);
        HLogger.fatal(new Exception(), Long.MIN_VALUE);
        HLogger.fatal(new Exception(), 0);
        HLogger.fatal((Exception) null, System.currentTimeMillis());
        HLogger.fatal((Exception) null, System.nanoTime());
        HLogger.fatal((Exception) null, Long.MAX_VALUE);
        HLogger.fatal((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
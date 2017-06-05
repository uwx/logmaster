
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest337 {

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
    public final void test_grey_Exception_long() {
        HLogger.grey(new Exception(), System.currentTimeMillis());
        HLogger.grey(new Exception(), System.nanoTime());
        HLogger.grey(new Exception(), Long.MAX_VALUE);
        HLogger.grey(new Exception(), Long.MIN_VALUE);
        HLogger.grey(new Exception(), 0);
        HLogger.grey((Exception) null, System.currentTimeMillis());
        HLogger.grey((Exception) null, System.nanoTime());
        HLogger.grey((Exception) null, Long.MAX_VALUE);
        HLogger.grey((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
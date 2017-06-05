
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest157 {

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
    public final void test_inverse_Exception_long() {
        HLogger.inverse(new Exception(), System.currentTimeMillis());
        HLogger.inverse(new Exception(), System.nanoTime());
        HLogger.inverse(new Exception(), Long.MAX_VALUE);
        HLogger.inverse(new Exception(), Long.MIN_VALUE);
        HLogger.inverse(new Exception(), 0);
        HLogger.inverse((Exception) null, System.currentTimeMillis());
        HLogger.inverse((Exception) null, System.nanoTime());
        HLogger.inverse((Exception) null, Long.MAX_VALUE);
        HLogger.inverse((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
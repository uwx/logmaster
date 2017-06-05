
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest592 {

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
    public final void test_bgWhite_Exception_long() {
        HLogger.bgWhite(new Exception(), System.currentTimeMillis());
        HLogger.bgWhite(new Exception(), System.nanoTime());
        HLogger.bgWhite(new Exception(), Long.MAX_VALUE);
        HLogger.bgWhite(new Exception(), Long.MIN_VALUE);
        HLogger.bgWhite(new Exception(), 0);
        HLogger.bgWhite((Exception) null, System.currentTimeMillis());
        HLogger.bgWhite((Exception) null, System.nanoTime());
        HLogger.bgWhite((Exception) null, Long.MAX_VALUE);
        HLogger.bgWhite((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
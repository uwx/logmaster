
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest232 {

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
    public final void test_brightRed_Exception_long() {
        HLogger.brightRed(new Exception(), System.currentTimeMillis());
        HLogger.brightRed(new Exception(), System.nanoTime());
        HLogger.brightRed(new Exception(), Long.MAX_VALUE);
        HLogger.brightRed(new Exception(), Long.MIN_VALUE);
        HLogger.brightRed(new Exception(), 0);
        HLogger.brightRed((Exception) null, System.currentTimeMillis());
        HLogger.brightRed((Exception) null, System.nanoTime());
        HLogger.brightRed((Exception) null, Long.MAX_VALUE);
        HLogger.brightRed((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
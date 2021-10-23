
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest322 {

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
    public final void test_brightWhite_Exception_long() {
        HLogger.brightWhite(new Exception(), System.currentTimeMillis());
        HLogger.brightWhite(new Exception(), System.nanoTime());
        HLogger.brightWhite(new Exception(), Long.MAX_VALUE);
        HLogger.brightWhite(new Exception(), Long.MIN_VALUE);
        HLogger.brightWhite(new Exception(), 0);
        HLogger.brightWhite((Exception) null, System.currentTimeMillis());
        HLogger.brightWhite((Exception) null, System.nanoTime());
        HLogger.brightWhite((Exception) null, Long.MAX_VALUE);
        HLogger.brightWhite((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
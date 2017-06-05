
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest142 {

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
    public final void test_underline_Exception_long() {
        HLogger.underline(new Exception(), System.currentTimeMillis());
        HLogger.underline(new Exception(), System.nanoTime());
        HLogger.underline(new Exception(), Long.MAX_VALUE);
        HLogger.underline(new Exception(), Long.MIN_VALUE);
        HLogger.underline(new Exception(), 0);
        HLogger.underline((Exception) null, System.currentTimeMillis());
        HLogger.underline((Exception) null, System.nanoTime());
        HLogger.underline((Exception) null, Long.MAX_VALUE);
        HLogger.underline((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
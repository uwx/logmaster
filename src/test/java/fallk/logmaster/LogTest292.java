
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest292 {

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
    public final void test_brightMagenta_Exception_long() {
        HLogger.brightMagenta(new Exception(), System.currentTimeMillis());
        HLogger.brightMagenta(new Exception(), System.nanoTime());
        HLogger.brightMagenta(new Exception(), Long.MAX_VALUE);
        HLogger.brightMagenta(new Exception(), Long.MIN_VALUE);
        HLogger.brightMagenta(new Exception(), 0);
        HLogger.brightMagenta((Exception) null, System.currentTimeMillis());
        HLogger.brightMagenta((Exception) null, System.nanoTime());
        HLogger.brightMagenta((Exception) null, Long.MAX_VALUE);
        HLogger.brightMagenta((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
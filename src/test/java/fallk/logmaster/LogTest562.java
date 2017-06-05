
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest562 {

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
    public final void test_bgMagenta_Exception_long() {
        HLogger.bgMagenta(new Exception(), System.currentTimeMillis());
        HLogger.bgMagenta(new Exception(), System.nanoTime());
        HLogger.bgMagenta(new Exception(), Long.MAX_VALUE);
        HLogger.bgMagenta(new Exception(), Long.MIN_VALUE);
        HLogger.bgMagenta(new Exception(), 0);
        HLogger.bgMagenta((Exception) null, System.currentTimeMillis());
        HLogger.bgMagenta((Exception) null, System.nanoTime());
        HLogger.bgMagenta((Exception) null, Long.MAX_VALUE);
        HLogger.bgMagenta((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
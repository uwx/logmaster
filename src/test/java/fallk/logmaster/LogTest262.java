
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest262 {

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
    public final void test_blue_Exception_long() {
        HLogger.blue(new Exception(), System.currentTimeMillis());
        HLogger.blue(new Exception(), System.nanoTime());
        HLogger.blue(new Exception(), Long.MAX_VALUE);
        HLogger.blue(new Exception(), Long.MIN_VALUE);
        HLogger.blue(new Exception(), 0);
        HLogger.blue((Exception) null, System.currentTimeMillis());
        HLogger.blue((Exception) null, System.nanoTime());
        HLogger.blue((Exception) null, Long.MAX_VALUE);
        HLogger.blue((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
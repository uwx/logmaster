
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest352 {

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
    public final void test_brightGray_Exception_long() {
        HLogger.brightGray(new Exception(), System.currentTimeMillis());
        HLogger.brightGray(new Exception(), System.nanoTime());
        HLogger.brightGray(new Exception(), Long.MAX_VALUE);
        HLogger.brightGray(new Exception(), Long.MIN_VALUE);
        HLogger.brightGray(new Exception(), 0);
        HLogger.brightGray((Exception) null, System.currentTimeMillis());
        HLogger.brightGray((Exception) null, System.nanoTime());
        HLogger.brightGray((Exception) null, Long.MAX_VALUE);
        HLogger.brightGray((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
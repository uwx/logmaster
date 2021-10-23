
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest367 {

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
    public final void test_brightGrey_Exception_long() {
        HLogger.brightGrey(new Exception(), System.currentTimeMillis());
        HLogger.brightGrey(new Exception(), System.nanoTime());
        HLogger.brightGrey(new Exception(), Long.MAX_VALUE);
        HLogger.brightGrey(new Exception(), Long.MIN_VALUE);
        HLogger.brightGrey(new Exception(), 0);
        HLogger.brightGrey((Exception) null, System.currentTimeMillis());
        HLogger.brightGrey((Exception) null, System.nanoTime());
        HLogger.brightGrey((Exception) null, Long.MAX_VALUE);
        HLogger.brightGrey((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
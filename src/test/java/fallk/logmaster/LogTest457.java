
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest457 {

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
    public final void test_bright_gray_Exception_long() {
        HLogger.bright_gray(new Exception(), System.currentTimeMillis());
        HLogger.bright_gray(new Exception(), System.nanoTime());
        HLogger.bright_gray(new Exception(), Long.MAX_VALUE);
        HLogger.bright_gray(new Exception(), Long.MIN_VALUE);
        HLogger.bright_gray(new Exception(), 0);
        HLogger.bright_gray((Exception) null, System.currentTimeMillis());
        HLogger.bright_gray((Exception) null, System.nanoTime());
        HLogger.bright_gray((Exception) null, Long.MAX_VALUE);
        HLogger.bright_gray((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
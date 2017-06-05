
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
    public final void test_gray_Exception_long() {
        HLogger.gray(new Exception(), System.currentTimeMillis());
        HLogger.gray(new Exception(), System.nanoTime());
        HLogger.gray(new Exception(), Long.MAX_VALUE);
        HLogger.gray(new Exception(), Long.MIN_VALUE);
        HLogger.gray(new Exception(), 0);
        HLogger.gray((Exception) null, System.currentTimeMillis());
        HLogger.gray((Exception) null, System.nanoTime());
        HLogger.gray((Exception) null, Long.MAX_VALUE);
        HLogger.gray((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest58 {

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
    public final void test_info_Exception_long() {
        HLogger.info(new Exception(), System.currentTimeMillis());
        HLogger.info(new Exception(), System.nanoTime());
        HLogger.info(new Exception(), Long.MAX_VALUE);
        HLogger.info(new Exception(), Long.MIN_VALUE);
        HLogger.info(new Exception(), 0);
        HLogger.info((Exception) null, System.currentTimeMillis());
        HLogger.info((Exception) null, System.nanoTime());
        HLogger.info((Exception) null, Long.MAX_VALUE);
        HLogger.info((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
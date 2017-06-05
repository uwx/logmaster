
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest532 {

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
    public final void test_bgYellow_Exception_long() {
        HLogger.bgYellow(new Exception(), System.currentTimeMillis());
        HLogger.bgYellow(new Exception(), System.nanoTime());
        HLogger.bgYellow(new Exception(), Long.MAX_VALUE);
        HLogger.bgYellow(new Exception(), Long.MIN_VALUE);
        HLogger.bgYellow(new Exception(), 0);
        HLogger.bgYellow((Exception) null, System.currentTimeMillis());
        HLogger.bgYellow((Exception) null, System.nanoTime());
        HLogger.bgYellow((Exception) null, Long.MAX_VALUE);
        HLogger.bgYellow((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
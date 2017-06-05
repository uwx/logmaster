
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest97 {

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
    public final void test_bold_Exception_long() {
        HLogger.bold(new Exception(), System.currentTimeMillis());
        HLogger.bold(new Exception(), System.nanoTime());
        HLogger.bold(new Exception(), Long.MAX_VALUE);
        HLogger.bold(new Exception(), Long.MIN_VALUE);
        HLogger.bold(new Exception(), 0);
        HLogger.bold((Exception) null, System.currentTimeMillis());
        HLogger.bold((Exception) null, System.nanoTime());
        HLogger.bold((Exception) null, Long.MAX_VALUE);
        HLogger.bold((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest172 {

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
    public final void test_hidden_Exception_long() {
        HLogger.hidden(new Exception(), System.currentTimeMillis());
        HLogger.hidden(new Exception(), System.nanoTime());
        HLogger.hidden(new Exception(), Long.MAX_VALUE);
        HLogger.hidden(new Exception(), Long.MIN_VALUE);
        HLogger.hidden(new Exception(), 0);
        HLogger.hidden((Exception) null, System.currentTimeMillis());
        HLogger.hidden((Exception) null, System.nanoTime());
        HLogger.hidden((Exception) null, Long.MAX_VALUE);
        HLogger.hidden((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
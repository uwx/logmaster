
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest187 {

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
    public final void test_strikethrough_Exception_long() {
        HLogger.strikethrough(new Exception(), System.currentTimeMillis());
        HLogger.strikethrough(new Exception(), System.nanoTime());
        HLogger.strikethrough(new Exception(), Long.MAX_VALUE);
        HLogger.strikethrough(new Exception(), Long.MIN_VALUE);
        HLogger.strikethrough(new Exception(), 0);
        HLogger.strikethrough((Exception) null, System.currentTimeMillis());
        HLogger.strikethrough((Exception) null, System.nanoTime());
        HLogger.strikethrough((Exception) null, Long.MAX_VALUE);
        HLogger.strikethrough((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
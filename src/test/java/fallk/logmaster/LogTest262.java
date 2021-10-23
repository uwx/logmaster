
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
    public final void test_brightYellow_Exception_long() {
        HLogger.brightYellow(new Exception(), System.currentTimeMillis());
        HLogger.brightYellow(new Exception(), System.nanoTime());
        HLogger.brightYellow(new Exception(), Long.MAX_VALUE);
        HLogger.brightYellow(new Exception(), Long.MIN_VALUE);
        HLogger.brightYellow(new Exception(), 0);
        HLogger.brightYellow((Exception) null, System.currentTimeMillis());
        HLogger.brightYellow((Exception) null, System.nanoTime());
        HLogger.brightYellow((Exception) null, Long.MAX_VALUE);
        HLogger.brightYellow((Exception) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
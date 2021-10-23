
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest258 {

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
    public final void test_brightYellow_String_long() {
        HLogger.brightYellow("Test string", System.currentTimeMillis());
        HLogger.brightYellow("Test string", System.nanoTime());
        HLogger.brightYellow("Test string", Long.MAX_VALUE);
        HLogger.brightYellow("Test string", Long.MIN_VALUE);
        HLogger.brightYellow("Test string", 0);
        HLogger.brightYellow((String) null, System.currentTimeMillis());
        HLogger.brightYellow((String) null, System.nanoTime());
        HLogger.brightYellow((String) null, Long.MAX_VALUE);
        HLogger.brightYellow((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
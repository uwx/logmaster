
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest423 {

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
    public final void test_bgYellow_String_long() {
        HLogger.bgYellow("Test string", System.currentTimeMillis());
        HLogger.bgYellow("Test string", System.nanoTime());
        HLogger.bgYellow("Test string", Long.MAX_VALUE);
        HLogger.bgYellow("Test string", Long.MIN_VALUE);
        HLogger.bgYellow("Test string", 0);
        HLogger.bgYellow((String) null, System.currentTimeMillis());
        HLogger.bgYellow((String) null, System.nanoTime());
        HLogger.bgYellow((String) null, Long.MAX_VALUE);
        HLogger.bgYellow((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest393 {

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
    public final void test_bgRed_String_long() {
        HLogger.bgRed("Test string", System.currentTimeMillis());
        HLogger.bgRed("Test string", System.nanoTime());
        HLogger.bgRed("Test string", Long.MAX_VALUE);
        HLogger.bgRed("Test string", Long.MIN_VALUE);
        HLogger.bgRed("Test string", 0);
        HLogger.bgRed((String) null, System.currentTimeMillis());
        HLogger.bgRed((String) null, System.nanoTime());
        HLogger.bgRed((String) null, Long.MAX_VALUE);
        HLogger.bgRed((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
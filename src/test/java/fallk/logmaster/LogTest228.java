
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest228 {

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
    public final void test_brightRed_String_long() {
        HLogger.brightRed("Test string", System.currentTimeMillis());
        HLogger.brightRed("Test string", System.nanoTime());
        HLogger.brightRed("Test string", Long.MAX_VALUE);
        HLogger.brightRed("Test string", Long.MIN_VALUE);
        HLogger.brightRed("Test string", 0);
        HLogger.brightRed((String) null, System.currentTimeMillis());
        HLogger.brightRed((String) null, System.nanoTime());
        HLogger.brightRed((String) null, Long.MAX_VALUE);
        HLogger.brightRed((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest183 {

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
    public final void test_strikethrough_String_long() {
        HLogger.strikethrough("Test string", System.currentTimeMillis());
        HLogger.strikethrough("Test string", System.nanoTime());
        HLogger.strikethrough("Test string", Long.MAX_VALUE);
        HLogger.strikethrough("Test string", Long.MIN_VALUE);
        HLogger.strikethrough("Test string", 0);
        HLogger.strikethrough((String) null, System.currentTimeMillis());
        HLogger.strikethrough((String) null, System.nanoTime());
        HLogger.strikethrough((String) null, Long.MAX_VALUE);
        HLogger.strikethrough((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
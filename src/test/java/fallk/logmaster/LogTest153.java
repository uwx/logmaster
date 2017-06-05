
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest153 {

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
    public final void test_inverse_String_long() {
        HLogger.inverse("Test string", System.currentTimeMillis());
        HLogger.inverse("Test string", System.nanoTime());
        HLogger.inverse("Test string", Long.MAX_VALUE);
        HLogger.inverse("Test string", Long.MIN_VALUE);
        HLogger.inverse("Test string", 0);
        HLogger.inverse((String) null, System.currentTimeMillis());
        HLogger.inverse((String) null, System.nanoTime());
        HLogger.inverse((String) null, Long.MAX_VALUE);
        HLogger.inverse((String) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
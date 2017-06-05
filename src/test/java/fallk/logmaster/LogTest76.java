
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest76 {

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
    public final void test_reset_int_String() {
        HLogger.reset(0, "Test string");
        HLogger.reset(0, (String) null);
        HLogger.reset(1, "Test string");
        HLogger.reset(1, (String) null);
        HLogger.reset(2, "Test string");
        HLogger.reset(2, (String) null);
        HLogger.reset(3, "Test string");
        HLogger.reset(3, (String) null);
        HLogger.reset(4, "Test string");
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest151 {

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
    public final void test_magenta_int_String() {
        HLogger.magenta(0, "Test string");
        HLogger.magenta(0, (String) null);
        HLogger.magenta(1, "Test string");
        HLogger.magenta(1, (String) null);
        HLogger.magenta(2, "Test string");
        HLogger.magenta(2, (String) null);
        HLogger.magenta(3, "Test string");
        HLogger.magenta(3, (String) null);
        HLogger.magenta(4, "Test string");
    }

    
    //! $CHALK_END
}
    
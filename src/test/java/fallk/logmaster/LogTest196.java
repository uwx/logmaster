
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest196 {

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
    public final void test_black_int_String() {
        HLogger.black(0, "Test string");
        HLogger.black(0, (String) null);
        HLogger.black(1, "Test string");
        HLogger.black(1, (String) null);
        HLogger.black(2, "Test string");
        HLogger.black(2, (String) null);
        HLogger.black(3, "Test string");
        HLogger.black(3, (String) null);
        HLogger.black(4, "Test string");
    }

    
    //! $CHALK_END
}
    
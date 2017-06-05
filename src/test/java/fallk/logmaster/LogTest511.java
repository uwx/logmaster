
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest511 {

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
    public final void test_bgGreen_int_String() {
        HLogger.bgGreen(0, "Test string");
        HLogger.bgGreen(0, (String) null);
        HLogger.bgGreen(1, "Test string");
        HLogger.bgGreen(1, (String) null);
        HLogger.bgGreen(2, "Test string");
        HLogger.bgGreen(2, (String) null);
        HLogger.bgGreen(3, "Test string");
        HLogger.bgGreen(3, (String) null);
        HLogger.bgGreen(4, "Test string");
    }

    
    //! $CHALK_END
}
    
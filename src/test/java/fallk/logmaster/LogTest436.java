
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest436 {

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
    public final void test_bgBlue_int_String() {
        HLogger.bgBlue(0, "Test string");
        HLogger.bgBlue(0, (String) null);
        HLogger.bgBlue(1, "Test string");
        HLogger.bgBlue(1, (String) null);
        HLogger.bgBlue(2, "Test string");
        HLogger.bgBlue(2, (String) null);
        HLogger.bgBlue(3, "Test string");
        HLogger.bgBlue(3, (String) null);
        HLogger.bgBlue(4, "Test string");
    }

    
    //! $CHALK_END
}
    
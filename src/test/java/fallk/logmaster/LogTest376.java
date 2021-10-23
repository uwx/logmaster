
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest376 {

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
    public final void test_bgBlack_int_String() {
        HLogger.bgBlack(0, "Test string");
        HLogger.bgBlack(0, (String) null);
        HLogger.bgBlack(1, "Test string");
        HLogger.bgBlack(1, (String) null);
        HLogger.bgBlack(2, "Test string");
        HLogger.bgBlack(2, (String) null);
        HLogger.bgBlack(3, "Test string");
        HLogger.bgBlack(3, (String) null);
        HLogger.bgBlack(4, "Test string");
    }

    
    //! $CHALK_END
}
    
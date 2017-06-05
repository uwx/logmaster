
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest331 {

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
    public final void test_grey_int_String() {
        HLogger.grey(0, "Test string");
        HLogger.grey(0, (String) null);
        HLogger.grey(1, "Test string");
        HLogger.grey(1, (String) null);
        HLogger.grey(2, "Test string");
        HLogger.grey(2, (String) null);
        HLogger.grey(3, "Test string");
        HLogger.grey(3, (String) null);
        HLogger.grey(4, "Test string");
    }

    
    //! $CHALK_END
}
    
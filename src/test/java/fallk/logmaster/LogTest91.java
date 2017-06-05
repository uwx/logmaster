
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest91 {

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
    public final void test_bold_int_String() {
        HLogger.bold(0, "Test string");
        HLogger.bold(0, (String) null);
        HLogger.bold(1, "Test string");
        HLogger.bold(1, (String) null);
        HLogger.bold(2, "Test string");
        HLogger.bold(2, (String) null);
        HLogger.bold(3, "Test string");
        HLogger.bold(3, (String) null);
        HLogger.bold(4, "Test string");
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest421 {

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
    public final void test_bgYellow_int_String() {
        HLogger.bgYellow(0, "Test string");
        HLogger.bgYellow(0, (String) null);
        HLogger.bgYellow(1, "Test string");
        HLogger.bgYellow(1, (String) null);
        HLogger.bgYellow(2, "Test string");
        HLogger.bgYellow(2, (String) null);
        HLogger.bgYellow(3, "Test string");
        HLogger.bgYellow(3, (String) null);
        HLogger.bgYellow(4, "Test string");
    }

    
    //! $CHALK_END
}
    
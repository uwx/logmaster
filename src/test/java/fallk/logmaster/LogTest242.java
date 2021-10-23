
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest242 {

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
    public final void test_brightGreen_int_Exception() {
        HLogger.brightGreen(0, new Exception());
        HLogger.brightGreen(0, (Exception) null);
        HLogger.brightGreen(1, new Exception());
        HLogger.brightGreen(1, (Exception) null);
        HLogger.brightGreen(2, new Exception());
        HLogger.brightGreen(2, (Exception) null);
        HLogger.brightGreen(3, new Exception());
        HLogger.brightGreen(3, (Exception) null);
        HLogger.brightGreen(4, new Exception());
    }

    
    //! $CHALK_END
}
    
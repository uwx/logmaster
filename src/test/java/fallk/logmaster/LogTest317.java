
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest317 {

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
    public final void test_brightWhite_int_Exception() {
        HLogger.brightWhite(0, new Exception());
        HLogger.brightWhite(0, (Exception) null);
        HLogger.brightWhite(1, new Exception());
        HLogger.brightWhite(1, (Exception) null);
        HLogger.brightWhite(2, new Exception());
        HLogger.brightWhite(2, (Exception) null);
        HLogger.brightWhite(3, new Exception());
        HLogger.brightWhite(3, (Exception) null);
        HLogger.brightWhite(4, new Exception());
    }

    
    //! $CHALK_END
}
    
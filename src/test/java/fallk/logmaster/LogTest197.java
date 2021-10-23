
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest197 {

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
    public final void test_gray_int_Exception() {
        HLogger.gray(0, new Exception());
        HLogger.gray(0, (Exception) null);
        HLogger.gray(1, new Exception());
        HLogger.gray(1, (Exception) null);
        HLogger.gray(2, new Exception());
        HLogger.gray(2, (Exception) null);
        HLogger.gray(3, new Exception());
        HLogger.gray(3, (Exception) null);
        HLogger.gray(4, new Exception());
    }

    
    //! $CHALK_END
}
    
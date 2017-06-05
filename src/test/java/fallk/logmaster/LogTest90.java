
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest90 {

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
    public final void test_bold_int_Object() {
        HLogger.bold(0, new Object());
        HLogger.bold(0, new Dummy());
        HLogger.bold(0, new Dummy2());
        HLogger.bold(0, (Object) null);
        HLogger.bold(1, new Object());
        HLogger.bold(1, new Dummy());
        HLogger.bold(1, new Dummy2());
        HLogger.bold(1, (Object) null);
        HLogger.bold(2, new Object());
        HLogger.bold(2, new Dummy());
        HLogger.bold(2, new Dummy2());
        HLogger.bold(2, (Object) null);
        HLogger.bold(3, new Object());
        HLogger.bold(3, new Dummy());
        HLogger.bold(3, new Dummy2());
        HLogger.bold(3, (Object) null);
        HLogger.bold(4, new Object());
        HLogger.bold(4, new Dummy());
        HLogger.bold(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
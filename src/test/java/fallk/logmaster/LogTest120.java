
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest120 {

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
    public final void test_italic_int_Object() {
        HLogger.italic(0, new Object());
        HLogger.italic(0, new Dummy());
        HLogger.italic(0, new Dummy2());
        HLogger.italic(0, (Object) null);
        HLogger.italic(1, new Object());
        HLogger.italic(1, new Dummy());
        HLogger.italic(1, new Dummy2());
        HLogger.italic(1, (Object) null);
        HLogger.italic(2, new Object());
        HLogger.italic(2, new Dummy());
        HLogger.italic(2, new Dummy2());
        HLogger.italic(2, (Object) null);
        HLogger.italic(3, new Object());
        HLogger.italic(3, new Dummy());
        HLogger.italic(3, new Dummy2());
        HLogger.italic(3, (Object) null);
        HLogger.italic(4, new Object());
        HLogger.italic(4, new Dummy());
        HLogger.italic(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    
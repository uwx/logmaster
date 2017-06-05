
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest105 {

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
    public final void test_dim_int_Object() {
        HLogger.dim(0, new Object());
        HLogger.dim(0, new Dummy());
        HLogger.dim(0, new Dummy2());
        HLogger.dim(0, (Object) null);
        HLogger.dim(1, new Object());
        HLogger.dim(1, new Dummy());
        HLogger.dim(1, new Dummy2());
        HLogger.dim(1, (Object) null);
        HLogger.dim(2, new Object());
        HLogger.dim(2, new Dummy());
        HLogger.dim(2, new Dummy2());
        HLogger.dim(2, (Object) null);
        HLogger.dim(3, new Object());
        HLogger.dim(3, new Dummy());
        HLogger.dim(3, new Dummy2());
        HLogger.dim(3, (Object) null);
        HLogger.dim(4, new Object());
        HLogger.dim(4, new Dummy());
        HLogger.dim(4, new Dummy2());
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest110 {

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
    public final void test_dim_Object_long() {
        HLogger.dim(new Object(), System.currentTimeMillis());
        HLogger.dim(new Object(), System.nanoTime());
        HLogger.dim(new Object(), Long.MAX_VALUE);
        HLogger.dim(new Object(), Long.MIN_VALUE);
        HLogger.dim(new Object(), 0);
        HLogger.dim(new Dummy(), System.currentTimeMillis());
        HLogger.dim(new Dummy(), System.nanoTime());
        HLogger.dim(new Dummy(), Long.MAX_VALUE);
        HLogger.dim(new Dummy(), Long.MIN_VALUE);
        HLogger.dim(new Dummy(), 0);
        HLogger.dim(new Dummy2(), System.currentTimeMillis());
        HLogger.dim(new Dummy2(), System.nanoTime());
        HLogger.dim(new Dummy2(), Long.MAX_VALUE);
        HLogger.dim(new Dummy2(), Long.MIN_VALUE);
        HLogger.dim(new Dummy2(), 0);
        HLogger.dim((Object) null, System.currentTimeMillis());
        HLogger.dim((Object) null, System.nanoTime());
        HLogger.dim((Object) null, Long.MAX_VALUE);
        HLogger.dim((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
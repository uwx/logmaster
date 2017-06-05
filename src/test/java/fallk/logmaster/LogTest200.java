
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest200 {

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
    public final void test_black_Object_long() {
        HLogger.black(new Object(), System.currentTimeMillis());
        HLogger.black(new Object(), System.nanoTime());
        HLogger.black(new Object(), Long.MAX_VALUE);
        HLogger.black(new Object(), Long.MIN_VALUE);
        HLogger.black(new Object(), 0);
        HLogger.black(new Dummy(), System.currentTimeMillis());
        HLogger.black(new Dummy(), System.nanoTime());
        HLogger.black(new Dummy(), Long.MAX_VALUE);
        HLogger.black(new Dummy(), Long.MIN_VALUE);
        HLogger.black(new Dummy(), 0);
        HLogger.black(new Dummy2(), System.currentTimeMillis());
        HLogger.black(new Dummy2(), System.nanoTime());
        HLogger.black(new Dummy2(), Long.MAX_VALUE);
        HLogger.black(new Dummy2(), Long.MIN_VALUE);
        HLogger.black(new Dummy2(), 0);
        HLogger.black((Object) null, System.currentTimeMillis());
        HLogger.black((Object) null, System.nanoTime());
        HLogger.black((Object) null, Long.MAX_VALUE);
        HLogger.black((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
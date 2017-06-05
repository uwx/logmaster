
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest515 {

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
    public final void test_bgGreen_Object_long() {
        HLogger.bgGreen(new Object(), System.currentTimeMillis());
        HLogger.bgGreen(new Object(), System.nanoTime());
        HLogger.bgGreen(new Object(), Long.MAX_VALUE);
        HLogger.bgGreen(new Object(), Long.MIN_VALUE);
        HLogger.bgGreen(new Object(), 0);
        HLogger.bgGreen(new Dummy(), System.currentTimeMillis());
        HLogger.bgGreen(new Dummy(), System.nanoTime());
        HLogger.bgGreen(new Dummy(), Long.MAX_VALUE);
        HLogger.bgGreen(new Dummy(), Long.MIN_VALUE);
        HLogger.bgGreen(new Dummy(), 0);
        HLogger.bgGreen(new Dummy2(), System.currentTimeMillis());
        HLogger.bgGreen(new Dummy2(), System.nanoTime());
        HLogger.bgGreen(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgGreen(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgGreen(new Dummy2(), 0);
        HLogger.bgGreen((Object) null, System.currentTimeMillis());
        HLogger.bgGreen((Object) null, System.nanoTime());
        HLogger.bgGreen((Object) null, Long.MAX_VALUE);
        HLogger.bgGreen((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
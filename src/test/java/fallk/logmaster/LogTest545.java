
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest545 {

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
    public final void test_bgBlue_Object_long() {
        HLogger.bgBlue(new Object(), System.currentTimeMillis());
        HLogger.bgBlue(new Object(), System.nanoTime());
        HLogger.bgBlue(new Object(), Long.MAX_VALUE);
        HLogger.bgBlue(new Object(), Long.MIN_VALUE);
        HLogger.bgBlue(new Object(), 0);
        HLogger.bgBlue(new Dummy(), System.currentTimeMillis());
        HLogger.bgBlue(new Dummy(), System.nanoTime());
        HLogger.bgBlue(new Dummy(), Long.MAX_VALUE);
        HLogger.bgBlue(new Dummy(), Long.MIN_VALUE);
        HLogger.bgBlue(new Dummy(), 0);
        HLogger.bgBlue(new Dummy2(), System.currentTimeMillis());
        HLogger.bgBlue(new Dummy2(), System.nanoTime());
        HLogger.bgBlue(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgBlue(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgBlue(new Dummy2(), 0);
        HLogger.bgBlue((Object) null, System.currentTimeMillis());
        HLogger.bgBlue((Object) null, System.nanoTime());
        HLogger.bgBlue((Object) null, Long.MAX_VALUE);
        HLogger.bgBlue((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
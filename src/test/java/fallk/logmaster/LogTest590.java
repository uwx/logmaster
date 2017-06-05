
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest590 {

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
    public final void test_bgWhite_Object_long() {
        HLogger.bgWhite(new Object(), System.currentTimeMillis());
        HLogger.bgWhite(new Object(), System.nanoTime());
        HLogger.bgWhite(new Object(), Long.MAX_VALUE);
        HLogger.bgWhite(new Object(), Long.MIN_VALUE);
        HLogger.bgWhite(new Object(), 0);
        HLogger.bgWhite(new Dummy(), System.currentTimeMillis());
        HLogger.bgWhite(new Dummy(), System.nanoTime());
        HLogger.bgWhite(new Dummy(), Long.MAX_VALUE);
        HLogger.bgWhite(new Dummy(), Long.MIN_VALUE);
        HLogger.bgWhite(new Dummy(), 0);
        HLogger.bgWhite(new Dummy2(), System.currentTimeMillis());
        HLogger.bgWhite(new Dummy2(), System.nanoTime());
        HLogger.bgWhite(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgWhite(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgWhite(new Dummy2(), 0);
        HLogger.bgWhite((Object) null, System.currentTimeMillis());
        HLogger.bgWhite((Object) null, System.nanoTime());
        HLogger.bgWhite((Object) null, Long.MAX_VALUE);
        HLogger.bgWhite((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
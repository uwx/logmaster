
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest455 {

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
    public final void test_bgMagenta_Object_long() {
        HLogger.bgMagenta(new Object(), System.currentTimeMillis());
        HLogger.bgMagenta(new Object(), System.nanoTime());
        HLogger.bgMagenta(new Object(), Long.MAX_VALUE);
        HLogger.bgMagenta(new Object(), Long.MIN_VALUE);
        HLogger.bgMagenta(new Object(), 0);
        HLogger.bgMagenta(new Dummy(), System.currentTimeMillis());
        HLogger.bgMagenta(new Dummy(), System.nanoTime());
        HLogger.bgMagenta(new Dummy(), Long.MAX_VALUE);
        HLogger.bgMagenta(new Dummy(), Long.MIN_VALUE);
        HLogger.bgMagenta(new Dummy(), 0);
        HLogger.bgMagenta(new Dummy2(), System.currentTimeMillis());
        HLogger.bgMagenta(new Dummy2(), System.nanoTime());
        HLogger.bgMagenta(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgMagenta(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgMagenta(new Dummy2(), 0);
        HLogger.bgMagenta((Object) null, System.currentTimeMillis());
        HLogger.bgMagenta((Object) null, System.nanoTime());
        HLogger.bgMagenta((Object) null, Long.MAX_VALUE);
        HLogger.bgMagenta((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
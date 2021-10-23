
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest290 {

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
    public final void test_brightMagenta_Object_long() {
        HLogger.brightMagenta(new Object(), System.currentTimeMillis());
        HLogger.brightMagenta(new Object(), System.nanoTime());
        HLogger.brightMagenta(new Object(), Long.MAX_VALUE);
        HLogger.brightMagenta(new Object(), Long.MIN_VALUE);
        HLogger.brightMagenta(new Object(), 0);
        HLogger.brightMagenta(new Dummy(), System.currentTimeMillis());
        HLogger.brightMagenta(new Dummy(), System.nanoTime());
        HLogger.brightMagenta(new Dummy(), Long.MAX_VALUE);
        HLogger.brightMagenta(new Dummy(), Long.MIN_VALUE);
        HLogger.brightMagenta(new Dummy(), 0);
        HLogger.brightMagenta(new Dummy2(), System.currentTimeMillis());
        HLogger.brightMagenta(new Dummy2(), System.nanoTime());
        HLogger.brightMagenta(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightMagenta(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightMagenta(new Dummy2(), 0);
        HLogger.brightMagenta((Object) null, System.currentTimeMillis());
        HLogger.brightMagenta((Object) null, System.nanoTime());
        HLogger.brightMagenta((Object) null, Long.MAX_VALUE);
        HLogger.brightMagenta((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
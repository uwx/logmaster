
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest245 {

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
    public final void test_brightGreen_Object_long() {
        HLogger.brightGreen(new Object(), System.currentTimeMillis());
        HLogger.brightGreen(new Object(), System.nanoTime());
        HLogger.brightGreen(new Object(), Long.MAX_VALUE);
        HLogger.brightGreen(new Object(), Long.MIN_VALUE);
        HLogger.brightGreen(new Object(), 0);
        HLogger.brightGreen(new Dummy(), System.currentTimeMillis());
        HLogger.brightGreen(new Dummy(), System.nanoTime());
        HLogger.brightGreen(new Dummy(), Long.MAX_VALUE);
        HLogger.brightGreen(new Dummy(), Long.MIN_VALUE);
        HLogger.brightGreen(new Dummy(), 0);
        HLogger.brightGreen(new Dummy2(), System.currentTimeMillis());
        HLogger.brightGreen(new Dummy2(), System.nanoTime());
        HLogger.brightGreen(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightGreen(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightGreen(new Dummy2(), 0);
        HLogger.brightGreen((Object) null, System.currentTimeMillis());
        HLogger.brightGreen((Object) null, System.nanoTime());
        HLogger.brightGreen((Object) null, Long.MAX_VALUE);
        HLogger.brightGreen((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
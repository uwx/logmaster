
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest320 {

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
    public final void test_brightWhite_Object_long() {
        HLogger.brightWhite(new Object(), System.currentTimeMillis());
        HLogger.brightWhite(new Object(), System.nanoTime());
        HLogger.brightWhite(new Object(), Long.MAX_VALUE);
        HLogger.brightWhite(new Object(), Long.MIN_VALUE);
        HLogger.brightWhite(new Object(), 0);
        HLogger.brightWhite(new Dummy(), System.currentTimeMillis());
        HLogger.brightWhite(new Dummy(), System.nanoTime());
        HLogger.brightWhite(new Dummy(), Long.MAX_VALUE);
        HLogger.brightWhite(new Dummy(), Long.MIN_VALUE);
        HLogger.brightWhite(new Dummy(), 0);
        HLogger.brightWhite(new Dummy2(), System.currentTimeMillis());
        HLogger.brightWhite(new Dummy2(), System.nanoTime());
        HLogger.brightWhite(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightWhite(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightWhite(new Dummy2(), 0);
        HLogger.brightWhite((Object) null, System.currentTimeMillis());
        HLogger.brightWhite((Object) null, System.nanoTime());
        HLogger.brightWhite((Object) null, Long.MAX_VALUE);
        HLogger.brightWhite((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest275 {

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
    public final void test_brightBlue_Object_long() {
        HLogger.brightBlue(new Object(), System.currentTimeMillis());
        HLogger.brightBlue(new Object(), System.nanoTime());
        HLogger.brightBlue(new Object(), Long.MAX_VALUE);
        HLogger.brightBlue(new Object(), Long.MIN_VALUE);
        HLogger.brightBlue(new Object(), 0);
        HLogger.brightBlue(new Dummy(), System.currentTimeMillis());
        HLogger.brightBlue(new Dummy(), System.nanoTime());
        HLogger.brightBlue(new Dummy(), Long.MAX_VALUE);
        HLogger.brightBlue(new Dummy(), Long.MIN_VALUE);
        HLogger.brightBlue(new Dummy(), 0);
        HLogger.brightBlue(new Dummy2(), System.currentTimeMillis());
        HLogger.brightBlue(new Dummy2(), System.nanoTime());
        HLogger.brightBlue(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightBlue(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightBlue(new Dummy2(), 0);
        HLogger.brightBlue((Object) null, System.currentTimeMillis());
        HLogger.brightBlue((Object) null, System.nanoTime());
        HLogger.brightBlue((Object) null, Long.MAX_VALUE);
        HLogger.brightBlue((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
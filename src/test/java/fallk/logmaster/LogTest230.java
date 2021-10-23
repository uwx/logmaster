
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest230 {

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
    public final void test_brightRed_Object_long() {
        HLogger.brightRed(new Object(), System.currentTimeMillis());
        HLogger.brightRed(new Object(), System.nanoTime());
        HLogger.brightRed(new Object(), Long.MAX_VALUE);
        HLogger.brightRed(new Object(), Long.MIN_VALUE);
        HLogger.brightRed(new Object(), 0);
        HLogger.brightRed(new Dummy(), System.currentTimeMillis());
        HLogger.brightRed(new Dummy(), System.nanoTime());
        HLogger.brightRed(new Dummy(), Long.MAX_VALUE);
        HLogger.brightRed(new Dummy(), Long.MIN_VALUE);
        HLogger.brightRed(new Dummy(), 0);
        HLogger.brightRed(new Dummy2(), System.currentTimeMillis());
        HLogger.brightRed(new Dummy2(), System.nanoTime());
        HLogger.brightRed(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightRed(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightRed(new Dummy2(), 0);
        HLogger.brightRed((Object) null, System.currentTimeMillis());
        HLogger.brightRed((Object) null, System.nanoTime());
        HLogger.brightRed((Object) null, Long.MAX_VALUE);
        HLogger.brightRed((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
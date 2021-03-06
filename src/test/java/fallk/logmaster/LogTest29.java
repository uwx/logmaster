
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest29 {

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
    public final void test_severe_Object_long() {
        HLogger.severe(new Object(), System.currentTimeMillis());
        HLogger.severe(new Object(), System.nanoTime());
        HLogger.severe(new Object(), Long.MAX_VALUE);
        HLogger.severe(new Object(), Long.MIN_VALUE);
        HLogger.severe(new Object(), 0);
        HLogger.severe(new Dummy(), System.currentTimeMillis());
        HLogger.severe(new Dummy(), System.nanoTime());
        HLogger.severe(new Dummy(), Long.MAX_VALUE);
        HLogger.severe(new Dummy(), Long.MIN_VALUE);
        HLogger.severe(new Dummy(), 0);
        HLogger.severe(new Dummy2(), System.currentTimeMillis());
        HLogger.severe(new Dummy2(), System.nanoTime());
        HLogger.severe(new Dummy2(), Long.MAX_VALUE);
        HLogger.severe(new Dummy2(), Long.MIN_VALUE);
        HLogger.severe(new Dummy2(), 0);
        HLogger.severe((Object) null, System.currentTimeMillis());
        HLogger.severe((Object) null, System.nanoTime());
        HLogger.severe((Object) null, Long.MAX_VALUE);
        HLogger.severe((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
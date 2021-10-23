
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest125 {

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
    public final void test_yellow_Object_long() {
        HLogger.yellow(new Object(), System.currentTimeMillis());
        HLogger.yellow(new Object(), System.nanoTime());
        HLogger.yellow(new Object(), Long.MAX_VALUE);
        HLogger.yellow(new Object(), Long.MIN_VALUE);
        HLogger.yellow(new Object(), 0);
        HLogger.yellow(new Dummy(), System.currentTimeMillis());
        HLogger.yellow(new Dummy(), System.nanoTime());
        HLogger.yellow(new Dummy(), Long.MAX_VALUE);
        HLogger.yellow(new Dummy(), Long.MIN_VALUE);
        HLogger.yellow(new Dummy(), 0);
        HLogger.yellow(new Dummy2(), System.currentTimeMillis());
        HLogger.yellow(new Dummy2(), System.nanoTime());
        HLogger.yellow(new Dummy2(), Long.MAX_VALUE);
        HLogger.yellow(new Dummy2(), Long.MIN_VALUE);
        HLogger.yellow(new Dummy2(), 0);
        HLogger.yellow((Object) null, System.currentTimeMillis());
        HLogger.yellow((Object) null, System.nanoTime());
        HLogger.yellow((Object) null, Long.MAX_VALUE);
        HLogger.yellow((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
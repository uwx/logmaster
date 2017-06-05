
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest38 {

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
    public final void test_fatal_Object_long() {
        HLogger.fatal(new Object(), System.currentTimeMillis());
        HLogger.fatal(new Object(), System.nanoTime());
        HLogger.fatal(new Object(), Long.MAX_VALUE);
        HLogger.fatal(new Object(), Long.MIN_VALUE);
        HLogger.fatal(new Object(), 0);
        HLogger.fatal(new Dummy(), System.currentTimeMillis());
        HLogger.fatal(new Dummy(), System.nanoTime());
        HLogger.fatal(new Dummy(), Long.MAX_VALUE);
        HLogger.fatal(new Dummy(), Long.MIN_VALUE);
        HLogger.fatal(new Dummy(), 0);
        HLogger.fatal(new Dummy2(), System.currentTimeMillis());
        HLogger.fatal(new Dummy2(), System.nanoTime());
        HLogger.fatal(new Dummy2(), Long.MAX_VALUE);
        HLogger.fatal(new Dummy2(), Long.MIN_VALUE);
        HLogger.fatal(new Dummy2(), 0);
        HLogger.fatal((Object) null, System.currentTimeMillis());
        HLogger.fatal((Object) null, System.nanoTime());
        HLogger.fatal((Object) null, Long.MAX_VALUE);
        HLogger.fatal((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
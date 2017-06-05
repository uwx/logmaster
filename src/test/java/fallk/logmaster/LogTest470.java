
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest470 {

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
    public final void test_bright_grey_Object_long() {
        HLogger.bright_grey(new Object(), System.currentTimeMillis());
        HLogger.bright_grey(new Object(), System.nanoTime());
        HLogger.bright_grey(new Object(), Long.MAX_VALUE);
        HLogger.bright_grey(new Object(), Long.MIN_VALUE);
        HLogger.bright_grey(new Object(), 0);
        HLogger.bright_grey(new Dummy(), System.currentTimeMillis());
        HLogger.bright_grey(new Dummy(), System.nanoTime());
        HLogger.bright_grey(new Dummy(), Long.MAX_VALUE);
        HLogger.bright_grey(new Dummy(), Long.MIN_VALUE);
        HLogger.bright_grey(new Dummy(), 0);
        HLogger.bright_grey(new Dummy2(), System.currentTimeMillis());
        HLogger.bright_grey(new Dummy2(), System.nanoTime());
        HLogger.bright_grey(new Dummy2(), Long.MAX_VALUE);
        HLogger.bright_grey(new Dummy2(), Long.MIN_VALUE);
        HLogger.bright_grey(new Dummy2(), 0);
        HLogger.bright_grey((Object) null, System.currentTimeMillis());
        HLogger.bright_grey((Object) null, System.nanoTime());
        HLogger.bright_grey((Object) null, Long.MAX_VALUE);
        HLogger.bright_grey((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
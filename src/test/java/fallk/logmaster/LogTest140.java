
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest140 {

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
    public final void test_underline_Object_long() {
        HLogger.underline(new Object(), System.currentTimeMillis());
        HLogger.underline(new Object(), System.nanoTime());
        HLogger.underline(new Object(), Long.MAX_VALUE);
        HLogger.underline(new Object(), Long.MIN_VALUE);
        HLogger.underline(new Object(), 0);
        HLogger.underline(new Dummy(), System.currentTimeMillis());
        HLogger.underline(new Dummy(), System.nanoTime());
        HLogger.underline(new Dummy(), Long.MAX_VALUE);
        HLogger.underline(new Dummy(), Long.MIN_VALUE);
        HLogger.underline(new Dummy(), 0);
        HLogger.underline(new Dummy2(), System.currentTimeMillis());
        HLogger.underline(new Dummy2(), System.nanoTime());
        HLogger.underline(new Dummy2(), Long.MAX_VALUE);
        HLogger.underline(new Dummy2(), Long.MIN_VALUE);
        HLogger.underline(new Dummy2(), 0);
        HLogger.underline((Object) null, System.currentTimeMillis());
        HLogger.underline((Object) null, System.nanoTime());
        HLogger.underline((Object) null, Long.MAX_VALUE);
        HLogger.underline((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    

    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest260 {

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
    public final void test_blue_Object_long() {
        HLogger.blue(new Object(), System.currentTimeMillis());
        HLogger.blue(new Object(), System.nanoTime());
        HLogger.blue(new Object(), Long.MAX_VALUE);
        HLogger.blue(new Object(), Long.MIN_VALUE);
        HLogger.blue(new Object(), 0);
        HLogger.blue(new Dummy(), System.currentTimeMillis());
        HLogger.blue(new Dummy(), System.nanoTime());
        HLogger.blue(new Dummy(), Long.MAX_VALUE);
        HLogger.blue(new Dummy(), Long.MIN_VALUE);
        HLogger.blue(new Dummy(), 0);
        HLogger.blue(new Dummy2(), System.currentTimeMillis());
        HLogger.blue(new Dummy2(), System.nanoTime());
        HLogger.blue(new Dummy2(), Long.MAX_VALUE);
        HLogger.blue(new Dummy2(), Long.MIN_VALUE);
        HLogger.blue(new Dummy2(), 0);
        HLogger.blue((Object) null, System.currentTimeMillis());
        HLogger.blue((Object) null, System.nanoTime());
        HLogger.blue((Object) null, Long.MAX_VALUE);
        HLogger.blue((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
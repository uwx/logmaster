
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
    public final void test_italic_Object_long() {
        HLogger.italic(new Object(), System.currentTimeMillis());
        HLogger.italic(new Object(), System.nanoTime());
        HLogger.italic(new Object(), Long.MAX_VALUE);
        HLogger.italic(new Object(), Long.MIN_VALUE);
        HLogger.italic(new Object(), 0);
        HLogger.italic(new Dummy(), System.currentTimeMillis());
        HLogger.italic(new Dummy(), System.nanoTime());
        HLogger.italic(new Dummy(), Long.MAX_VALUE);
        HLogger.italic(new Dummy(), Long.MIN_VALUE);
        HLogger.italic(new Dummy(), 0);
        HLogger.italic(new Dummy2(), System.currentTimeMillis());
        HLogger.italic(new Dummy2(), System.nanoTime());
        HLogger.italic(new Dummy2(), Long.MAX_VALUE);
        HLogger.italic(new Dummy2(), Long.MIN_VALUE);
        HLogger.italic(new Dummy2(), 0);
        HLogger.italic((Object) null, System.currentTimeMillis());
        HLogger.italic((Object) null, System.nanoTime());
        HLogger.italic((Object) null, Long.MAX_VALUE);
        HLogger.italic((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
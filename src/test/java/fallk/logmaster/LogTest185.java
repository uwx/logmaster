
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest185 {

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
    public final void test_strikethrough_Object_long() {
        HLogger.strikethrough(new Object(), System.currentTimeMillis());
        HLogger.strikethrough(new Object(), System.nanoTime());
        HLogger.strikethrough(new Object(), Long.MAX_VALUE);
        HLogger.strikethrough(new Object(), Long.MIN_VALUE);
        HLogger.strikethrough(new Object(), 0);
        HLogger.strikethrough(new Dummy(), System.currentTimeMillis());
        HLogger.strikethrough(new Dummy(), System.nanoTime());
        HLogger.strikethrough(new Dummy(), Long.MAX_VALUE);
        HLogger.strikethrough(new Dummy(), Long.MIN_VALUE);
        HLogger.strikethrough(new Dummy(), 0);
        HLogger.strikethrough(new Dummy2(), System.currentTimeMillis());
        HLogger.strikethrough(new Dummy2(), System.nanoTime());
        HLogger.strikethrough(new Dummy2(), Long.MAX_VALUE);
        HLogger.strikethrough(new Dummy2(), Long.MIN_VALUE);
        HLogger.strikethrough(new Dummy2(), 0);
        HLogger.strikethrough((Object) null, System.currentTimeMillis());
        HLogger.strikethrough((Object) null, System.nanoTime());
        HLogger.strikethrough((Object) null, Long.MAX_VALUE);
        HLogger.strikethrough((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
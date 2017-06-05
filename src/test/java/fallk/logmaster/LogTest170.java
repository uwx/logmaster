
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest170 {

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
    public final void test_hidden_Object_long() {
        HLogger.hidden(new Object(), System.currentTimeMillis());
        HLogger.hidden(new Object(), System.nanoTime());
        HLogger.hidden(new Object(), Long.MAX_VALUE);
        HLogger.hidden(new Object(), Long.MIN_VALUE);
        HLogger.hidden(new Object(), 0);
        HLogger.hidden(new Dummy(), System.currentTimeMillis());
        HLogger.hidden(new Dummy(), System.nanoTime());
        HLogger.hidden(new Dummy(), Long.MAX_VALUE);
        HLogger.hidden(new Dummy(), Long.MIN_VALUE);
        HLogger.hidden(new Dummy(), 0);
        HLogger.hidden(new Dummy2(), System.currentTimeMillis());
        HLogger.hidden(new Dummy2(), System.nanoTime());
        HLogger.hidden(new Dummy2(), Long.MAX_VALUE);
        HLogger.hidden(new Dummy2(), Long.MIN_VALUE);
        HLogger.hidden(new Dummy2(), 0);
        HLogger.hidden((Object) null, System.currentTimeMillis());
        HLogger.hidden((Object) null, System.nanoTime());
        HLogger.hidden((Object) null, Long.MAX_VALUE);
        HLogger.hidden((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
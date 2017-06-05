
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest95 {

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
    public final void test_bold_Object_long() {
        HLogger.bold(new Object(), System.currentTimeMillis());
        HLogger.bold(new Object(), System.nanoTime());
        HLogger.bold(new Object(), Long.MAX_VALUE);
        HLogger.bold(new Object(), Long.MIN_VALUE);
        HLogger.bold(new Object(), 0);
        HLogger.bold(new Dummy(), System.currentTimeMillis());
        HLogger.bold(new Dummy(), System.nanoTime());
        HLogger.bold(new Dummy(), Long.MAX_VALUE);
        HLogger.bold(new Dummy(), Long.MIN_VALUE);
        HLogger.bold(new Dummy(), 0);
        HLogger.bold(new Dummy2(), System.currentTimeMillis());
        HLogger.bold(new Dummy2(), System.nanoTime());
        HLogger.bold(new Dummy2(), Long.MAX_VALUE);
        HLogger.bold(new Dummy2(), Long.MIN_VALUE);
        HLogger.bold(new Dummy2(), 0);
        HLogger.bold((Object) null, System.currentTimeMillis());
        HLogger.bold((Object) null, System.nanoTime());
        HLogger.bold((Object) null, Long.MAX_VALUE);
        HLogger.bold((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
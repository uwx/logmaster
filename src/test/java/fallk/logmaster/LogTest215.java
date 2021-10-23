
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest215 {

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
    public final void test_grey_Object_long() {
        HLogger.grey(new Object(), System.currentTimeMillis());
        HLogger.grey(new Object(), System.nanoTime());
        HLogger.grey(new Object(), Long.MAX_VALUE);
        HLogger.grey(new Object(), Long.MIN_VALUE);
        HLogger.grey(new Object(), 0);
        HLogger.grey(new Dummy(), System.currentTimeMillis());
        HLogger.grey(new Dummy(), System.nanoTime());
        HLogger.grey(new Dummy(), Long.MAX_VALUE);
        HLogger.grey(new Dummy(), Long.MIN_VALUE);
        HLogger.grey(new Dummy(), 0);
        HLogger.grey(new Dummy2(), System.currentTimeMillis());
        HLogger.grey(new Dummy2(), System.nanoTime());
        HLogger.grey(new Dummy2(), Long.MAX_VALUE);
        HLogger.grey(new Dummy2(), Long.MIN_VALUE);
        HLogger.grey(new Dummy2(), 0);
        HLogger.grey((Object) null, System.currentTimeMillis());
        HLogger.grey((Object) null, System.nanoTime());
        HLogger.grey((Object) null, Long.MAX_VALUE);
        HLogger.grey((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
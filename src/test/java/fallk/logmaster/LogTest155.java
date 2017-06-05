
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest155 {

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
    public final void test_inverse_Object_long() {
        HLogger.inverse(new Object(), System.currentTimeMillis());
        HLogger.inverse(new Object(), System.nanoTime());
        HLogger.inverse(new Object(), Long.MAX_VALUE);
        HLogger.inverse(new Object(), Long.MIN_VALUE);
        HLogger.inverse(new Object(), 0);
        HLogger.inverse(new Dummy(), System.currentTimeMillis());
        HLogger.inverse(new Dummy(), System.nanoTime());
        HLogger.inverse(new Dummy(), Long.MAX_VALUE);
        HLogger.inverse(new Dummy(), Long.MIN_VALUE);
        HLogger.inverse(new Dummy(), 0);
        HLogger.inverse(new Dummy2(), System.currentTimeMillis());
        HLogger.inverse(new Dummy2(), System.nanoTime());
        HLogger.inverse(new Dummy2(), Long.MAX_VALUE);
        HLogger.inverse(new Dummy2(), Long.MIN_VALUE);
        HLogger.inverse(new Dummy2(), 0);
        HLogger.inverse((Object) null, System.currentTimeMillis());
        HLogger.inverse((Object) null, System.nanoTime());
        HLogger.inverse((Object) null, Long.MAX_VALUE);
        HLogger.inverse((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
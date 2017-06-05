
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest11 {

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
    public final void test_log_Object_long() {
        HLogger.log(new Object(), System.currentTimeMillis());
        HLogger.log(new Object(), System.nanoTime());
        HLogger.log(new Object(), Long.MAX_VALUE);
        HLogger.log(new Object(), Long.MIN_VALUE);
        HLogger.log(new Object(), 0);
        HLogger.log(new Dummy(), System.currentTimeMillis());
        HLogger.log(new Dummy(), System.nanoTime());
        HLogger.log(new Dummy(), Long.MAX_VALUE);
        HLogger.log(new Dummy(), Long.MIN_VALUE);
        HLogger.log(new Dummy(), 0);
        HLogger.log(new Dummy2(), System.currentTimeMillis());
        HLogger.log(new Dummy2(), System.nanoTime());
        HLogger.log(new Dummy2(), Long.MAX_VALUE);
        HLogger.log(new Dummy2(), Long.MIN_VALUE);
        HLogger.log(new Dummy2(), 0);
        HLogger.log((Object) null, System.currentTimeMillis());
        HLogger.log((Object) null, System.nanoTime());
        HLogger.log((Object) null, Long.MAX_VALUE);
        HLogger.log((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
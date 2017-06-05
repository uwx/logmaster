
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest53 {

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
    public final void test_info_Object_long_boolean() {
        HLogger.info(new Object(), System.currentTimeMillis(), true);
        HLogger.info(new Object(), System.currentTimeMillis(), false);
        HLogger.info(new Object(), System.nanoTime(), true);
        HLogger.info(new Object(), System.nanoTime(), false);
        HLogger.info(new Object(), Long.MAX_VALUE, true);
        HLogger.info(new Object(), Long.MAX_VALUE, false);
        HLogger.info(new Object(), Long.MIN_VALUE, true);
        HLogger.info(new Object(), Long.MIN_VALUE, false);
        HLogger.info(new Object(), 0, true);
        HLogger.info(new Object(), 0, false);
        HLogger.info(new Dummy(), System.currentTimeMillis(), true);
        HLogger.info(new Dummy(), System.currentTimeMillis(), false);
        HLogger.info(new Dummy(), System.nanoTime(), true);
        HLogger.info(new Dummy(), System.nanoTime(), false);
        HLogger.info(new Dummy(), Long.MAX_VALUE, true);
        HLogger.info(new Dummy(), Long.MAX_VALUE, false);
        HLogger.info(new Dummy(), Long.MIN_VALUE, true);
        HLogger.info(new Dummy(), Long.MIN_VALUE, false);
        HLogger.info(new Dummy(), 0, true);
        HLogger.info(new Dummy(), 0, false);
        HLogger.info(new Dummy2(), System.currentTimeMillis(), true);
        HLogger.info(new Dummy2(), System.currentTimeMillis(), false);
        HLogger.info(new Dummy2(), System.nanoTime(), true);
        HLogger.info(new Dummy2(), System.nanoTime(), false);
        HLogger.info(new Dummy2(), Long.MAX_VALUE, true);
        HLogger.info(new Dummy2(), Long.MAX_VALUE, false);
        HLogger.info(new Dummy2(), Long.MIN_VALUE, true);
        HLogger.info(new Dummy2(), Long.MIN_VALUE, false);
        HLogger.info(new Dummy2(), 0, true);
        HLogger.info(new Dummy2(), 0, false);
        HLogger.info((Object) null, System.currentTimeMillis(), true);
        HLogger.info((Object) null, System.currentTimeMillis(), false);
        HLogger.info((Object) null, System.nanoTime(), true);
        HLogger.info((Object) null, System.nanoTime(), false);
        HLogger.info((Object) null, Long.MAX_VALUE, true);
        HLogger.info((Object) null, Long.MAX_VALUE, false);
        HLogger.info((Object) null, Long.MIN_VALUE, true);
        HLogger.info((Object) null, Long.MIN_VALUE, false);
        HLogger.info((Object) null, 0, true);
    }

    
    //! $CHALK_END
}
    
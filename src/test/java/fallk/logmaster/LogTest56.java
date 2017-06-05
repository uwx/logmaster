
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest56 {

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
    public final void test_info_Object_long() {
        HLogger.info(new Object(), System.currentTimeMillis());
        HLogger.info(new Object(), System.nanoTime());
        HLogger.info(new Object(), Long.MAX_VALUE);
        HLogger.info(new Object(), Long.MIN_VALUE);
        HLogger.info(new Object(), 0);
        HLogger.info(new Dummy(), System.currentTimeMillis());
        HLogger.info(new Dummy(), System.nanoTime());
        HLogger.info(new Dummy(), Long.MAX_VALUE);
        HLogger.info(new Dummy(), Long.MIN_VALUE);
        HLogger.info(new Dummy(), 0);
        HLogger.info(new Dummy2(), System.currentTimeMillis());
        HLogger.info(new Dummy2(), System.nanoTime());
        HLogger.info(new Dummy2(), Long.MAX_VALUE);
        HLogger.info(new Dummy2(), Long.MIN_VALUE);
        HLogger.info(new Dummy2(), 0);
        HLogger.info((Object) null, System.currentTimeMillis());
        HLogger.info((Object) null, System.nanoTime());
        HLogger.info((Object) null, Long.MAX_VALUE);
        HLogger.info((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
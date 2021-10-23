
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest350 {

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
    public final void test_brightGray_Object_long() {
        HLogger.brightGray(new Object(), System.currentTimeMillis());
        HLogger.brightGray(new Object(), System.nanoTime());
        HLogger.brightGray(new Object(), Long.MAX_VALUE);
        HLogger.brightGray(new Object(), Long.MIN_VALUE);
        HLogger.brightGray(new Object(), 0);
        HLogger.brightGray(new Dummy(), System.currentTimeMillis());
        HLogger.brightGray(new Dummy(), System.nanoTime());
        HLogger.brightGray(new Dummy(), Long.MAX_VALUE);
        HLogger.brightGray(new Dummy(), Long.MIN_VALUE);
        HLogger.brightGray(new Dummy(), 0);
        HLogger.brightGray(new Dummy2(), System.currentTimeMillis());
        HLogger.brightGray(new Dummy2(), System.nanoTime());
        HLogger.brightGray(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightGray(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightGray(new Dummy2(), 0);
        HLogger.brightGray((Object) null, System.currentTimeMillis());
        HLogger.brightGray((Object) null, System.nanoTime());
        HLogger.brightGray((Object) null, Long.MAX_VALUE);
        HLogger.brightGray((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
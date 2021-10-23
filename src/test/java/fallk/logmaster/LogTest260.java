
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest260 {

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
    public final void test_brightYellow_Object_long() {
        HLogger.brightYellow(new Object(), System.currentTimeMillis());
        HLogger.brightYellow(new Object(), System.nanoTime());
        HLogger.brightYellow(new Object(), Long.MAX_VALUE);
        HLogger.brightYellow(new Object(), Long.MIN_VALUE);
        HLogger.brightYellow(new Object(), 0);
        HLogger.brightYellow(new Dummy(), System.currentTimeMillis());
        HLogger.brightYellow(new Dummy(), System.nanoTime());
        HLogger.brightYellow(new Dummy(), Long.MAX_VALUE);
        HLogger.brightYellow(new Dummy(), Long.MIN_VALUE);
        HLogger.brightYellow(new Dummy(), 0);
        HLogger.brightYellow(new Dummy2(), System.currentTimeMillis());
        HLogger.brightYellow(new Dummy2(), System.nanoTime());
        HLogger.brightYellow(new Dummy2(), Long.MAX_VALUE);
        HLogger.brightYellow(new Dummy2(), Long.MIN_VALUE);
        HLogger.brightYellow(new Dummy2(), 0);
        HLogger.brightYellow((Object) null, System.currentTimeMillis());
        HLogger.brightYellow((Object) null, System.nanoTime());
        HLogger.brightYellow((Object) null, Long.MAX_VALUE);
        HLogger.brightYellow((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
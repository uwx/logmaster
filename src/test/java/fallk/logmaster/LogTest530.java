
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest530 {

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
    public final void test_bgYellow_Object_long() {
        HLogger.bgYellow(new Object(), System.currentTimeMillis());
        HLogger.bgYellow(new Object(), System.nanoTime());
        HLogger.bgYellow(new Object(), Long.MAX_VALUE);
        HLogger.bgYellow(new Object(), Long.MIN_VALUE);
        HLogger.bgYellow(new Object(), 0);
        HLogger.bgYellow(new Dummy(), System.currentTimeMillis());
        HLogger.bgYellow(new Dummy(), System.nanoTime());
        HLogger.bgYellow(new Dummy(), Long.MAX_VALUE);
        HLogger.bgYellow(new Dummy(), Long.MIN_VALUE);
        HLogger.bgYellow(new Dummy(), 0);
        HLogger.bgYellow(new Dummy2(), System.currentTimeMillis());
        HLogger.bgYellow(new Dummy2(), System.nanoTime());
        HLogger.bgYellow(new Dummy2(), Long.MAX_VALUE);
        HLogger.bgYellow(new Dummy2(), Long.MIN_VALUE);
        HLogger.bgYellow(new Dummy2(), 0);
        HLogger.bgYellow((Object) null, System.currentTimeMillis());
        HLogger.bgYellow((Object) null, System.nanoTime());
        HLogger.bgYellow((Object) null, Long.MAX_VALUE);
        HLogger.bgYellow((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
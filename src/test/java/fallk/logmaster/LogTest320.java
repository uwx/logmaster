
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest320 {

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
    public final void test_gray_Object_long() {
        HLogger.gray(new Object(), System.currentTimeMillis());
        HLogger.gray(new Object(), System.nanoTime());
        HLogger.gray(new Object(), Long.MAX_VALUE);
        HLogger.gray(new Object(), Long.MIN_VALUE);
        HLogger.gray(new Object(), 0);
        HLogger.gray(new Dummy(), System.currentTimeMillis());
        HLogger.gray(new Dummy(), System.nanoTime());
        HLogger.gray(new Dummy(), Long.MAX_VALUE);
        HLogger.gray(new Dummy(), Long.MIN_VALUE);
        HLogger.gray(new Dummy(), 0);
        HLogger.gray(new Dummy2(), System.currentTimeMillis());
        HLogger.gray(new Dummy2(), System.nanoTime());
        HLogger.gray(new Dummy2(), Long.MAX_VALUE);
        HLogger.gray(new Dummy2(), Long.MIN_VALUE);
        HLogger.gray(new Dummy2(), 0);
        HLogger.gray((Object) null, System.currentTimeMillis());
        HLogger.gray((Object) null, System.nanoTime());
        HLogger.gray((Object) null, Long.MAX_VALUE);
        HLogger.gray((Object) null, Long.MIN_VALUE);
    }

    
    //! $CHALK_END
}
    
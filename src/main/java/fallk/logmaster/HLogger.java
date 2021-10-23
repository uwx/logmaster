package fallk.logmaster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * It's a logger
 *
 * @author Eli / Maxine / Some fuckin discord bot author
 */
@SuppressWarnings({"WeakerAccess", "ResultOfMethodCallIgnored", "FieldCanBeLocal", "unused", "SameParameterValue"})
public final class HLogger {

    private static final float minutesInHour = 60f;
    private static final float secondsInMinute = 60f;
    private static final float milisecondsInSecond = 1000f;

    private HLogger() {
    }

    /**
     * time which the program started running
     */
    static final long initialTime = System.currentTimeMillis();

    /**
     * Set to false to suppress all output, including stderr
     */
    static boolean stdOut = true;
    /**
     * Set to false to suppress stderr output
     */
    static boolean stdErr = true;

    /**
     * Set to false to disable logging to file in the /logs/ folder
     */
    static boolean outputToFile = true;

    /**
     * Set to false to suppress {@link Level#DEBUG} messages
     */
    static boolean outputFine = true;

    /**
     * file to log to
     */
    private static File logFile;

    /**
     * fileWriter to output log data to if trailfile is true
     */
    private static PrintWriter fileWriter;

    static {
        applySettings(null);
    }

    public static void applySettings(LogmasterSettings set) {
        if (set != null) {
            stdOut = set.stdOut;
            stdErr = set.stdErr;
            outputToFile = set.outputToFile;
            outputFine = set.outputFine;
        }
        
        if (outputToFile) {
            final String mainClassName = getMainClassName();
            logFile = new File("./logs/" + mainClassName + '-' + LocalDate.now().toString() + ".log");

            // make folder if not exists
            final File logDir = new File("./logs");
            if (!logDir.exists()) {
                logDir.mkdir();
            } else if (!logDir.isDirectory()) {
                logDir.delete();
                logDir.mkdir();
            }

            // create log folder if previously failed
            File pFile = logFile.getParentFile();
            if (!pFile.exists()) {
                pFile.mkdirs();
            }
            // create log file if not exists
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (final IOException ignored) {
                }
            }

            // make writer
            try {
                fileWriter = new PrintWriter(new FileWriter(logFile,
                        // required since we've made the file already
                        true),
                        // saves a constant call to .flush()
                        true);
            } catch (final IOException ignored) {
                fileWriter = new NullWriter();
            }
            fileWriter.println(mainClassName + " --- " + LocalDateTime.now());

            // make sure we flush even if it didn't do it automatically
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                fileWriter.flush();
                fileWriter.close();
            }));
        }
    }

    private static String formatFloat(final float f) {
        final String s = Float.toString(f);
        final int dot = s.indexOf('.');
        return dot != -1 // decimal exists
                ? dot < s.length() - 2 // dot is under length
                  ? s.substring(0, dot + 2) // dot + 2 digits
                  : s.substring(0, dot) // just the dot
                : s; // not decimal, give whole number
    }

    private static String _getMainClassName() {
        try { // Oracle JVM only
            final String s = System.getProperty("sun.java.command");
            if (s != null) {
                return s;
            }
        } catch (final Exception ignored) {
        }

        try { // Oracle JVM as well
            for (final Map.Entry<String, String> entry : System.getenv().entrySet()) {
                if (entry.getKey().startsWith("JAVA_MAIN_CLASS")) {
                    return entry.getValue();
                }
            }
        } catch (final Exception ignored) {
        }

        // Doesn't work in threaded environments, but shouldn't matter
        final StackTraceElement[] stack = new Throwable().getStackTrace(); // new Throwable or new Exception? any difference?
        final StackTraceElement main = stack[stack.length - 1];
        return main.getClassName();
    }

    private static String getMainClassName() {
        final String s = _getMainClassName();
        int id = s.indexOf(' ');
        if (id > -1) {
            return s.substring(0, id);
        }
        return s;
    }

    /**
     * do not call this method directly
     * @return caller class name of whoever called the method that called this method
     */
    public static StackTraceElement getCallerCallerInfo() {
        StackTraceElement[] stElements = new Throwable().getStackTrace();
        return stElements[3];
    }

    public static final class Level {
        private Level() {}
        
        // DEBUG, INFO, WARNING, SEVERE, FATAL
        public static final int DEBUG = 0;
        public static final int INFO = 1;
        public static final int WARNING = 2;
        public static final int SEVERE = 3;
        public static final int FATAL = 4;

        public static final String[] name = {
                "debug", "info", "warning", "severe", "fatal"
        };

        public static final String[] colored = {
                Chalk.brightBlack(name[DEBUG]),
                Chalk.brightCyan(name[INFO]),
                Chalk.brightYellow(name[WARNING]),
                Chalk.brightRed(name[SEVERE]),
                Chalk.red(name[FATAL]),
        };
    }

    /**
     * Convert Throwable to String
     *
     * @author Maxine / Oracle devs
     */
    public static final class ThrowableUtils {
        private ThrowableUtils() {}
        
        /**
         * Caption for labeling causative exception stack traces
         */
        private static final String CAUSE_CAPTION = "Caused by: ";

        /**
         * Caption for labeling suppressed exception stack traces
         */
        private static final String SUPPRESSED_CAPTION = "Suppressed: ";

        /**
         * Gets the stack trace of an exception
         *
         * @param e the exception
         * @return the full stack trace
         */
        public static String getStackTrace(final Throwable e) {
            if (e == null) return "[ No stack trace available ]";
            final StringBuilder s = new StringBuilder(e.toString().length());
            // Print our stack trace
            s.append(e);
            final StackTraceElement[] trace = e.getStackTrace();
            for (final StackTraceElement traceElement : trace) {
                s.append("\n\tat ").append(traceElement);
            }

            // Print suppressed exceptions, if any
            for (final Throwable se : e.getSuppressed()) {
                putEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION, "\t", se);
            }

            // Print cause, if any
            final Throwable ourCause = e.getCause();
            if (ourCause != null) {
                putEnclosedStackTrace(s, trace, CAUSE_CAPTION, "", ourCause);
            }

            return s.toString();
        }

        /**
         * Includes the stack trace as an enclosed exception for the specified stack trace.
         *
         * @param s the StringBuilder to append to
         * @param enclosingTrace the exception's stack trace
         * @param caption the stack trace caption
         * @param prefix prefix for each line
         * @param t the exception itself
         */
        private static void putEnclosedStackTrace(final StringBuilder s, final StackTraceElement[] enclosingTrace, final String caption, final String prefix, final Throwable t) {

            // Compute number of frames in common between this and enclosing trace
            final StackTraceElement[] trace = t.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
                m--;
                n--;
            }
            final int framesInCommon = trace.length - 1 - m;

            // Print our stack trace
            s.append('\n').append(prefix).append(caption).append(t); // maybe no \n here
            for (int i = 0; i <= m; i++) {
                s.append(prefix).append("\n\tat ").append(trace[i]);
            }
            if (framesInCommon != 0) {
                s.append(prefix).append("\n\t... ").append(framesInCommon).append(" more");
            }

            // Print suppressed exceptions, if any
            for (final Throwable se : t.getSuppressed()) {
                putEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION, prefix + "\t", se);
            }

            // Print cause, if any
            final Throwable ourCause = t.getCause();
            if (ourCause != null) {
                putEnclosedStackTrace(s, trace, CAUSE_CAPTION, prefix, ourCause);
            }
        }
    }

    private static void write(final int level, final long timeStamp, final boolean logToFile, final String message, final String colorStart, final String colorEnd) {
        final StackTraceElement callerCallerInfo = getCallerCallerInfo();

        final StringBuilder sb = new StringBuilder();
        sb.append(LogmasterUtils.FormatTime(timeStamp));
        sb.append(' ');
        sb.append(Level.colored[level]);
        sb.append(' ');
        sb.append(callerCallerInfo.getClassName());
        sb.append('.');
        sb.append(callerCallerInfo.getMethodName());
        sb.append(':');
        sb.append(callerCallerInfo.getLineNumber());
        sb.append(' ');
        if (colorStart != null) sb.append(colorStart);
        sb.append(message);
        if (colorEnd != null) sb.append(colorEnd);
        sb.append('\n');

        final String fString = sb.toString();
        if (outputToFile && logToFile) {
            fileWriter.write(fString);
        }

        if (stdOut) {
            if (level >= Level.SEVERE) {
                if (stdErr) System.err.print(fString);
            } else if (level != Level.DEBUG) {
                System.out.print(fString);
            } else if (outputFine) {
                System.out.print(fString);
            }
        }
    }

    /*
     * The following methods are machine-generated. do not edit unless you have a freakin' deathwish.
     */

    /*
     * Chalk logger
     */
    
    //! $CHALK_START

    // region black
    // 1-arg methods
    public static void black(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.black_open, Chalk.black_close);
    }

    public static void black(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.black_open, Chalk.black_close);
    }

    public static void black(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.black_open, Chalk.black_close);
    }

    public static void black(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.black_open, Chalk.black_close);
    }
    
    // 2-arg methods
    public static void black(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.black_open, Chalk.black_close);
    }

    public static void black(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.black_open, Chalk.black_close);
    }

    public static void black(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.black_open, Chalk.black_close);
    }
    
    // vararg methods
    public static void black(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.black_open, Chalk.black_close);
    }

    public static void black(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.black_open, Chalk.black_close);
    }

    public static void black(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.black_open, Chalk.black_close);
    }
    // endregion black


    // region red
    // 1-arg methods
    public static void red(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.red_open, Chalk.red_close);
    }

    public static void red(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.red_open, Chalk.red_close);
    }

    public static void red(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.red_open, Chalk.red_close);
    }

    public static void red(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.red_open, Chalk.red_close);
    }
    
    // 2-arg methods
    public static void red(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.red_open, Chalk.red_close);
    }

    public static void red(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.red_open, Chalk.red_close);
    }

    public static void red(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.red_open, Chalk.red_close);
    }
    
    // vararg methods
    public static void red(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.red_open, Chalk.red_close);
    }

    public static void red(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.red_open, Chalk.red_close);
    }

    public static void red(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.red_open, Chalk.red_close);
    }
    // endregion red


    // region green
    // 1-arg methods
    public static void green(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.green_open, Chalk.green_close);
    }

    public static void green(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.green_open, Chalk.green_close);
    }

    public static void green(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.green_open, Chalk.green_close);
    }

    public static void green(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.green_open, Chalk.green_close);
    }
    
    // 2-arg methods
    public static void green(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.green_open, Chalk.green_close);
    }

    public static void green(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.green_open, Chalk.green_close);
    }

    public static void green(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.green_open, Chalk.green_close);
    }
    
    // vararg methods
    public static void green(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.green_open, Chalk.green_close);
    }

    public static void green(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.green_open, Chalk.green_close);
    }

    public static void green(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.green_open, Chalk.green_close);
    }
    // endregion green


    // region yellow
    // 1-arg methods
    public static void yellow(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.yellow_open, Chalk.yellow_close);
    }
    
    // 2-arg methods
    public static void yellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.yellow_open, Chalk.yellow_close);
    }
    
    // vararg methods
    public static void yellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.yellow_open, Chalk.yellow_close);
    }

    public static void yellow(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.yellow_open, Chalk.yellow_close);
    }
    // endregion yellow


    // region blue
    // 1-arg methods
    public static void blue(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.blue_open, Chalk.blue_close);
    }
    
    // 2-arg methods
    public static void blue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.blue_open, Chalk.blue_close);
    }
    
    // vararg methods
    public static void blue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.blue_open, Chalk.blue_close);
    }

    public static void blue(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.blue_open, Chalk.blue_close);
    }
    // endregion blue


    // region magenta
    // 1-arg methods
    public static void magenta(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.magenta_open, Chalk.magenta_close);
    }
    
    // 2-arg methods
    public static void magenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.magenta_open, Chalk.magenta_close);
    }
    
    // vararg methods
    public static void magenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.magenta_open, Chalk.magenta_close);
    }

    public static void magenta(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.magenta_open, Chalk.magenta_close);
    }
    // endregion magenta


    // region cyan
    // 1-arg methods
    public static void cyan(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.cyan_open, Chalk.cyan_close);
    }
    
    // 2-arg methods
    public static void cyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.cyan_open, Chalk.cyan_close);
    }
    
    // vararg methods
    public static void cyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.cyan_open, Chalk.cyan_close);
    }

    public static void cyan(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.cyan_open, Chalk.cyan_close);
    }
    // endregion cyan


    // region white
    // 1-arg methods
    public static void white(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.white_open, Chalk.white_close);
    }

    public static void white(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.white_open, Chalk.white_close);
    }

    public static void white(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.white_open, Chalk.white_close);
    }

    public static void white(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.white_open, Chalk.white_close);
    }
    
    // 2-arg methods
    public static void white(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.white_open, Chalk.white_close);
    }

    public static void white(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.white_open, Chalk.white_close);
    }

    public static void white(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.white_open, Chalk.white_close);
    }
    
    // vararg methods
    public static void white(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.white_open, Chalk.white_close);
    }

    public static void white(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.white_open, Chalk.white_close);
    }

    public static void white(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.white_open, Chalk.white_close);
    }
    // endregion white


    // region gray
    // 1-arg methods
    public static void gray(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.gray_open, Chalk.gray_close);
    }
    
    // 2-arg methods
    public static void gray(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.gray_open, Chalk.gray_close);
    }
    
    // vararg methods
    public static void gray(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.gray_open, Chalk.gray_close);
    }

    public static void gray(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.gray_open, Chalk.gray_close);
    }
    // endregion gray


    // region bgBlack
    // 1-arg methods
    public static void bgBlack(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }
    
    // 2-arg methods
    public static void bgBlack(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBlack_open, Chalk.bgBlack_close);
    }
    
    // vararg methods
    public static void bgBlack(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }

    public static void bgBlack(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBlack_open, Chalk.bgBlack_close);
    }
    // endregion bgBlack


    // region bgRed
    // 1-arg methods
    public static void bgRed(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgRed_open, Chalk.bgRed_close);
    }
    
    // 2-arg methods
    public static void bgRed(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgRed_open, Chalk.bgRed_close);
    }
    
    // vararg methods
    public static void bgRed(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgRed_open, Chalk.bgRed_close);
    }

    public static void bgRed(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgRed_open, Chalk.bgRed_close);
    }
    // endregion bgRed


    // region bgGreen
    // 1-arg methods
    public static void bgGreen(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }
    
    // 2-arg methods
    public static void bgGreen(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgGreen_open, Chalk.bgGreen_close);
    }
    
    // vararg methods
    public static void bgGreen(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }

    public static void bgGreen(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgGreen_open, Chalk.bgGreen_close);
    }
    // endregion bgGreen


    // region bgYellow
    // 1-arg methods
    public static void bgYellow(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }
    
    // 2-arg methods
    public static void bgYellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgYellow_open, Chalk.bgYellow_close);
    }
    
    // vararg methods
    public static void bgYellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }

    public static void bgYellow(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgYellow_open, Chalk.bgYellow_close);
    }
    // endregion bgYellow


    // region bgBlue
    // 1-arg methods
    public static void bgBlue(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }
    
    // 2-arg methods
    public static void bgBlue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBlue_open, Chalk.bgBlue_close);
    }
    
    // vararg methods
    public static void bgBlue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }

    public static void bgBlue(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBlue_open, Chalk.bgBlue_close);
    }
    // endregion bgBlue


    // region bgMagenta
    // 1-arg methods
    public static void bgMagenta(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }
    
    // 2-arg methods
    public static void bgMagenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }
    
    // vararg methods
    public static void bgMagenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }

    public static void bgMagenta(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgMagenta_open, Chalk.bgMagenta_close);
    }
    // endregion bgMagenta


    // region bgCyan
    // 1-arg methods
    public static void bgCyan(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }
    
    // 2-arg methods
    public static void bgCyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgCyan_open, Chalk.bgCyan_close);
    }
    
    // vararg methods
    public static void bgCyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }

    public static void bgCyan(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgCyan_open, Chalk.bgCyan_close);
    }
    // endregion bgCyan


    // region bgWhite
    // 1-arg methods
    public static void bgWhite(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }
    
    // 2-arg methods
    public static void bgWhite(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgWhite_open, Chalk.bgWhite_close);
    }
    
    // vararg methods
    public static void bgWhite(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }

    public static void bgWhite(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgWhite_open, Chalk.bgWhite_close);
    }
    // endregion bgWhite


    // region brightBlack
    // 1-arg methods
    public static void brightBlack(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }
    
    // 2-arg methods
    public static void brightBlack(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightBlack_open, Chalk.brightBlack_close);
    }
    
    // vararg methods
    public static void brightBlack(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }

    public static void brightBlack(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightBlack_open, Chalk.brightBlack_close);
    }
    // endregion brightBlack


    // region brightRed
    // 1-arg methods
    public static void brightRed(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightRed_open, Chalk.brightRed_close);
    }
    
    // 2-arg methods
    public static void brightRed(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightRed_open, Chalk.brightRed_close);
    }
    
    // vararg methods
    public static void brightRed(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightRed_open, Chalk.brightRed_close);
    }

    public static void brightRed(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightRed_open, Chalk.brightRed_close);
    }
    // endregion brightRed


    // region brightGreen
    // 1-arg methods
    public static void brightGreen(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }
    
    // 2-arg methods
    public static void brightGreen(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightGreen_open, Chalk.brightGreen_close);
    }
    
    // vararg methods
    public static void brightGreen(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }

    public static void brightGreen(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightGreen_open, Chalk.brightGreen_close);
    }
    // endregion brightGreen


    // region brightYellow
    // 1-arg methods
    public static void brightYellow(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }
    
    // 2-arg methods
    public static void brightYellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightYellow_open, Chalk.brightYellow_close);
    }
    
    // vararg methods
    public static void brightYellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }

    public static void brightYellow(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightYellow_open, Chalk.brightYellow_close);
    }
    // endregion brightYellow


    // region brightBlue
    // 1-arg methods
    public static void brightBlue(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }
    
    // 2-arg methods
    public static void brightBlue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightBlue_open, Chalk.brightBlue_close);
    }
    
    // vararg methods
    public static void brightBlue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }

    public static void brightBlue(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightBlue_open, Chalk.brightBlue_close);
    }
    // endregion brightBlue


    // region brightMagenta
    // 1-arg methods
    public static void brightMagenta(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }
    
    // 2-arg methods
    public static void brightMagenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }
    
    // vararg methods
    public static void brightMagenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }

    public static void brightMagenta(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightMagenta_open, Chalk.brightMagenta_close);
    }
    // endregion brightMagenta


    // region brightCyan
    // 1-arg methods
    public static void brightCyan(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }
    
    // 2-arg methods
    public static void brightCyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightCyan_open, Chalk.brightCyan_close);
    }
    
    // vararg methods
    public static void brightCyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }

    public static void brightCyan(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightCyan_open, Chalk.brightCyan_close);
    }
    // endregion brightCyan


    // region brightWhite
    // 1-arg methods
    public static void brightWhite(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }
    
    // 2-arg methods
    public static void brightWhite(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.brightWhite_open, Chalk.brightWhite_close);
    }
    
    // vararg methods
    public static void brightWhite(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }

    public static void brightWhite(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.brightWhite_open, Chalk.brightWhite_close);
    }
    // endregion brightWhite


    // region bgBrightBlack
    // 1-arg methods
    public static void bgBrightBlack(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }
    
    // 2-arg methods
    public static void bgBrightBlack(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }
    
    // vararg methods
    public static void bgBrightBlack(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }

    public static void bgBrightBlack(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightBlack_open, Chalk.bgBrightBlack_close);
    }
    // endregion bgBrightBlack


    // region bgBrightRed
    // 1-arg methods
    public static void bgBrightRed(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }
    
    // 2-arg methods
    public static void bgBrightRed(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }
    
    // vararg methods
    public static void bgBrightRed(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }

    public static void bgBrightRed(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightRed_open, Chalk.bgBrightRed_close);
    }
    // endregion bgBrightRed


    // region bgBrightGreen
    // 1-arg methods
    public static void bgBrightGreen(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }
    
    // 2-arg methods
    public static void bgBrightGreen(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }
    
    // vararg methods
    public static void bgBrightGreen(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }

    public static void bgBrightGreen(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightGreen_open, Chalk.bgBrightGreen_close);
    }
    // endregion bgBrightGreen


    // region bgBrightYellow
    // 1-arg methods
    public static void bgBrightYellow(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }
    
    // 2-arg methods
    public static void bgBrightYellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }
    
    // vararg methods
    public static void bgBrightYellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }

    public static void bgBrightYellow(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightYellow_open, Chalk.bgBrightYellow_close);
    }
    // endregion bgBrightYellow


    // region bgBrightBlue
    // 1-arg methods
    public static void bgBrightBlue(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }
    
    // 2-arg methods
    public static void bgBrightBlue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }
    
    // vararg methods
    public static void bgBrightBlue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }

    public static void bgBrightBlue(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightBlue_open, Chalk.bgBrightBlue_close);
    }
    // endregion bgBrightBlue


    // region bgBrightMagenta
    // 1-arg methods
    public static void bgBrightMagenta(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }
    
    // 2-arg methods
    public static void bgBrightMagenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }
    
    // vararg methods
    public static void bgBrightMagenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }

    public static void bgBrightMagenta(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightMagenta_open, Chalk.bgBrightMagenta_close);
    }
    // endregion bgBrightMagenta


    // region bgBrightCyan
    // 1-arg methods
    public static void bgBrightCyan(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }
    
    // 2-arg methods
    public static void bgBrightCyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }
    
    // vararg methods
    public static void bgBrightCyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }

    public static void bgBrightCyan(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightCyan_open, Chalk.bgBrightCyan_close);
    }
    // endregion bgBrightCyan


    // region bgBrightWhite
    // 1-arg methods
    public static void bgBrightWhite(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }
    
    // 2-arg methods
    public static void bgBrightWhite(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }
    
    // vararg methods
    public static void bgBrightWhite(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }

    public static void bgBrightWhite(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), Chalk.bgBrightWhite_open, Chalk.bgBrightWhite_close);
    }
    // endregion bgBrightWhite


    // region log
    // 1-arg methods
    public static void log(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void log(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void log(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void log(final Exception exception) {
        write(Level.INFO, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void log(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void log(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void log(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void log(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void log(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void log(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion log


    // region error
    // 1-arg methods
    public static void error(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void error(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void error(final Object message) {
        write(Level.SEVERE, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void error(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void error(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void error(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void error(final Object msg1, Object msg2) {
        write(Level.SEVERE, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void error(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void error(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void error(final Object... msgs) {
        write(Level.SEVERE, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion error


    // region warn
    // 1-arg methods
    public static void warn(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void warn(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void warn(final Object message) {
        write(Level.WARNING, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void warn(final Exception exception) {
        write(Level.WARNING, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void warn(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void warn(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void warn(final Object msg1, Object msg2) {
        write(Level.WARNING, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void warn(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void warn(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void warn(final Object... msgs) {
        write(Level.WARNING, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion warn


    // region debug
    // 1-arg methods
    public static void debug(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void debug(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void debug(final Object message) {
        write(Level.DEBUG, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void debug(final Exception exception) {
        write(Level.DEBUG, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void debug(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void debug(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void debug(final Object msg1, Object msg2) {
        write(Level.DEBUG, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void debug(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void debug(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void debug(final Object... msgs) {
        write(Level.DEBUG, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion debug


    // region info
    // 1-arg methods
    public static void info(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void info(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void info(final Object message) {
        write(Level.INFO, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void info(final Exception exception) {
        write(Level.INFO, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void info(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void info(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void info(final Object msg1, Object msg2) {
        write(Level.INFO, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void info(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void info(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void info(final Object... msgs) {
        write(Level.INFO, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion info


    // region warning
    // 1-arg methods
    public static void warning(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void warning(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void warning(final Object message) {
        write(Level.WARNING, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void warning(final Exception exception) {
        write(Level.WARNING, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void warning(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void warning(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void warning(final Object msg1, Object msg2) {
        write(Level.WARNING, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void warning(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void warning(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void warning(final Object... msgs) {
        write(Level.WARNING, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion warning


    // region severe
    // 1-arg methods
    public static void severe(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void severe(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void severe(final Object message) {
        write(Level.SEVERE, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void severe(final Exception exception) {
        write(Level.SEVERE, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void severe(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void severe(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void severe(final Object msg1, Object msg2) {
        write(Level.SEVERE, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void severe(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void severe(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void severe(final Object... msgs) {
        write(Level.SEVERE, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion severe


    // region fatal
    // 1-arg methods
    public static void fatal(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void fatal(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }

    public static void fatal(final Object message) {
        write(Level.FATAL, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), null, null);
    }

    public static void fatal(final Exception exception) {
        write(Level.FATAL, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), null, null);
    }
    
    // 2-arg methods
    public static void fatal(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, null, null);
    }

    public static void fatal(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }

    public static void fatal(final Object msg1, Object msg2) {
        write(Level.FATAL, System.currentTimeMillis(), true, msg1 + " " + msg2, null, null);
    }
    
    // vararg methods
    public static void fatal(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), null, null);
    }

    public static void fatal(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }

    public static void fatal(final Object... msgs) {
        write(Level.FATAL, System.currentTimeMillis(), true, Arrays.toString(msgs), null, null);
    }
    // endregion fatal


    /**
     * Utility class for dealing with cooooooolors
     *
     * @author Maxine / Chalk contributors (base is used in this)
     */
    @SuppressWarnings("all")
    public static final class Chalk {
        private Chalk() {
        }
        
        public static transient final String reset_open = "\u001b[0m";
        public static transient final String reset_close = "\u001b[0m";

        public static transient final String bold_open = "\u001b[1m";
        public static transient final String bold_close = "\u001b[22m";

        public static transient final String dim_open = "\u001b[2m";
        public static transient final String dim_close = "\u001b[22m";

        public static transient final String italic_open = "\u001b[3m";
        public static transient final String italic_close = "\u001b[23m";

        public static transient final String underline_open = "\u001b[4m";
        public static transient final String underline_close = "\u001b[24m";

        public static transient final String inverse_open = "\u001b[7m";
        public static transient final String inverse_close = "\u001b[27m";

        public static transient final String hidden_open = "\u001b[8m";
        public static transient final String hidden_close = "\u001b[28m";

        public static transient final String strikethrough_open = "\u001b[9m";
        public static transient final String strikethrough_close = "\u001b[29m";

        public static transient final String black_open = "\u001b[30m";
        public static transient final String black_close = "\u001b[39m";

        public static transient final String red_open = "\u001b[31m";
        public static transient final String red_close = "\u001b[39m";

        public static transient final String green_open = "\u001b[32m";
        public static transient final String green_close = "\u001b[39m";

        public static transient final String yellow_open = "\u001b[33m";
        public static transient final String yellow_close = "\u001b[39m";

        public static transient final String blue_open = "\u001b[34m";
        public static transient final String blue_close = "\u001b[39m";

        public static transient final String magenta_open = "\u001b[35m";
        public static transient final String magenta_close = "\u001b[39m";

        public static transient final String cyan_open = "\u001b[36m";
        public static transient final String cyan_close = "\u001b[39m";

        public static transient final String white_open = "\u001b[37m";
        public static transient final String white_close = "\u001b[39m";

        public static transient final String gray_open = "\u001b[90m";
        public static transient final String gray_close = "\u001b[39m";

        public static transient final String grey_open = "\u001b[90m";
        public static transient final String grey_close = "\u001b[39m";

        public static transient final String bgBlack_open = "\u001b[40m";
        public static transient final String bgBlack_close = "\u001b[49m";

        public static transient final String bgRed_open = "\u001b[41m";
        public static transient final String bgRed_close = "\u001b[49m";

        public static transient final String bgGreen_open = "\u001b[42m";
        public static transient final String bgGreen_close = "\u001b[49m";

        public static transient final String bgYellow_open = "\u001b[43m";
        public static transient final String bgYellow_close = "\u001b[49m";

        public static transient final String bgBlue_open = "\u001b[44m";
        public static transient final String bgBlue_close = "\u001b[49m";

        public static transient final String bgMagenta_open = "\u001b[45m";
        public static transient final String bgMagenta_close = "\u001b[49m";

        public static transient final String bgCyan_open = "\u001b[46m";
        public static transient final String bgCyan_close = "\u001b[49m";

        public static transient final String bgWhite_open = "\u001b[47m";
        public static transient final String bgWhite_close = "\u001b[49m";

        public static transient final String brightBlack_open = "\u001b[90m";
        public static transient final String brightBlack_close = "\u001b[39m";

        public static transient final String brightRed_open = "\u001b[91m";
        public static transient final String brightRed_close = "\u001b[39m";

        public static transient final String brightGreen_open = "\u001b[92m";
        public static transient final String brightGreen_close = "\u001b[39m";

        public static transient final String brightYellow_open = "\u001b[93m";
        public static transient final String brightYellow_close = "\u001b[39m";

        public static transient final String brightBlue_open = "\u001b[94m";
        public static transient final String brightBlue_close = "\u001b[39m";

        public static transient final String brightMagenta_open = "\u001b[95m";
        public static transient final String brightMagenta_close = "\u001b[39m";

        public static transient final String brightCyan_open = "\u001b[96m";
        public static transient final String brightCyan_close = "\u001b[39m";

        public static transient final String brightWhite_open = "\u001b[97m";
        public static transient final String brightWhite_close = "\u001b[39m";

        public static transient final String bgBrightBlack_open = "\u001b[100m";
        public static transient final String bgBrightBlack_close = "\u001b[49m";

        public static transient final String bgBrightRed_open = "\u001b[101m";
        public static transient final String bgBrightRed_close = "\u001b[49m";

        public static transient final String bgBrightGreen_open = "\u001b[102m";
        public static transient final String bgBrightGreen_close = "\u001b[49m";

        public static transient final String bgBrightYellow_open = "\u001b[103m";
        public static transient final String bgBrightYellow_close = "\u001b[49m";

        public static transient final String bgBrightBlue_open = "\u001b[104m";
        public static transient final String bgBrightBlue_close = "\u001b[49m";

        public static transient final String bgBrightMagenta_open = "\u001b[105m";
        public static transient final String bgBrightMagenta_close = "\u001b[49m";

        public static transient final String bgBrightCyan_open = "\u001b[106m";
        public static transient final String bgBrightCyan_close = "\u001b[49m";

        public static transient final String bgBrightWhite_open = "\u001b[107m";
        public static transient final String bgBrightWhite_close = "\u001b[49m";

        public static void p_reset(final String s) {
            HLogger.log(reset_open + s + reset_close);
        }

        public static void p_bold(final String s) {
            HLogger.log(bold_open + s + bold_close);
        }

        public static void p_dim(final String s) {
            HLogger.log(dim_open + s + dim_close);
        }

        public static void p_italic(final String s) {
            HLogger.log(italic_open + s + italic_close);
        }

        public static void p_underline(final String s) {
            HLogger.log(underline_open + s + underline_close);
        }

        public static void p_inverse(final String s) {
            HLogger.log(inverse_open + s + inverse_close);
        }

        public static void p_hidden(final String s) {
            HLogger.log(hidden_open + s + hidden_close);
        }

        public static void p_strikethrough(final String s) {
            HLogger.log(strikethrough_open + s + strikethrough_close);
        }

        public static void p_black(final String s) {
            HLogger.log(black_open + s + black_close);
        }

        public static void p_red(final String s) {
            HLogger.log(red_open + s + red_close);
        }

        public static void p_green(final String s) {
            HLogger.log(green_open + s + green_close);
        }

        public static void p_yellow(final String s) {
            HLogger.log(yellow_open + s + yellow_close);
        }

        public static void p_blue(final String s) {
            HLogger.log(blue_open + s + blue_close);
        }

        public static void p_magenta(final String s) {
            HLogger.log(magenta_open + s + magenta_close);
        }

        public static void p_cyan(final String s) {
            HLogger.log(cyan_open + s + cyan_close);
        }

        public static void p_white(final String s) {
            HLogger.log(white_open + s + white_close);
        }

        public static void p_gray(final String s) {
            HLogger.log(gray_open + s + gray_close);
        }

        public static void p_grey(final String s) {
            HLogger.log(grey_open + s + grey_close);
        }

        public static void p_bgBlack(final String s) {
            HLogger.log(bgBlack_open + s + bgBlack_close);
        }

        public static void p_bgRed(final String s) {
            HLogger.log(bgRed_open + s + bgRed_close);
        }

        public static void p_bgGreen(final String s) {
            HLogger.log(bgGreen_open + s + bgGreen_close);
        }

        public static void p_bgYellow(final String s) {
            HLogger.log(bgYellow_open + s + bgYellow_close);
        }

        public static void p_bgBlue(final String s) {
            HLogger.log(bgBlue_open + s + bgBlue_close);
        }

        public static void p_bgMagenta(final String s) {
            HLogger.log(bgMagenta_open + s + bgMagenta_close);
        }

        public static void p_bgCyan(final String s) {
            HLogger.log(bgCyan_open + s + bgCyan_close);
        }

        public static void p_bgWhite(final String s) {
            HLogger.log(bgWhite_open + s + bgWhite_close);
        }

        public static void p_brightBlack(final String s) {
            HLogger.log(brightBlack_open + s + brightBlack_close);
        }

        public static void p_brightRed(final String s) {
            HLogger.log(brightRed_open + s + brightRed_close);
        }

        public static void p_brightGreen(final String s) {
            HLogger.log(brightGreen_open + s + brightGreen_close);
        }

        public static void p_brightYellow(final String s) {
            HLogger.log(brightYellow_open + s + brightYellow_close);
        }

        public static void p_brightBlue(final String s) {
            HLogger.log(brightBlue_open + s + brightBlue_close);
        }

        public static void p_brightMagenta(final String s) {
            HLogger.log(brightMagenta_open + s + brightMagenta_close);
        }

        public static void p_brightCyan(final String s) {
            HLogger.log(brightCyan_open + s + brightCyan_close);
        }

        public static void p_brightWhite(final String s) {
            HLogger.log(brightWhite_open + s + brightWhite_close);
        }

        public static void p_bgBrightBlack(final String s) {
            HLogger.log(bgBrightBlack_open + s + bgBrightBlack_close);
        }

        public static void p_bgBrightRed(final String s) {
            HLogger.log(bgBrightRed_open + s + bgBrightRed_close);
        }

        public static void p_bgBrightGreen(final String s) {
            HLogger.log(bgBrightGreen_open + s + bgBrightGreen_close);
        }

        public static void p_bgBrightYellow(final String s) {
            HLogger.log(bgBrightYellow_open + s + bgBrightYellow_close);
        }

        public static void p_bgBrightBlue(final String s) {
            HLogger.log(bgBrightBlue_open + s + bgBrightBlue_close);
        }

        public static void p_bgBrightMagenta(final String s) {
            HLogger.log(bgBrightMagenta_open + s + bgBrightMagenta_close);
        }

        public static void p_bgBrightCyan(final String s) {
            HLogger.log(bgBrightCyan_open + s + bgBrightCyan_close);
        }

        public static void p_bgBrightWhite(final String s) {
            HLogger.log(bgBrightWhite_open + s + bgBrightWhite_close);
        }

        public static String reset(final String s) {
            return reset_open + s + reset_close;
        }
        public static String bold(final String s) {
            return bold_open + s + bold_close;
        }
        public static String dim(final String s) {
            return dim_open + s + dim_close;
        }
        public static String italic(final String s) {
            return italic_open + s + italic_close;
        }
        public static String underline(final String s) {
            return underline_open + s + underline_close;
        }
        public static String inverse(final String s) {
            return inverse_open + s + inverse_close;
        }
        public static String hidden(final String s) {
            return hidden_open + s + hidden_close;
        }
        public static String strikethrough(final String s) {
            return strikethrough_open + s + strikethrough_close;
        }
        public static String black(final String s) {
            return black_open + s + black_close;
        }
        public static String red(final String s) {
            return red_open + s + red_close;
        }
        public static String green(final String s) {
            return green_open + s + green_close;
        }
        public static String yellow(final String s) {
            return yellow_open + s + yellow_close;
        }
        public static String blue(final String s) {
            return blue_open + s + blue_close;
        }
        public static String magenta(final String s) {
            return magenta_open + s + magenta_close;
        }
        public static String cyan(final String s) {
            return cyan_open + s + cyan_close;
        }
        public static String white(final String s) {
            return white_open + s + white_close;
        }
        public static String gray(final String s) {
            return gray_open + s + gray_close;
        }
        public static String grey(final String s) {
            return grey_open + s + grey_close;
        }
        public static String bgBlack(final String s) {
            return bgBlack_open + s + bgBlack_close;
        }
        public static String bgRed(final String s) {
            return bgRed_open + s + bgRed_close;
        }
        public static String bgGreen(final String s) {
            return bgGreen_open + s + bgGreen_close;
        }
        public static String bgYellow(final String s) {
            return bgYellow_open + s + bgYellow_close;
        }
        public static String bgBlue(final String s) {
            return bgBlue_open + s + bgBlue_close;
        }
        public static String bgMagenta(final String s) {
            return bgMagenta_open + s + bgMagenta_close;
        }
        public static String bgCyan(final String s) {
            return bgCyan_open + s + bgCyan_close;
        }
        public static String bgWhite(final String s) {
            return bgWhite_open + s + bgWhite_close;
        }
        public static String brightBlack(final String s) {
            return brightBlack_open + s + brightBlack_close;
        }
        public static String brightRed(final String s) {
            return brightRed_open + s + brightRed_close;
        }
        public static String brightGreen(final String s) {
            return brightGreen_open + s + brightGreen_close;
        }
        public static String brightYellow(final String s) {
            return brightYellow_open + s + brightYellow_close;
        }
        public static String brightBlue(final String s) {
            return brightBlue_open + s + brightBlue_close;
        }
        public static String brightMagenta(final String s) {
            return brightMagenta_open + s + brightMagenta_close;
        }
        public static String brightCyan(final String s) {
            return brightCyan_open + s + brightCyan_close;
        }
        public static String brightWhite(final String s) {
            return brightWhite_open + s + brightWhite_close;
        }
        public static String bgBrightBlack(final String s) {
            return bgBrightBlack_open + s + bgBrightBlack_close;
        }
        public static String bgBrightRed(final String s) {
            return bgBrightRed_open + s + bgBrightRed_close;
        }
        public static String bgBrightGreen(final String s) {
            return bgBrightGreen_open + s + bgBrightGreen_close;
        }
        public static String bgBrightYellow(final String s) {
            return bgBrightYellow_open + s + bgBrightYellow_close;
        }
        public static String bgBrightBlue(final String s) {
            return bgBrightBlue_open + s + bgBrightBlue_close;
        }
        public static String bgBrightMagenta(final String s) {
            return bgBrightMagenta_open + s + bgBrightMagenta_close;
        }
        public static String bgBrightCyan(final String s) {
            return bgBrightCyan_open + s + bgBrightCyan_close;
        }
        public static String bgBrightWhite(final String s) {
            return bgBrightWhite_open + s + bgBrightWhite_close;
        }

    }
//! $CHALK_END

}

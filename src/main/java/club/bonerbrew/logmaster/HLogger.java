package club.bonerbrew.logmaster;

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
 * @author Eli / Rafael / Some fuckin discord bot author
 */
@SuppressWarnings({"WeakerAccess", "ResultOfMethodCallIgnored", "FieldCanBeLocal", "unused", "SameParameterValue"})
public final class HLogger {

    private static final float minutesInHour = 60f;
    private static final float secondsInMinute = 60f;
    private static final float milisecondsInSecond = 1000f;

    private HLogger() {
    }

    ///** formatter used for String.format */
    //private static final Formatter FORMATTER = new Formatter();

    /**
     * time which the program started running
     */
    private static final long initialTime = System.currentTimeMillis();

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
    public static String getCallerCallerClassName() { 
        StackTraceElement[] stElements = new Throwable().getStackTrace();
        return stElements[4].getClassName();
     }

    private static String formatTime(final long timeStamp) {
        final long timeElapsed = timeStamp - initialTime;
        final int hours = (int) (timeElapsed / milisecondsInSecond / secondsInMinute / minutesInHour);
        final int mins = (int) (timeElapsed / milisecondsInSecond / secondsInMinute);

        final StringBuilder s = new StringBuilder(9/*+16*/); // +23:56:89
        s.append('+');
        // fast code! i guess
        if (hours != 0) {
            s.append(hours < 10 ? '0' + hours : hours) //
                    .append(':') //
                    .append(formatFloat(timeElapsed / milisecondsInSecond / secondsInMinute)).append("min");
        } else if (mins != 0) {
            s.append(formatFloat(timeElapsed / milisecondsInSecond / secondsInMinute)).append("min");
        } else {
            s.append(formatFloat(timeElapsed / milisecondsInSecond)).append('s');
        }

        return s.toString();
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
                "Debug", "Info", "Warning", "Severe", "Fatal"
        };
    }

    /**
     * Convert Throwable to String
     *
     * @author Rafael / Oracle devs
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

    /*
     * The following methods are machine-generated. do not edit unless you have a freakin' deathwish.
     */

    private static void write_(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
     * Chalk logger
     */
    
    //! $CHALK_START

    // 1-arg methods
    public static void reset(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void reset(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, message);
    }

    public static void reset(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void reset(final int level, final Object message, final long timeStamp) {
        reset(level, message, timeStamp, true);
    }

    public static void reset(final int level, final String message, final long timeStamp) {
        reset(level, message, timeStamp, true);
    }

    public static void reset(final int level, final Exception message, final long timeStamp) {
        reset(level, message, timeStamp, true);
    }

    public static void reset(final int level, final Object message) {
        reset(level, message, System.currentTimeMillis(), true);
    }

    public static void reset(final int level, final String message) {
        reset(level, message, System.currentTimeMillis(), true);
    }

    public static void reset(final int level, final Exception message) {
        reset(level, message, System.currentTimeMillis(), true);
    }

    public static void reset(final String message, final long timeStamp) {
        reset(Level.INFO, message, timeStamp, true);
    }

    public static void reset(final String message) {
        reset(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void reset(final Object message, final long timeStamp) {
        reset(Level.INFO, message, timeStamp, true);
    }

    public static void reset(final Object message) {
        reset(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void reset(final Exception exception, final long timeStamp) {
        reset(Level.INFO, exception, timeStamp, true);
    }

    public static void reset(final Exception exception) {
        reset(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void reset(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void reset(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void reset(final int level, final Object msg1, Object msg2, final long timeStamp) {
        reset(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void reset(final int level, final String msg1, String msg2, final long timeStamp) {
        reset(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void reset(final int level, final Object msg1, Object msg2) {
        reset(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void reset(final int level, final String msg1, String msg2) {
        reset(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void reset(final String msg1, String msg2, final long timeStamp) {
        reset(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void reset(final String msg1, String msg2) {
        reset(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void reset(final Object msg1, Object msg2, final long timeStamp) {
        reset(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void reset(final Object msg1, Object msg2) {
        reset(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void reset(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void reset(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_reset(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void reset(final int level, final Object[] msgs, final long timeStamp) {
        reset(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void reset(final int level, final String[] msgs, final long timeStamp) {
        reset(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void reset(final int level, final Object... msgs) {
        reset(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void reset(final int level, final String... msgs) {
        reset(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void reset(final String[] msgs, final long timeStamp) {
        reset(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void reset(final String... msgs) {
        reset(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void reset(final Object[] msgs, final long timeStamp) {
        reset(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void reset(final Object... msgs) {
        reset(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_reset(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.reset_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.reset_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bold(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bold(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, message);
    }

    public static void bold(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bold(final int level, final Object message, final long timeStamp) {
        bold(level, message, timeStamp, true);
    }

    public static void bold(final int level, final String message, final long timeStamp) {
        bold(level, message, timeStamp, true);
    }

    public static void bold(final int level, final Exception message, final long timeStamp) {
        bold(level, message, timeStamp, true);
    }

    public static void bold(final int level, final Object message) {
        bold(level, message, System.currentTimeMillis(), true);
    }

    public static void bold(final int level, final String message) {
        bold(level, message, System.currentTimeMillis(), true);
    }

    public static void bold(final int level, final Exception message) {
        bold(level, message, System.currentTimeMillis(), true);
    }

    public static void bold(final String message, final long timeStamp) {
        bold(Level.INFO, message, timeStamp, true);
    }

    public static void bold(final String message) {
        bold(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bold(final Object message, final long timeStamp) {
        bold(Level.INFO, message, timeStamp, true);
    }

    public static void bold(final Object message) {
        bold(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bold(final Exception exception, final long timeStamp) {
        bold(Level.INFO, exception, timeStamp, true);
    }

    public static void bold(final Exception exception) {
        bold(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bold(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bold(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bold(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bold(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bold(final int level, final String msg1, String msg2, final long timeStamp) {
        bold(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bold(final int level, final Object msg1, Object msg2) {
        bold(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bold(final int level, final String msg1, String msg2) {
        bold(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bold(final String msg1, String msg2, final long timeStamp) {
        bold(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bold(final String msg1, String msg2) {
        bold(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bold(final Object msg1, Object msg2, final long timeStamp) {
        bold(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bold(final Object msg1, Object msg2) {
        bold(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bold(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bold(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bold(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bold(final int level, final Object[] msgs, final long timeStamp) {
        bold(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bold(final int level, final String[] msgs, final long timeStamp) {
        bold(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bold(final int level, final Object... msgs) {
        bold(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bold(final int level, final String... msgs) {
        bold(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bold(final String[] msgs, final long timeStamp) {
        bold(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bold(final String... msgs) {
        bold(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bold(final Object[] msgs, final long timeStamp) {
        bold(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bold(final Object... msgs) {
        bold(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bold(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bold_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bold_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void dim(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void dim(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, message);
    }

    public static void dim(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void dim(final int level, final Object message, final long timeStamp) {
        dim(level, message, timeStamp, true);
    }

    public static void dim(final int level, final String message, final long timeStamp) {
        dim(level, message, timeStamp, true);
    }

    public static void dim(final int level, final Exception message, final long timeStamp) {
        dim(level, message, timeStamp, true);
    }

    public static void dim(final int level, final Object message) {
        dim(level, message, System.currentTimeMillis(), true);
    }

    public static void dim(final int level, final String message) {
        dim(level, message, System.currentTimeMillis(), true);
    }

    public static void dim(final int level, final Exception message) {
        dim(level, message, System.currentTimeMillis(), true);
    }

    public static void dim(final String message, final long timeStamp) {
        dim(Level.INFO, message, timeStamp, true);
    }

    public static void dim(final String message) {
        dim(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void dim(final Object message, final long timeStamp) {
        dim(Level.INFO, message, timeStamp, true);
    }

    public static void dim(final Object message) {
        dim(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void dim(final Exception exception, final long timeStamp) {
        dim(Level.INFO, exception, timeStamp, true);
    }

    public static void dim(final Exception exception) {
        dim(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void dim(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void dim(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void dim(final int level, final Object msg1, Object msg2, final long timeStamp) {
        dim(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void dim(final int level, final String msg1, String msg2, final long timeStamp) {
        dim(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void dim(final int level, final Object msg1, Object msg2) {
        dim(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void dim(final int level, final String msg1, String msg2) {
        dim(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void dim(final String msg1, String msg2, final long timeStamp) {
        dim(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void dim(final String msg1, String msg2) {
        dim(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void dim(final Object msg1, Object msg2, final long timeStamp) {
        dim(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void dim(final Object msg1, Object msg2) {
        dim(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void dim(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void dim(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_dim(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void dim(final int level, final Object[] msgs, final long timeStamp) {
        dim(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void dim(final int level, final String[] msgs, final long timeStamp) {
        dim(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void dim(final int level, final Object... msgs) {
        dim(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void dim(final int level, final String... msgs) {
        dim(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void dim(final String[] msgs, final long timeStamp) {
        dim(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void dim(final String... msgs) {
        dim(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void dim(final Object[] msgs, final long timeStamp) {
        dim(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void dim(final Object... msgs) {
        dim(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_dim(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.dim_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.dim_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void italic(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void italic(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, message);
    }

    public static void italic(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void italic(final int level, final Object message, final long timeStamp) {
        italic(level, message, timeStamp, true);
    }

    public static void italic(final int level, final String message, final long timeStamp) {
        italic(level, message, timeStamp, true);
    }

    public static void italic(final int level, final Exception message, final long timeStamp) {
        italic(level, message, timeStamp, true);
    }

    public static void italic(final int level, final Object message) {
        italic(level, message, System.currentTimeMillis(), true);
    }

    public static void italic(final int level, final String message) {
        italic(level, message, System.currentTimeMillis(), true);
    }

    public static void italic(final int level, final Exception message) {
        italic(level, message, System.currentTimeMillis(), true);
    }

    public static void italic(final String message, final long timeStamp) {
        italic(Level.INFO, message, timeStamp, true);
    }

    public static void italic(final String message) {
        italic(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void italic(final Object message, final long timeStamp) {
        italic(Level.INFO, message, timeStamp, true);
    }

    public static void italic(final Object message) {
        italic(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void italic(final Exception exception, final long timeStamp) {
        italic(Level.INFO, exception, timeStamp, true);
    }

    public static void italic(final Exception exception) {
        italic(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void italic(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void italic(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void italic(final int level, final Object msg1, Object msg2, final long timeStamp) {
        italic(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void italic(final int level, final String msg1, String msg2, final long timeStamp) {
        italic(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void italic(final int level, final Object msg1, Object msg2) {
        italic(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void italic(final int level, final String msg1, String msg2) {
        italic(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void italic(final String msg1, String msg2, final long timeStamp) {
        italic(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void italic(final String msg1, String msg2) {
        italic(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void italic(final Object msg1, Object msg2, final long timeStamp) {
        italic(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void italic(final Object msg1, Object msg2) {
        italic(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void italic(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void italic(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_italic(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void italic(final int level, final Object[] msgs, final long timeStamp) {
        italic(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void italic(final int level, final String[] msgs, final long timeStamp) {
        italic(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void italic(final int level, final Object... msgs) {
        italic(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void italic(final int level, final String... msgs) {
        italic(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void italic(final String[] msgs, final long timeStamp) {
        italic(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void italic(final String... msgs) {
        italic(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void italic(final Object[] msgs, final long timeStamp) {
        italic(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void italic(final Object... msgs) {
        italic(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_italic(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.italic_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.italic_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void underline(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void underline(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, message);
    }

    public static void underline(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void underline(final int level, final Object message, final long timeStamp) {
        underline(level, message, timeStamp, true);
    }

    public static void underline(final int level, final String message, final long timeStamp) {
        underline(level, message, timeStamp, true);
    }

    public static void underline(final int level, final Exception message, final long timeStamp) {
        underline(level, message, timeStamp, true);
    }

    public static void underline(final int level, final Object message) {
        underline(level, message, System.currentTimeMillis(), true);
    }

    public static void underline(final int level, final String message) {
        underline(level, message, System.currentTimeMillis(), true);
    }

    public static void underline(final int level, final Exception message) {
        underline(level, message, System.currentTimeMillis(), true);
    }

    public static void underline(final String message, final long timeStamp) {
        underline(Level.INFO, message, timeStamp, true);
    }

    public static void underline(final String message) {
        underline(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void underline(final Object message, final long timeStamp) {
        underline(Level.INFO, message, timeStamp, true);
    }

    public static void underline(final Object message) {
        underline(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void underline(final Exception exception, final long timeStamp) {
        underline(Level.INFO, exception, timeStamp, true);
    }

    public static void underline(final Exception exception) {
        underline(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void underline(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void underline(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void underline(final int level, final Object msg1, Object msg2, final long timeStamp) {
        underline(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void underline(final int level, final String msg1, String msg2, final long timeStamp) {
        underline(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void underline(final int level, final Object msg1, Object msg2) {
        underline(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void underline(final int level, final String msg1, String msg2) {
        underline(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void underline(final String msg1, String msg2, final long timeStamp) {
        underline(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void underline(final String msg1, String msg2) {
        underline(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void underline(final Object msg1, Object msg2, final long timeStamp) {
        underline(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void underline(final Object msg1, Object msg2) {
        underline(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void underline(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void underline(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_underline(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void underline(final int level, final Object[] msgs, final long timeStamp) {
        underline(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void underline(final int level, final String[] msgs, final long timeStamp) {
        underline(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void underline(final int level, final Object... msgs) {
        underline(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void underline(final int level, final String... msgs) {
        underline(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void underline(final String[] msgs, final long timeStamp) {
        underline(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void underline(final String... msgs) {
        underline(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void underline(final Object[] msgs, final long timeStamp) {
        underline(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void underline(final Object... msgs) {
        underline(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_underline(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.underline_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.underline_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void inverse(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void inverse(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, message);
    }

    public static void inverse(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void inverse(final int level, final Object message, final long timeStamp) {
        inverse(level, message, timeStamp, true);
    }

    public static void inverse(final int level, final String message, final long timeStamp) {
        inverse(level, message, timeStamp, true);
    }

    public static void inverse(final int level, final Exception message, final long timeStamp) {
        inverse(level, message, timeStamp, true);
    }

    public static void inverse(final int level, final Object message) {
        inverse(level, message, System.currentTimeMillis(), true);
    }

    public static void inverse(final int level, final String message) {
        inverse(level, message, System.currentTimeMillis(), true);
    }

    public static void inverse(final int level, final Exception message) {
        inverse(level, message, System.currentTimeMillis(), true);
    }

    public static void inverse(final String message, final long timeStamp) {
        inverse(Level.INFO, message, timeStamp, true);
    }

    public static void inverse(final String message) {
        inverse(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void inverse(final Object message, final long timeStamp) {
        inverse(Level.INFO, message, timeStamp, true);
    }

    public static void inverse(final Object message) {
        inverse(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void inverse(final Exception exception, final long timeStamp) {
        inverse(Level.INFO, exception, timeStamp, true);
    }

    public static void inverse(final Exception exception) {
        inverse(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void inverse(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void inverse(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void inverse(final int level, final Object msg1, Object msg2, final long timeStamp) {
        inverse(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void inverse(final int level, final String msg1, String msg2, final long timeStamp) {
        inverse(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void inverse(final int level, final Object msg1, Object msg2) {
        inverse(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void inverse(final int level, final String msg1, String msg2) {
        inverse(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void inverse(final String msg1, String msg2, final long timeStamp) {
        inverse(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void inverse(final String msg1, String msg2) {
        inverse(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void inverse(final Object msg1, Object msg2, final long timeStamp) {
        inverse(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void inverse(final Object msg1, Object msg2) {
        inverse(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void inverse(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void inverse(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_inverse(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void inverse(final int level, final Object[] msgs, final long timeStamp) {
        inverse(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void inverse(final int level, final String[] msgs, final long timeStamp) {
        inverse(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void inverse(final int level, final Object... msgs) {
        inverse(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void inverse(final int level, final String... msgs) {
        inverse(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void inverse(final String[] msgs, final long timeStamp) {
        inverse(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void inverse(final String... msgs) {
        inverse(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void inverse(final Object[] msgs, final long timeStamp) {
        inverse(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void inverse(final Object... msgs) {
        inverse(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_inverse(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.inverse_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.inverse_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void hidden(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void hidden(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, message);
    }

    public static void hidden(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void hidden(final int level, final Object message, final long timeStamp) {
        hidden(level, message, timeStamp, true);
    }

    public static void hidden(final int level, final String message, final long timeStamp) {
        hidden(level, message, timeStamp, true);
    }

    public static void hidden(final int level, final Exception message, final long timeStamp) {
        hidden(level, message, timeStamp, true);
    }

    public static void hidden(final int level, final Object message) {
        hidden(level, message, System.currentTimeMillis(), true);
    }

    public static void hidden(final int level, final String message) {
        hidden(level, message, System.currentTimeMillis(), true);
    }

    public static void hidden(final int level, final Exception message) {
        hidden(level, message, System.currentTimeMillis(), true);
    }

    public static void hidden(final String message, final long timeStamp) {
        hidden(Level.INFO, message, timeStamp, true);
    }

    public static void hidden(final String message) {
        hidden(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void hidden(final Object message, final long timeStamp) {
        hidden(Level.INFO, message, timeStamp, true);
    }

    public static void hidden(final Object message) {
        hidden(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void hidden(final Exception exception, final long timeStamp) {
        hidden(Level.INFO, exception, timeStamp, true);
    }

    public static void hidden(final Exception exception) {
        hidden(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void hidden(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void hidden(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void hidden(final int level, final Object msg1, Object msg2, final long timeStamp) {
        hidden(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void hidden(final int level, final String msg1, String msg2, final long timeStamp) {
        hidden(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void hidden(final int level, final Object msg1, Object msg2) {
        hidden(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void hidden(final int level, final String msg1, String msg2) {
        hidden(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void hidden(final String msg1, String msg2, final long timeStamp) {
        hidden(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void hidden(final String msg1, String msg2) {
        hidden(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void hidden(final Object msg1, Object msg2, final long timeStamp) {
        hidden(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void hidden(final Object msg1, Object msg2) {
        hidden(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void hidden(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void hidden(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_hidden(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void hidden(final int level, final Object[] msgs, final long timeStamp) {
        hidden(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void hidden(final int level, final String[] msgs, final long timeStamp) {
        hidden(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void hidden(final int level, final Object... msgs) {
        hidden(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void hidden(final int level, final String... msgs) {
        hidden(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void hidden(final String[] msgs, final long timeStamp) {
        hidden(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void hidden(final String... msgs) {
        hidden(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void hidden(final Object[] msgs, final long timeStamp) {
        hidden(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void hidden(final Object... msgs) {
        hidden(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_hidden(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.hidden_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.hidden_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void strikethrough(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void strikethrough(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, message);
    }

    public static void strikethrough(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void strikethrough(final int level, final Object message, final long timeStamp) {
        strikethrough(level, message, timeStamp, true);
    }

    public static void strikethrough(final int level, final String message, final long timeStamp) {
        strikethrough(level, message, timeStamp, true);
    }

    public static void strikethrough(final int level, final Exception message, final long timeStamp) {
        strikethrough(level, message, timeStamp, true);
    }

    public static void strikethrough(final int level, final Object message) {
        strikethrough(level, message, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final int level, final String message) {
        strikethrough(level, message, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final int level, final Exception message) {
        strikethrough(level, message, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final String message, final long timeStamp) {
        strikethrough(Level.INFO, message, timeStamp, true);
    }

    public static void strikethrough(final String message) {
        strikethrough(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final Object message, final long timeStamp) {
        strikethrough(Level.INFO, message, timeStamp, true);
    }

    public static void strikethrough(final Object message) {
        strikethrough(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final Exception exception, final long timeStamp) {
        strikethrough(Level.INFO, exception, timeStamp, true);
    }

    public static void strikethrough(final Exception exception) {
        strikethrough(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void strikethrough(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void strikethrough(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void strikethrough(final int level, final Object msg1, Object msg2, final long timeStamp) {
        strikethrough(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strikethrough(final int level, final String msg1, String msg2, final long timeStamp) {
        strikethrough(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strikethrough(final int level, final Object msg1, Object msg2) {
        strikethrough(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final int level, final String msg1, String msg2) {
        strikethrough(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final String msg1, String msg2, final long timeStamp) {
        strikethrough(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strikethrough(final String msg1, String msg2) {
        strikethrough(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strikethrough(final Object msg1, Object msg2, final long timeStamp) {
        strikethrough(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strikethrough(final Object msg1, Object msg2) {
        strikethrough(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void strikethrough(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void strikethrough(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void strikethrough(final int level, final Object[] msgs, final long timeStamp) {
        strikethrough(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strikethrough(final int level, final String[] msgs, final long timeStamp) {
        strikethrough(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strikethrough(final int level, final Object... msgs) {
        strikethrough(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strikethrough(final int level, final String... msgs) {
        strikethrough(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strikethrough(final String[] msgs, final long timeStamp) {
        strikethrough(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strikethrough(final String... msgs) {
        strikethrough(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strikethrough(final Object[] msgs, final long timeStamp) {
        strikethrough(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strikethrough(final Object... msgs) {
        strikethrough(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_strikethrough(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.strikethrough_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.strikethrough_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void black(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void black(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, message);
    }

    public static void black(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void black(final int level, final Object message, final long timeStamp) {
        black(level, message, timeStamp, true);
    }

    public static void black(final int level, final String message, final long timeStamp) {
        black(level, message, timeStamp, true);
    }

    public static void black(final int level, final Exception message, final long timeStamp) {
        black(level, message, timeStamp, true);
    }

    public static void black(final int level, final Object message) {
        black(level, message, System.currentTimeMillis(), true);
    }

    public static void black(final int level, final String message) {
        black(level, message, System.currentTimeMillis(), true);
    }

    public static void black(final int level, final Exception message) {
        black(level, message, System.currentTimeMillis(), true);
    }

    public static void black(final String message, final long timeStamp) {
        black(Level.INFO, message, timeStamp, true);
    }

    public static void black(final String message) {
        black(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void black(final Object message, final long timeStamp) {
        black(Level.INFO, message, timeStamp, true);
    }

    public static void black(final Object message) {
        black(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void black(final Exception exception, final long timeStamp) {
        black(Level.INFO, exception, timeStamp, true);
    }

    public static void black(final Exception exception) {
        black(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void black(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void black(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void black(final int level, final Object msg1, Object msg2, final long timeStamp) {
        black(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void black(final int level, final String msg1, String msg2, final long timeStamp) {
        black(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void black(final int level, final Object msg1, Object msg2) {
        black(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void black(final int level, final String msg1, String msg2) {
        black(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void black(final String msg1, String msg2, final long timeStamp) {
        black(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void black(final String msg1, String msg2) {
        black(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void black(final Object msg1, Object msg2, final long timeStamp) {
        black(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void black(final Object msg1, Object msg2) {
        black(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void black(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void black(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_black(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void black(final int level, final Object[] msgs, final long timeStamp) {
        black(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void black(final int level, final String[] msgs, final long timeStamp) {
        black(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void black(final int level, final Object... msgs) {
        black(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void black(final int level, final String... msgs) {
        black(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void black(final String[] msgs, final long timeStamp) {
        black(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void black(final String... msgs) {
        black(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void black(final Object[] msgs, final long timeStamp) {
        black(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void black(final Object... msgs) {
        black(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_black(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.black_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.black_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void red(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void red(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message);
    }

    public static void red(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void red(final int level, final Object message, final long timeStamp) {
        red(level, message, timeStamp, true);
    }

    public static void red(final int level, final String message, final long timeStamp) {
        red(level, message, timeStamp, true);
    }

    public static void red(final int level, final Exception message, final long timeStamp) {
        red(level, message, timeStamp, true);
    }

    public static void red(final int level, final Object message) {
        red(level, message, System.currentTimeMillis(), true);
    }

    public static void red(final int level, final String message) {
        red(level, message, System.currentTimeMillis(), true);
    }

    public static void red(final int level, final Exception message) {
        red(level, message, System.currentTimeMillis(), true);
    }

    public static void red(final String message, final long timeStamp) {
        red(Level.INFO, message, timeStamp, true);
    }

    public static void red(final String message) {
        red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void red(final Object message, final long timeStamp) {
        red(Level.INFO, message, timeStamp, true);
    }

    public static void red(final Object message) {
        red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void red(final Exception exception, final long timeStamp) {
        red(Level.INFO, exception, timeStamp, true);
    }

    public static void red(final Exception exception) {
        red(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void red(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void red(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void red(final int level, final Object msg1, Object msg2, final long timeStamp) {
        red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void red(final int level, final String msg1, String msg2, final long timeStamp) {
        red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void red(final int level, final Object msg1, Object msg2) {
        red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void red(final int level, final String msg1, String msg2) {
        red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void red(final String msg1, String msg2, final long timeStamp) {
        red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void red(final String msg1, String msg2) {
        red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void red(final Object msg1, Object msg2, final long timeStamp) {
        red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void red(final Object msg1, Object msg2) {
        red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void red(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void red(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void red(final int level, final Object[] msgs, final long timeStamp) {
        red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void red(final int level, final String[] msgs, final long timeStamp) {
        red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void red(final int level, final Object... msgs) {
        red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void red(final int level, final String... msgs) {
        red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void red(final String[] msgs, final long timeStamp) {
        red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void red(final String... msgs) {
        red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void red(final Object[] msgs, final long timeStamp) {
        red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void red(final Object... msgs) {
        red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_red(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.red_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.red_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void green(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void green(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, message);
    }

    public static void green(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void green(final int level, final Object message, final long timeStamp) {
        green(level, message, timeStamp, true);
    }

    public static void green(final int level, final String message, final long timeStamp) {
        green(level, message, timeStamp, true);
    }

    public static void green(final int level, final Exception message, final long timeStamp) {
        green(level, message, timeStamp, true);
    }

    public static void green(final int level, final Object message) {
        green(level, message, System.currentTimeMillis(), true);
    }

    public static void green(final int level, final String message) {
        green(level, message, System.currentTimeMillis(), true);
    }

    public static void green(final int level, final Exception message) {
        green(level, message, System.currentTimeMillis(), true);
    }

    public static void green(final String message, final long timeStamp) {
        green(Level.INFO, message, timeStamp, true);
    }

    public static void green(final String message) {
        green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void green(final Object message, final long timeStamp) {
        green(Level.INFO, message, timeStamp, true);
    }

    public static void green(final Object message) {
        green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void green(final Exception exception, final long timeStamp) {
        green(Level.INFO, exception, timeStamp, true);
    }

    public static void green(final Exception exception) {
        green(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void green(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void green(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void green(final int level, final Object msg1, Object msg2, final long timeStamp) {
        green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void green(final int level, final String msg1, String msg2, final long timeStamp) {
        green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void green(final int level, final Object msg1, Object msg2) {
        green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void green(final int level, final String msg1, String msg2) {
        green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void green(final String msg1, String msg2, final long timeStamp) {
        green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void green(final String msg1, String msg2) {
        green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void green(final Object msg1, Object msg2, final long timeStamp) {
        green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void green(final Object msg1, Object msg2) {
        green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void green(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void green(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void green(final int level, final Object[] msgs, final long timeStamp) {
        green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void green(final int level, final String[] msgs, final long timeStamp) {
        green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void green(final int level, final Object... msgs) {
        green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void green(final int level, final String... msgs) {
        green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void green(final String[] msgs, final long timeStamp) {
        green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void green(final String... msgs) {
        green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void green(final Object[] msgs, final long timeStamp) {
        green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void green(final Object... msgs) {
        green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_green(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.green_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.green_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void yellow(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void yellow(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, message);
    }

    public static void yellow(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void yellow(final int level, final Object message, final long timeStamp) {
        yellow(level, message, timeStamp, true);
    }

    public static void yellow(final int level, final String message, final long timeStamp) {
        yellow(level, message, timeStamp, true);
    }

    public static void yellow(final int level, final Exception message, final long timeStamp) {
        yellow(level, message, timeStamp, true);
    }

    public static void yellow(final int level, final Object message) {
        yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void yellow(final int level, final String message) {
        yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void yellow(final int level, final Exception message) {
        yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void yellow(final String message, final long timeStamp) {
        yellow(Level.INFO, message, timeStamp, true);
    }

    public static void yellow(final String message) {
        yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void yellow(final Object message, final long timeStamp) {
        yellow(Level.INFO, message, timeStamp, true);
    }

    public static void yellow(final Object message) {
        yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void yellow(final Exception exception, final long timeStamp) {
        yellow(Level.INFO, exception, timeStamp, true);
    }

    public static void yellow(final Exception exception) {
        yellow(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void yellow(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void yellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void yellow(final int level, final Object msg1, Object msg2, final long timeStamp) {
        yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void yellow(final int level, final String msg1, String msg2, final long timeStamp) {
        yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void yellow(final int level, final Object msg1, Object msg2) {
        yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void yellow(final int level, final String msg1, String msg2) {
        yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void yellow(final String msg1, String msg2, final long timeStamp) {
        yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void yellow(final String msg1, String msg2) {
        yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void yellow(final Object msg1, Object msg2, final long timeStamp) {
        yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void yellow(final Object msg1, Object msg2) {
        yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void yellow(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void yellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void yellow(final int level, final Object[] msgs, final long timeStamp) {
        yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void yellow(final int level, final String[] msgs, final long timeStamp) {
        yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void yellow(final int level, final Object... msgs) {
        yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void yellow(final int level, final String... msgs) {
        yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void yellow(final String[] msgs, final long timeStamp) {
        yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void yellow(final String... msgs) {
        yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void yellow(final Object[] msgs, final long timeStamp) {
        yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void yellow(final Object... msgs) {
        yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_yellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.yellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.yellow_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void blue(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void blue(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, message);
    }

    public static void blue(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void blue(final int level, final Object message, final long timeStamp) {
        blue(level, message, timeStamp, true);
    }

    public static void blue(final int level, final String message, final long timeStamp) {
        blue(level, message, timeStamp, true);
    }

    public static void blue(final int level, final Exception message, final long timeStamp) {
        blue(level, message, timeStamp, true);
    }

    public static void blue(final int level, final Object message) {
        blue(level, message, System.currentTimeMillis(), true);
    }

    public static void blue(final int level, final String message) {
        blue(level, message, System.currentTimeMillis(), true);
    }

    public static void blue(final int level, final Exception message) {
        blue(level, message, System.currentTimeMillis(), true);
    }

    public static void blue(final String message, final long timeStamp) {
        blue(Level.INFO, message, timeStamp, true);
    }

    public static void blue(final String message) {
        blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void blue(final Object message, final long timeStamp) {
        blue(Level.INFO, message, timeStamp, true);
    }

    public static void blue(final Object message) {
        blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void blue(final Exception exception, final long timeStamp) {
        blue(Level.INFO, exception, timeStamp, true);
    }

    public static void blue(final Exception exception) {
        blue(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void blue(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void blue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void blue(final int level, final Object msg1, Object msg2, final long timeStamp) {
        blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void blue(final int level, final String msg1, String msg2, final long timeStamp) {
        blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void blue(final int level, final Object msg1, Object msg2) {
        blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void blue(final int level, final String msg1, String msg2) {
        blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void blue(final String msg1, String msg2, final long timeStamp) {
        blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void blue(final String msg1, String msg2) {
        blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void blue(final Object msg1, Object msg2, final long timeStamp) {
        blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void blue(final Object msg1, Object msg2) {
        blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void blue(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void blue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void blue(final int level, final Object[] msgs, final long timeStamp) {
        blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void blue(final int level, final String[] msgs, final long timeStamp) {
        blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void blue(final int level, final Object... msgs) {
        blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void blue(final int level, final String... msgs) {
        blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void blue(final String[] msgs, final long timeStamp) {
        blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void blue(final String... msgs) {
        blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void blue(final Object[] msgs, final long timeStamp) {
        blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void blue(final Object... msgs) {
        blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_blue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.blue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.blue_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void magenta(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void magenta(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, message);
    }

    public static void magenta(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void magenta(final int level, final Object message, final long timeStamp) {
        magenta(level, message, timeStamp, true);
    }

    public static void magenta(final int level, final String message, final long timeStamp) {
        magenta(level, message, timeStamp, true);
    }

    public static void magenta(final int level, final Exception message, final long timeStamp) {
        magenta(level, message, timeStamp, true);
    }

    public static void magenta(final int level, final Object message) {
        magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void magenta(final int level, final String message) {
        magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void magenta(final int level, final Exception message) {
        magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void magenta(final String message, final long timeStamp) {
        magenta(Level.INFO, message, timeStamp, true);
    }

    public static void magenta(final String message) {
        magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void magenta(final Object message, final long timeStamp) {
        magenta(Level.INFO, message, timeStamp, true);
    }

    public static void magenta(final Object message) {
        magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void magenta(final Exception exception, final long timeStamp) {
        magenta(Level.INFO, exception, timeStamp, true);
    }

    public static void magenta(final Exception exception) {
        magenta(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void magenta(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void magenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void magenta(final int level, final Object msg1, Object msg2, final long timeStamp) {
        magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void magenta(final int level, final String msg1, String msg2, final long timeStamp) {
        magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void magenta(final int level, final Object msg1, Object msg2) {
        magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void magenta(final int level, final String msg1, String msg2) {
        magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void magenta(final String msg1, String msg2, final long timeStamp) {
        magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void magenta(final String msg1, String msg2) {
        magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void magenta(final Object msg1, Object msg2, final long timeStamp) {
        magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void magenta(final Object msg1, Object msg2) {
        magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void magenta(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void magenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void magenta(final int level, final Object[] msgs, final long timeStamp) {
        magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void magenta(final int level, final String[] msgs, final long timeStamp) {
        magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void magenta(final int level, final Object... msgs) {
        magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void magenta(final int level, final String... msgs) {
        magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void magenta(final String[] msgs, final long timeStamp) {
        magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void magenta(final String... msgs) {
        magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void magenta(final Object[] msgs, final long timeStamp) {
        magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void magenta(final Object... msgs) {
        magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_magenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.magenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.magenta_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void cyan(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void cyan(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, message);
    }

    public static void cyan(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void cyan(final int level, final Object message, final long timeStamp) {
        cyan(level, message, timeStamp, true);
    }

    public static void cyan(final int level, final String message, final long timeStamp) {
        cyan(level, message, timeStamp, true);
    }

    public static void cyan(final int level, final Exception message, final long timeStamp) {
        cyan(level, message, timeStamp, true);
    }

    public static void cyan(final int level, final Object message) {
        cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void cyan(final int level, final String message) {
        cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void cyan(final int level, final Exception message) {
        cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void cyan(final String message, final long timeStamp) {
        cyan(Level.INFO, message, timeStamp, true);
    }

    public static void cyan(final String message) {
        cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void cyan(final Object message, final long timeStamp) {
        cyan(Level.INFO, message, timeStamp, true);
    }

    public static void cyan(final Object message) {
        cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void cyan(final Exception exception, final long timeStamp) {
        cyan(Level.INFO, exception, timeStamp, true);
    }

    public static void cyan(final Exception exception) {
        cyan(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void cyan(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void cyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void cyan(final int level, final Object msg1, Object msg2, final long timeStamp) {
        cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void cyan(final int level, final String msg1, String msg2, final long timeStamp) {
        cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void cyan(final int level, final Object msg1, Object msg2) {
        cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void cyan(final int level, final String msg1, String msg2) {
        cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void cyan(final String msg1, String msg2, final long timeStamp) {
        cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void cyan(final String msg1, String msg2) {
        cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void cyan(final Object msg1, Object msg2, final long timeStamp) {
        cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void cyan(final Object msg1, Object msg2) {
        cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void cyan(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void cyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void cyan(final int level, final Object[] msgs, final long timeStamp) {
        cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void cyan(final int level, final String[] msgs, final long timeStamp) {
        cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void cyan(final int level, final Object... msgs) {
        cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void cyan(final int level, final String... msgs) {
        cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void cyan(final String[] msgs, final long timeStamp) {
        cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void cyan(final String... msgs) {
        cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void cyan(final Object[] msgs, final long timeStamp) {
        cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void cyan(final Object... msgs) {
        cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_cyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.cyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.cyan_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void white(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void white(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, message);
    }

    public static void white(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void white(final int level, final Object message, final long timeStamp) {
        white(level, message, timeStamp, true);
    }

    public static void white(final int level, final String message, final long timeStamp) {
        white(level, message, timeStamp, true);
    }

    public static void white(final int level, final Exception message, final long timeStamp) {
        white(level, message, timeStamp, true);
    }

    public static void white(final int level, final Object message) {
        white(level, message, System.currentTimeMillis(), true);
    }

    public static void white(final int level, final String message) {
        white(level, message, System.currentTimeMillis(), true);
    }

    public static void white(final int level, final Exception message) {
        white(level, message, System.currentTimeMillis(), true);
    }

    public static void white(final String message, final long timeStamp) {
        white(Level.INFO, message, timeStamp, true);
    }

    public static void white(final String message) {
        white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void white(final Object message, final long timeStamp) {
        white(Level.INFO, message, timeStamp, true);
    }

    public static void white(final Object message) {
        white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void white(final Exception exception, final long timeStamp) {
        white(Level.INFO, exception, timeStamp, true);
    }

    public static void white(final Exception exception) {
        white(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void white(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void white(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void white(final int level, final Object msg1, Object msg2, final long timeStamp) {
        white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void white(final int level, final String msg1, String msg2, final long timeStamp) {
        white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void white(final int level, final Object msg1, Object msg2) {
        white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void white(final int level, final String msg1, String msg2) {
        white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void white(final String msg1, String msg2, final long timeStamp) {
        white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void white(final String msg1, String msg2) {
        white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void white(final Object msg1, Object msg2, final long timeStamp) {
        white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void white(final Object msg1, Object msg2) {
        white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void white(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void white(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void white(final int level, final Object[] msgs, final long timeStamp) {
        white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void white(final int level, final String[] msgs, final long timeStamp) {
        white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void white(final int level, final Object... msgs) {
        white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void white(final int level, final String... msgs) {
        white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void white(final String[] msgs, final long timeStamp) {
        white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void white(final String... msgs) {
        white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void white(final Object[] msgs, final long timeStamp) {
        white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void white(final Object... msgs) {
        white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_white(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.white_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.white_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void gray(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void gray(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, message);
    }

    public static void gray(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void gray(final int level, final Object message, final long timeStamp) {
        gray(level, message, timeStamp, true);
    }

    public static void gray(final int level, final String message, final long timeStamp) {
        gray(level, message, timeStamp, true);
    }

    public static void gray(final int level, final Exception message, final long timeStamp) {
        gray(level, message, timeStamp, true);
    }

    public static void gray(final int level, final Object message) {
        gray(level, message, System.currentTimeMillis(), true);
    }

    public static void gray(final int level, final String message) {
        gray(level, message, System.currentTimeMillis(), true);
    }

    public static void gray(final int level, final Exception message) {
        gray(level, message, System.currentTimeMillis(), true);
    }

    public static void gray(final String message, final long timeStamp) {
        gray(Level.INFO, message, timeStamp, true);
    }

    public static void gray(final String message) {
        gray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void gray(final Object message, final long timeStamp) {
        gray(Level.INFO, message, timeStamp, true);
    }

    public static void gray(final Object message) {
        gray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void gray(final Exception exception, final long timeStamp) {
        gray(Level.INFO, exception, timeStamp, true);
    }

    public static void gray(final Exception exception) {
        gray(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void gray(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void gray(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void gray(final int level, final Object msg1, Object msg2, final long timeStamp) {
        gray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void gray(final int level, final String msg1, String msg2, final long timeStamp) {
        gray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void gray(final int level, final Object msg1, Object msg2) {
        gray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void gray(final int level, final String msg1, String msg2) {
        gray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void gray(final String msg1, String msg2, final long timeStamp) {
        gray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void gray(final String msg1, String msg2) {
        gray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void gray(final Object msg1, Object msg2, final long timeStamp) {
        gray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void gray(final Object msg1, Object msg2) {
        gray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void gray(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void gray(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void gray(final int level, final Object[] msgs, final long timeStamp) {
        gray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void gray(final int level, final String[] msgs, final long timeStamp) {
        gray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void gray(final int level, final Object... msgs) {
        gray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void gray(final int level, final String... msgs) {
        gray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void gray(final String[] msgs, final long timeStamp) {
        gray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void gray(final String... msgs) {
        gray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void gray(final Object[] msgs, final long timeStamp) {
        gray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void gray(final Object... msgs) {
        gray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_gray(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.gray_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.gray_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void grey(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void grey(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, message);
    }

    public static void grey(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void grey(final int level, final Object message, final long timeStamp) {
        grey(level, message, timeStamp, true);
    }

    public static void grey(final int level, final String message, final long timeStamp) {
        grey(level, message, timeStamp, true);
    }

    public static void grey(final int level, final Exception message, final long timeStamp) {
        grey(level, message, timeStamp, true);
    }

    public static void grey(final int level, final Object message) {
        grey(level, message, System.currentTimeMillis(), true);
    }

    public static void grey(final int level, final String message) {
        grey(level, message, System.currentTimeMillis(), true);
    }

    public static void grey(final int level, final Exception message) {
        grey(level, message, System.currentTimeMillis(), true);
    }

    public static void grey(final String message, final long timeStamp) {
        grey(Level.INFO, message, timeStamp, true);
    }

    public static void grey(final String message) {
        grey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void grey(final Object message, final long timeStamp) {
        grey(Level.INFO, message, timeStamp, true);
    }

    public static void grey(final Object message) {
        grey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void grey(final Exception exception, final long timeStamp) {
        grey(Level.INFO, exception, timeStamp, true);
    }

    public static void grey(final Exception exception) {
        grey(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void grey(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void grey(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void grey(final int level, final Object msg1, Object msg2, final long timeStamp) {
        grey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void grey(final int level, final String msg1, String msg2, final long timeStamp) {
        grey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void grey(final int level, final Object msg1, Object msg2) {
        grey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void grey(final int level, final String msg1, String msg2) {
        grey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void grey(final String msg1, String msg2, final long timeStamp) {
        grey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void grey(final String msg1, String msg2) {
        grey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void grey(final Object msg1, Object msg2, final long timeStamp) {
        grey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void grey(final Object msg1, Object msg2) {
        grey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void grey(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void grey(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void grey(final int level, final Object[] msgs, final long timeStamp) {
        grey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void grey(final int level, final String[] msgs, final long timeStamp) {
        grey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void grey(final int level, final Object... msgs) {
        grey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void grey(final int level, final String... msgs) {
        grey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void grey(final String[] msgs, final long timeStamp) {
        grey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void grey(final String... msgs) {
        grey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void grey(final Object[] msgs, final long timeStamp) {
        grey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void grey(final Object... msgs) {
        grey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_grey(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.grey_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.grey_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_red(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_red(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message);
    }

    public static void bright_red(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_red(final int level, final Object message, final long timeStamp) {
        bright_red(level, message, timeStamp, true);
    }

    public static void bright_red(final int level, final String message, final long timeStamp) {
        bright_red(level, message, timeStamp, true);
    }

    public static void bright_red(final int level, final Exception message, final long timeStamp) {
        bright_red(level, message, timeStamp, true);
    }

    public static void bright_red(final int level, final Object message) {
        bright_red(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_red(final int level, final String message) {
        bright_red(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_red(final int level, final Exception message) {
        bright_red(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_red(final String message, final long timeStamp) {
        bright_red(Level.INFO, message, timeStamp, true);
    }

    public static void bright_red(final String message) {
        bright_red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_red(final Object message, final long timeStamp) {
        bright_red(Level.INFO, message, timeStamp, true);
    }

    public static void bright_red(final Object message) {
        bright_red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_red(final Exception exception, final long timeStamp) {
        bright_red(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_red(final Exception exception) {
        bright_red(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_red(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_red(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_red(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_red(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_red(final int level, final Object msg1, Object msg2) {
        bright_red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_red(final int level, final String msg1, String msg2) {
        bright_red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_red(final String msg1, String msg2, final long timeStamp) {
        bright_red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_red(final String msg1, String msg2) {
        bright_red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_red(final Object msg1, Object msg2, final long timeStamp) {
        bright_red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_red(final Object msg1, Object msg2) {
        bright_red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_red(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_red(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_red(final int level, final Object[] msgs, final long timeStamp) {
        bright_red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_red(final int level, final String[] msgs, final long timeStamp) {
        bright_red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_red(final int level, final Object... msgs) {
        bright_red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_red(final int level, final String... msgs) {
        bright_red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_red(final String[] msgs, final long timeStamp) {
        bright_red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_red(final String... msgs) {
        bright_red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_red(final Object[] msgs, final long timeStamp) {
        bright_red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_red(final Object... msgs) {
        bright_red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_red(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_red_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_red_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_green(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_green(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, message);
    }

    public static void bright_green(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_green(final int level, final Object message, final long timeStamp) {
        bright_green(level, message, timeStamp, true);
    }

    public static void bright_green(final int level, final String message, final long timeStamp) {
        bright_green(level, message, timeStamp, true);
    }

    public static void bright_green(final int level, final Exception message, final long timeStamp) {
        bright_green(level, message, timeStamp, true);
    }

    public static void bright_green(final int level, final Object message) {
        bright_green(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_green(final int level, final String message) {
        bright_green(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_green(final int level, final Exception message) {
        bright_green(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_green(final String message, final long timeStamp) {
        bright_green(Level.INFO, message, timeStamp, true);
    }

    public static void bright_green(final String message) {
        bright_green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_green(final Object message, final long timeStamp) {
        bright_green(Level.INFO, message, timeStamp, true);
    }

    public static void bright_green(final Object message) {
        bright_green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_green(final Exception exception, final long timeStamp) {
        bright_green(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_green(final Exception exception) {
        bright_green(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_green(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_green(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_green(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_green(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_green(final int level, final Object msg1, Object msg2) {
        bright_green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_green(final int level, final String msg1, String msg2) {
        bright_green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_green(final String msg1, String msg2, final long timeStamp) {
        bright_green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_green(final String msg1, String msg2) {
        bright_green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_green(final Object msg1, Object msg2, final long timeStamp) {
        bright_green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_green(final Object msg1, Object msg2) {
        bright_green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_green(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_green(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_green(final int level, final Object[] msgs, final long timeStamp) {
        bright_green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_green(final int level, final String[] msgs, final long timeStamp) {
        bright_green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_green(final int level, final Object... msgs) {
        bright_green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_green(final int level, final String... msgs) {
        bright_green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_green(final String[] msgs, final long timeStamp) {
        bright_green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_green(final String... msgs) {
        bright_green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_green(final Object[] msgs, final long timeStamp) {
        bright_green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_green(final Object... msgs) {
        bright_green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_green(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_green_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_green_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_yellow(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_yellow(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message);
    }

    public static void bright_yellow(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_yellow(final int level, final Object message, final long timeStamp) {
        bright_yellow(level, message, timeStamp, true);
    }

    public static void bright_yellow(final int level, final String message, final long timeStamp) {
        bright_yellow(level, message, timeStamp, true);
    }

    public static void bright_yellow(final int level, final Exception message, final long timeStamp) {
        bright_yellow(level, message, timeStamp, true);
    }

    public static void bright_yellow(final int level, final Object message) {
        bright_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final int level, final String message) {
        bright_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final int level, final Exception message) {
        bright_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final String message, final long timeStamp) {
        bright_yellow(Level.INFO, message, timeStamp, true);
    }

    public static void bright_yellow(final String message) {
        bright_yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final Object message, final long timeStamp) {
        bright_yellow(Level.INFO, message, timeStamp, true);
    }

    public static void bright_yellow(final Object message) {
        bright_yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final Exception exception, final long timeStamp) {
        bright_yellow(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_yellow(final Exception exception) {
        bright_yellow(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_yellow(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_yellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_yellow(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_yellow(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_yellow(final int level, final Object msg1, Object msg2) {
        bright_yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final int level, final String msg1, String msg2) {
        bright_yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final String msg1, String msg2, final long timeStamp) {
        bright_yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_yellow(final String msg1, String msg2) {
        bright_yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final Object msg1, Object msg2, final long timeStamp) {
        bright_yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_yellow(final Object msg1, Object msg2) {
        bright_yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_yellow(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_yellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_yellow(final int level, final Object[] msgs, final long timeStamp) {
        bright_yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_yellow(final int level, final String[] msgs, final long timeStamp) {
        bright_yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_yellow(final int level, final Object... msgs) {
        bright_yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final int level, final String... msgs) {
        bright_yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final String[] msgs, final long timeStamp) {
        bright_yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_yellow(final String... msgs) {
        bright_yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_yellow(final Object[] msgs, final long timeStamp) {
        bright_yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_yellow(final Object... msgs) {
        bright_yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_yellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_yellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_yellow_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_blue(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_blue(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, message);
    }

    public static void bright_blue(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_blue(final int level, final Object message, final long timeStamp) {
        bright_blue(level, message, timeStamp, true);
    }

    public static void bright_blue(final int level, final String message, final long timeStamp) {
        bright_blue(level, message, timeStamp, true);
    }

    public static void bright_blue(final int level, final Exception message, final long timeStamp) {
        bright_blue(level, message, timeStamp, true);
    }

    public static void bright_blue(final int level, final Object message) {
        bright_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final int level, final String message) {
        bright_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final int level, final Exception message) {
        bright_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final String message, final long timeStamp) {
        bright_blue(Level.INFO, message, timeStamp, true);
    }

    public static void bright_blue(final String message) {
        bright_blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final Object message, final long timeStamp) {
        bright_blue(Level.INFO, message, timeStamp, true);
    }

    public static void bright_blue(final Object message) {
        bright_blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final Exception exception, final long timeStamp) {
        bright_blue(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_blue(final Exception exception) {
        bright_blue(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_blue(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_blue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_blue(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_blue(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_blue(final int level, final Object msg1, Object msg2) {
        bright_blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final int level, final String msg1, String msg2) {
        bright_blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final String msg1, String msg2, final long timeStamp) {
        bright_blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_blue(final String msg1, String msg2) {
        bright_blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_blue(final Object msg1, Object msg2, final long timeStamp) {
        bright_blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_blue(final Object msg1, Object msg2) {
        bright_blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_blue(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_blue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_blue(final int level, final Object[] msgs, final long timeStamp) {
        bright_blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_blue(final int level, final String[] msgs, final long timeStamp) {
        bright_blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_blue(final int level, final Object... msgs) {
        bright_blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_blue(final int level, final String... msgs) {
        bright_blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_blue(final String[] msgs, final long timeStamp) {
        bright_blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_blue(final String... msgs) {
        bright_blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_blue(final Object[] msgs, final long timeStamp) {
        bright_blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_blue(final Object... msgs) {
        bright_blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_blue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_blue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_blue_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_magenta(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_magenta(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, message);
    }

    public static void bright_magenta(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_magenta(final int level, final Object message, final long timeStamp) {
        bright_magenta(level, message, timeStamp, true);
    }

    public static void bright_magenta(final int level, final String message, final long timeStamp) {
        bright_magenta(level, message, timeStamp, true);
    }

    public static void bright_magenta(final int level, final Exception message, final long timeStamp) {
        bright_magenta(level, message, timeStamp, true);
    }

    public static void bright_magenta(final int level, final Object message) {
        bright_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final int level, final String message) {
        bright_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final int level, final Exception message) {
        bright_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final String message, final long timeStamp) {
        bright_magenta(Level.INFO, message, timeStamp, true);
    }

    public static void bright_magenta(final String message) {
        bright_magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final Object message, final long timeStamp) {
        bright_magenta(Level.INFO, message, timeStamp, true);
    }

    public static void bright_magenta(final Object message) {
        bright_magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final Exception exception, final long timeStamp) {
        bright_magenta(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_magenta(final Exception exception) {
        bright_magenta(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_magenta(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_magenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_magenta(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_magenta(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_magenta(final int level, final Object msg1, Object msg2) {
        bright_magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final int level, final String msg1, String msg2) {
        bright_magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final String msg1, String msg2, final long timeStamp) {
        bright_magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_magenta(final String msg1, String msg2) {
        bright_magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final Object msg1, Object msg2, final long timeStamp) {
        bright_magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_magenta(final Object msg1, Object msg2) {
        bright_magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_magenta(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_magenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_magenta(final int level, final Object[] msgs, final long timeStamp) {
        bright_magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_magenta(final int level, final String[] msgs, final long timeStamp) {
        bright_magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_magenta(final int level, final Object... msgs) {
        bright_magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final int level, final String... msgs) {
        bright_magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final String[] msgs, final long timeStamp) {
        bright_magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_magenta(final String... msgs) {
        bright_magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_magenta(final Object[] msgs, final long timeStamp) {
        bright_magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_magenta(final Object... msgs) {
        bright_magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_magenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_magenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_magenta_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_cyan(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_cyan(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message);
    }

    public static void bright_cyan(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_cyan(final int level, final Object message, final long timeStamp) {
        bright_cyan(level, message, timeStamp, true);
    }

    public static void bright_cyan(final int level, final String message, final long timeStamp) {
        bright_cyan(level, message, timeStamp, true);
    }

    public static void bright_cyan(final int level, final Exception message, final long timeStamp) {
        bright_cyan(level, message, timeStamp, true);
    }

    public static void bright_cyan(final int level, final Object message) {
        bright_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final int level, final String message) {
        bright_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final int level, final Exception message) {
        bright_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final String message, final long timeStamp) {
        bright_cyan(Level.INFO, message, timeStamp, true);
    }

    public static void bright_cyan(final String message) {
        bright_cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final Object message, final long timeStamp) {
        bright_cyan(Level.INFO, message, timeStamp, true);
    }

    public static void bright_cyan(final Object message) {
        bright_cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final Exception exception, final long timeStamp) {
        bright_cyan(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_cyan(final Exception exception) {
        bright_cyan(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_cyan(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_cyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_cyan(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_cyan(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_cyan(final int level, final Object msg1, Object msg2) {
        bright_cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final int level, final String msg1, String msg2) {
        bright_cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final String msg1, String msg2, final long timeStamp) {
        bright_cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_cyan(final String msg1, String msg2) {
        bright_cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final Object msg1, Object msg2, final long timeStamp) {
        bright_cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_cyan(final Object msg1, Object msg2) {
        bright_cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_cyan(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_cyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_cyan(final int level, final Object[] msgs, final long timeStamp) {
        bright_cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_cyan(final int level, final String[] msgs, final long timeStamp) {
        bright_cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_cyan(final int level, final Object... msgs) {
        bright_cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final int level, final String... msgs) {
        bright_cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final String[] msgs, final long timeStamp) {
        bright_cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_cyan(final String... msgs) {
        bright_cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_cyan(final Object[] msgs, final long timeStamp) {
        bright_cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_cyan(final Object... msgs) {
        bright_cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_cyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_cyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_cyan_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_white(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_white(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, message);
    }

    public static void bright_white(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_white(final int level, final Object message, final long timeStamp) {
        bright_white(level, message, timeStamp, true);
    }

    public static void bright_white(final int level, final String message, final long timeStamp) {
        bright_white(level, message, timeStamp, true);
    }

    public static void bright_white(final int level, final Exception message, final long timeStamp) {
        bright_white(level, message, timeStamp, true);
    }

    public static void bright_white(final int level, final Object message) {
        bright_white(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_white(final int level, final String message) {
        bright_white(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_white(final int level, final Exception message) {
        bright_white(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_white(final String message, final long timeStamp) {
        bright_white(Level.INFO, message, timeStamp, true);
    }

    public static void bright_white(final String message) {
        bright_white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_white(final Object message, final long timeStamp) {
        bright_white(Level.INFO, message, timeStamp, true);
    }

    public static void bright_white(final Object message) {
        bright_white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_white(final Exception exception, final long timeStamp) {
        bright_white(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_white(final Exception exception) {
        bright_white(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_white(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_white(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_white(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_white(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_white(final int level, final Object msg1, Object msg2) {
        bright_white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_white(final int level, final String msg1, String msg2) {
        bright_white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_white(final String msg1, String msg2, final long timeStamp) {
        bright_white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_white(final String msg1, String msg2) {
        bright_white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_white(final Object msg1, Object msg2, final long timeStamp) {
        bright_white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_white(final Object msg1, Object msg2) {
        bright_white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_white(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_white(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_white(final int level, final Object[] msgs, final long timeStamp) {
        bright_white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_white(final int level, final String[] msgs, final long timeStamp) {
        bright_white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_white(final int level, final Object... msgs) {
        bright_white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_white(final int level, final String... msgs) {
        bright_white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_white(final String[] msgs, final long timeStamp) {
        bright_white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_white(final String... msgs) {
        bright_white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_white(final Object[] msgs, final long timeStamp) {
        bright_white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_white(final Object... msgs) {
        bright_white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_white(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_white_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_white_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_gray(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_gray(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, message);
    }

    public static void bright_gray(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_gray(final int level, final Object message, final long timeStamp) {
        bright_gray(level, message, timeStamp, true);
    }

    public static void bright_gray(final int level, final String message, final long timeStamp) {
        bright_gray(level, message, timeStamp, true);
    }

    public static void bright_gray(final int level, final Exception message, final long timeStamp) {
        bright_gray(level, message, timeStamp, true);
    }

    public static void bright_gray(final int level, final Object message) {
        bright_gray(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final int level, final String message) {
        bright_gray(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final int level, final Exception message) {
        bright_gray(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final String message, final long timeStamp) {
        bright_gray(Level.INFO, message, timeStamp, true);
    }

    public static void bright_gray(final String message) {
        bright_gray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final Object message, final long timeStamp) {
        bright_gray(Level.INFO, message, timeStamp, true);
    }

    public static void bright_gray(final Object message) {
        bright_gray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final Exception exception, final long timeStamp) {
        bright_gray(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_gray(final Exception exception) {
        bright_gray(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_gray(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_gray(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_gray(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_gray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_gray(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_gray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_gray(final int level, final Object msg1, Object msg2) {
        bright_gray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final int level, final String msg1, String msg2) {
        bright_gray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final String msg1, String msg2, final long timeStamp) {
        bright_gray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_gray(final String msg1, String msg2) {
        bright_gray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_gray(final Object msg1, Object msg2, final long timeStamp) {
        bright_gray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_gray(final Object msg1, Object msg2) {
        bright_gray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_gray(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_gray(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_gray(final int level, final Object[] msgs, final long timeStamp) {
        bright_gray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_gray(final int level, final String[] msgs, final long timeStamp) {
        bright_gray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_gray(final int level, final Object... msgs) {
        bright_gray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_gray(final int level, final String... msgs) {
        bright_gray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_gray(final String[] msgs, final long timeStamp) {
        bright_gray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_gray(final String... msgs) {
        bright_gray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_gray(final Object[] msgs, final long timeStamp) {
        bright_gray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_gray(final Object... msgs) {
        bright_gray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_gray(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_gray_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_gray_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bright_grey(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bright_grey(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, message);
    }

    public static void bright_grey(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bright_grey(final int level, final Object message, final long timeStamp) {
        bright_grey(level, message, timeStamp, true);
    }

    public static void bright_grey(final int level, final String message, final long timeStamp) {
        bright_grey(level, message, timeStamp, true);
    }

    public static void bright_grey(final int level, final Exception message, final long timeStamp) {
        bright_grey(level, message, timeStamp, true);
    }

    public static void bright_grey(final int level, final Object message) {
        bright_grey(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final int level, final String message) {
        bright_grey(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final int level, final Exception message) {
        bright_grey(level, message, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final String message, final long timeStamp) {
        bright_grey(Level.INFO, message, timeStamp, true);
    }

    public static void bright_grey(final String message) {
        bright_grey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final Object message, final long timeStamp) {
        bright_grey(Level.INFO, message, timeStamp, true);
    }

    public static void bright_grey(final Object message) {
        bright_grey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final Exception exception, final long timeStamp) {
        bright_grey(Level.INFO, exception, timeStamp, true);
    }

    public static void bright_grey(final Exception exception) {
        bright_grey(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bright_grey(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_grey(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bright_grey(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bright_grey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_grey(final int level, final String msg1, String msg2, final long timeStamp) {
        bright_grey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_grey(final int level, final Object msg1, Object msg2) {
        bright_grey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final int level, final String msg1, String msg2) {
        bright_grey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final String msg1, String msg2, final long timeStamp) {
        bright_grey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_grey(final String msg1, String msg2) {
        bright_grey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bright_grey(final Object msg1, Object msg2, final long timeStamp) {
        bright_grey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bright_grey(final Object msg1, Object msg2) {
        bright_grey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bright_grey(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_grey(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bright_grey(final int level, final Object[] msgs, final long timeStamp) {
        bright_grey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_grey(final int level, final String[] msgs, final long timeStamp) {
        bright_grey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_grey(final int level, final Object... msgs) {
        bright_grey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_grey(final int level, final String... msgs) {
        bright_grey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_grey(final String[] msgs, final long timeStamp) {
        bright_grey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_grey(final String... msgs) {
        bright_grey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bright_grey(final Object[] msgs, final long timeStamp) {
        bright_grey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bright_grey(final Object... msgs) {
        bright_grey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bright_grey(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_grey_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_grey_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgBlack(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgBlack(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, message);
    }

    public static void bgBlack(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgBlack(final int level, final Object message, final long timeStamp) {
        bgBlack(level, message, timeStamp, true);
    }

    public static void bgBlack(final int level, final String message, final long timeStamp) {
        bgBlack(level, message, timeStamp, true);
    }

    public static void bgBlack(final int level, final Exception message, final long timeStamp) {
        bgBlack(level, message, timeStamp, true);
    }

    public static void bgBlack(final int level, final Object message) {
        bgBlack(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final int level, final String message) {
        bgBlack(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final int level, final Exception message) {
        bgBlack(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final String message, final long timeStamp) {
        bgBlack(Level.INFO, message, timeStamp, true);
    }

    public static void bgBlack(final String message) {
        bgBlack(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final Object message, final long timeStamp) {
        bgBlack(Level.INFO, message, timeStamp, true);
    }

    public static void bgBlack(final Object message) {
        bgBlack(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final Exception exception, final long timeStamp) {
        bgBlack(Level.INFO, exception, timeStamp, true);
    }

    public static void bgBlack(final Exception exception) {
        bgBlack(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgBlack(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgBlack(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgBlack(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgBlack(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlack(final int level, final String msg1, String msg2, final long timeStamp) {
        bgBlack(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlack(final int level, final Object msg1, Object msg2) {
        bgBlack(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final int level, final String msg1, String msg2) {
        bgBlack(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final String msg1, String msg2, final long timeStamp) {
        bgBlack(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlack(final String msg1, String msg2) {
        bgBlack(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlack(final Object msg1, Object msg2, final long timeStamp) {
        bgBlack(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlack(final Object msg1, Object msg2) {
        bgBlack(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgBlack(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgBlack(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgBlack(final int level, final Object[] msgs, final long timeStamp) {
        bgBlack(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlack(final int level, final String[] msgs, final long timeStamp) {
        bgBlack(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlack(final int level, final Object... msgs) {
        bgBlack(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlack(final int level, final String... msgs) {
        bgBlack(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlack(final String[] msgs, final long timeStamp) {
        bgBlack(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlack(final String... msgs) {
        bgBlack(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlack(final Object[] msgs, final long timeStamp) {
        bgBlack(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlack(final Object... msgs) {
        bgBlack(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgBlack(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgBlack_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgBlack_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgRed(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgRed(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, message);
    }

    public static void bgRed(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgRed(final int level, final Object message, final long timeStamp) {
        bgRed(level, message, timeStamp, true);
    }

    public static void bgRed(final int level, final String message, final long timeStamp) {
        bgRed(level, message, timeStamp, true);
    }

    public static void bgRed(final int level, final Exception message, final long timeStamp) {
        bgRed(level, message, timeStamp, true);
    }

    public static void bgRed(final int level, final Object message) {
        bgRed(level, message, System.currentTimeMillis(), true);
    }

    public static void bgRed(final int level, final String message) {
        bgRed(level, message, System.currentTimeMillis(), true);
    }

    public static void bgRed(final int level, final Exception message) {
        bgRed(level, message, System.currentTimeMillis(), true);
    }

    public static void bgRed(final String message, final long timeStamp) {
        bgRed(Level.INFO, message, timeStamp, true);
    }

    public static void bgRed(final String message) {
        bgRed(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgRed(final Object message, final long timeStamp) {
        bgRed(Level.INFO, message, timeStamp, true);
    }

    public static void bgRed(final Object message) {
        bgRed(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgRed(final Exception exception, final long timeStamp) {
        bgRed(Level.INFO, exception, timeStamp, true);
    }

    public static void bgRed(final Exception exception) {
        bgRed(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgRed(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgRed(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgRed(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgRed(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgRed(final int level, final String msg1, String msg2, final long timeStamp) {
        bgRed(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgRed(final int level, final Object msg1, Object msg2) {
        bgRed(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgRed(final int level, final String msg1, String msg2) {
        bgRed(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgRed(final String msg1, String msg2, final long timeStamp) {
        bgRed(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgRed(final String msg1, String msg2) {
        bgRed(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgRed(final Object msg1, Object msg2, final long timeStamp) {
        bgRed(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgRed(final Object msg1, Object msg2) {
        bgRed(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgRed(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgRed(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgRed(final int level, final Object[] msgs, final long timeStamp) {
        bgRed(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgRed(final int level, final String[] msgs, final long timeStamp) {
        bgRed(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgRed(final int level, final Object... msgs) {
        bgRed(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgRed(final int level, final String... msgs) {
        bgRed(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgRed(final String[] msgs, final long timeStamp) {
        bgRed(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgRed(final String... msgs) {
        bgRed(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgRed(final Object[] msgs, final long timeStamp) {
        bgRed(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgRed(final Object... msgs) {
        bgRed(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgRed(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgRed_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgRed_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgGreen(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgGreen(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, message);
    }

    public static void bgGreen(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgGreen(final int level, final Object message, final long timeStamp) {
        bgGreen(level, message, timeStamp, true);
    }

    public static void bgGreen(final int level, final String message, final long timeStamp) {
        bgGreen(level, message, timeStamp, true);
    }

    public static void bgGreen(final int level, final Exception message, final long timeStamp) {
        bgGreen(level, message, timeStamp, true);
    }

    public static void bgGreen(final int level, final Object message) {
        bgGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final int level, final String message) {
        bgGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final int level, final Exception message) {
        bgGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final String message, final long timeStamp) {
        bgGreen(Level.INFO, message, timeStamp, true);
    }

    public static void bgGreen(final String message) {
        bgGreen(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final Object message, final long timeStamp) {
        bgGreen(Level.INFO, message, timeStamp, true);
    }

    public static void bgGreen(final Object message) {
        bgGreen(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final Exception exception, final long timeStamp) {
        bgGreen(Level.INFO, exception, timeStamp, true);
    }

    public static void bgGreen(final Exception exception) {
        bgGreen(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgGreen(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgGreen(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgGreen(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgGreen(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgGreen(final int level, final String msg1, String msg2, final long timeStamp) {
        bgGreen(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgGreen(final int level, final Object msg1, Object msg2) {
        bgGreen(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final int level, final String msg1, String msg2) {
        bgGreen(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final String msg1, String msg2, final long timeStamp) {
        bgGreen(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgGreen(final String msg1, String msg2) {
        bgGreen(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgGreen(final Object msg1, Object msg2, final long timeStamp) {
        bgGreen(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgGreen(final Object msg1, Object msg2) {
        bgGreen(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgGreen(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgGreen(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgGreen(final int level, final Object[] msgs, final long timeStamp) {
        bgGreen(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgGreen(final int level, final String[] msgs, final long timeStamp) {
        bgGreen(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgGreen(final int level, final Object... msgs) {
        bgGreen(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgGreen(final int level, final String... msgs) {
        bgGreen(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgGreen(final String[] msgs, final long timeStamp) {
        bgGreen(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgGreen(final String... msgs) {
        bgGreen(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgGreen(final Object[] msgs, final long timeStamp) {
        bgGreen(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgGreen(final Object... msgs) {
        bgGreen(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgGreen(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgGreen_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgGreen_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgYellow(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgYellow(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, message);
    }

    public static void bgYellow(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgYellow(final int level, final Object message, final long timeStamp) {
        bgYellow(level, message, timeStamp, true);
    }

    public static void bgYellow(final int level, final String message, final long timeStamp) {
        bgYellow(level, message, timeStamp, true);
    }

    public static void bgYellow(final int level, final Exception message, final long timeStamp) {
        bgYellow(level, message, timeStamp, true);
    }

    public static void bgYellow(final int level, final Object message) {
        bgYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final int level, final String message) {
        bgYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final int level, final Exception message) {
        bgYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final String message, final long timeStamp) {
        bgYellow(Level.INFO, message, timeStamp, true);
    }

    public static void bgYellow(final String message) {
        bgYellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final Object message, final long timeStamp) {
        bgYellow(Level.INFO, message, timeStamp, true);
    }

    public static void bgYellow(final Object message) {
        bgYellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final Exception exception, final long timeStamp) {
        bgYellow(Level.INFO, exception, timeStamp, true);
    }

    public static void bgYellow(final Exception exception) {
        bgYellow(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgYellow(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgYellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgYellow(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgYellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgYellow(final int level, final String msg1, String msg2, final long timeStamp) {
        bgYellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgYellow(final int level, final Object msg1, Object msg2) {
        bgYellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final int level, final String msg1, String msg2) {
        bgYellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final String msg1, String msg2, final long timeStamp) {
        bgYellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgYellow(final String msg1, String msg2) {
        bgYellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgYellow(final Object msg1, Object msg2, final long timeStamp) {
        bgYellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgYellow(final Object msg1, Object msg2) {
        bgYellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgYellow(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgYellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgYellow(final int level, final Object[] msgs, final long timeStamp) {
        bgYellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgYellow(final int level, final String[] msgs, final long timeStamp) {
        bgYellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgYellow(final int level, final Object... msgs) {
        bgYellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgYellow(final int level, final String... msgs) {
        bgYellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgYellow(final String[] msgs, final long timeStamp) {
        bgYellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgYellow(final String... msgs) {
        bgYellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgYellow(final Object[] msgs, final long timeStamp) {
        bgYellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgYellow(final Object... msgs) {
        bgYellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgYellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgYellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgYellow_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgBlue(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgBlue(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, message);
    }

    public static void bgBlue(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgBlue(final int level, final Object message, final long timeStamp) {
        bgBlue(level, message, timeStamp, true);
    }

    public static void bgBlue(final int level, final String message, final long timeStamp) {
        bgBlue(level, message, timeStamp, true);
    }

    public static void bgBlue(final int level, final Exception message, final long timeStamp) {
        bgBlue(level, message, timeStamp, true);
    }

    public static void bgBlue(final int level, final Object message) {
        bgBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final int level, final String message) {
        bgBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final int level, final Exception message) {
        bgBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final String message, final long timeStamp) {
        bgBlue(Level.INFO, message, timeStamp, true);
    }

    public static void bgBlue(final String message) {
        bgBlue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final Object message, final long timeStamp) {
        bgBlue(Level.INFO, message, timeStamp, true);
    }

    public static void bgBlue(final Object message) {
        bgBlue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final Exception exception, final long timeStamp) {
        bgBlue(Level.INFO, exception, timeStamp, true);
    }

    public static void bgBlue(final Exception exception) {
        bgBlue(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgBlue(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgBlue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgBlue(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgBlue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlue(final int level, final String msg1, String msg2, final long timeStamp) {
        bgBlue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlue(final int level, final Object msg1, Object msg2) {
        bgBlue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final int level, final String msg1, String msg2) {
        bgBlue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final String msg1, String msg2, final long timeStamp) {
        bgBlue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlue(final String msg1, String msg2) {
        bgBlue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgBlue(final Object msg1, Object msg2, final long timeStamp) {
        bgBlue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgBlue(final Object msg1, Object msg2) {
        bgBlue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgBlue(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgBlue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgBlue(final int level, final Object[] msgs, final long timeStamp) {
        bgBlue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlue(final int level, final String[] msgs, final long timeStamp) {
        bgBlue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlue(final int level, final Object... msgs) {
        bgBlue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlue(final int level, final String... msgs) {
        bgBlue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlue(final String[] msgs, final long timeStamp) {
        bgBlue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlue(final String... msgs) {
        bgBlue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgBlue(final Object[] msgs, final long timeStamp) {
        bgBlue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgBlue(final Object... msgs) {
        bgBlue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgBlue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgBlue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgBlue_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgMagenta(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgMagenta(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, message);
    }

    public static void bgMagenta(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgMagenta(final int level, final Object message, final long timeStamp) {
        bgMagenta(level, message, timeStamp, true);
    }

    public static void bgMagenta(final int level, final String message, final long timeStamp) {
        bgMagenta(level, message, timeStamp, true);
    }

    public static void bgMagenta(final int level, final Exception message, final long timeStamp) {
        bgMagenta(level, message, timeStamp, true);
    }

    public static void bgMagenta(final int level, final Object message) {
        bgMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final int level, final String message) {
        bgMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final int level, final Exception message) {
        bgMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final String message, final long timeStamp) {
        bgMagenta(Level.INFO, message, timeStamp, true);
    }

    public static void bgMagenta(final String message) {
        bgMagenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final Object message, final long timeStamp) {
        bgMagenta(Level.INFO, message, timeStamp, true);
    }

    public static void bgMagenta(final Object message) {
        bgMagenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final Exception exception, final long timeStamp) {
        bgMagenta(Level.INFO, exception, timeStamp, true);
    }

    public static void bgMagenta(final Exception exception) {
        bgMagenta(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgMagenta(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgMagenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgMagenta(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgMagenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgMagenta(final int level, final String msg1, String msg2, final long timeStamp) {
        bgMagenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgMagenta(final int level, final Object msg1, Object msg2) {
        bgMagenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final int level, final String msg1, String msg2) {
        bgMagenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final String msg1, String msg2, final long timeStamp) {
        bgMagenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgMagenta(final String msg1, String msg2) {
        bgMagenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final Object msg1, Object msg2, final long timeStamp) {
        bgMagenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgMagenta(final Object msg1, Object msg2) {
        bgMagenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgMagenta(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgMagenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgMagenta(final int level, final Object[] msgs, final long timeStamp) {
        bgMagenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgMagenta(final int level, final String[] msgs, final long timeStamp) {
        bgMagenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgMagenta(final int level, final Object... msgs) {
        bgMagenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final int level, final String... msgs) {
        bgMagenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final String[] msgs, final long timeStamp) {
        bgMagenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgMagenta(final String... msgs) {
        bgMagenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgMagenta(final Object[] msgs, final long timeStamp) {
        bgMagenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgMagenta(final Object... msgs) {
        bgMagenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgMagenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgMagenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgMagenta_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgCyan(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgCyan(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, message);
    }

    public static void bgCyan(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgCyan(final int level, final Object message, final long timeStamp) {
        bgCyan(level, message, timeStamp, true);
    }

    public static void bgCyan(final int level, final String message, final long timeStamp) {
        bgCyan(level, message, timeStamp, true);
    }

    public static void bgCyan(final int level, final Exception message, final long timeStamp) {
        bgCyan(level, message, timeStamp, true);
    }

    public static void bgCyan(final int level, final Object message) {
        bgCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final int level, final String message) {
        bgCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final int level, final Exception message) {
        bgCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final String message, final long timeStamp) {
        bgCyan(Level.INFO, message, timeStamp, true);
    }

    public static void bgCyan(final String message) {
        bgCyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final Object message, final long timeStamp) {
        bgCyan(Level.INFO, message, timeStamp, true);
    }

    public static void bgCyan(final Object message) {
        bgCyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final Exception exception, final long timeStamp) {
        bgCyan(Level.INFO, exception, timeStamp, true);
    }

    public static void bgCyan(final Exception exception) {
        bgCyan(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgCyan(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgCyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgCyan(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgCyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgCyan(final int level, final String msg1, String msg2, final long timeStamp) {
        bgCyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgCyan(final int level, final Object msg1, Object msg2) {
        bgCyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final int level, final String msg1, String msg2) {
        bgCyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final String msg1, String msg2, final long timeStamp) {
        bgCyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgCyan(final String msg1, String msg2) {
        bgCyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgCyan(final Object msg1, Object msg2, final long timeStamp) {
        bgCyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgCyan(final Object msg1, Object msg2) {
        bgCyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgCyan(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgCyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgCyan(final int level, final Object[] msgs, final long timeStamp) {
        bgCyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgCyan(final int level, final String[] msgs, final long timeStamp) {
        bgCyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgCyan(final int level, final Object... msgs) {
        bgCyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgCyan(final int level, final String... msgs) {
        bgCyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgCyan(final String[] msgs, final long timeStamp) {
        bgCyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgCyan(final String... msgs) {
        bgCyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgCyan(final Object[] msgs, final long timeStamp) {
        bgCyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgCyan(final Object... msgs) {
        bgCyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgCyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgCyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgCyan_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void bgWhite(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void bgWhite(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, message);
    }

    public static void bgWhite(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void bgWhite(final int level, final Object message, final long timeStamp) {
        bgWhite(level, message, timeStamp, true);
    }

    public static void bgWhite(final int level, final String message, final long timeStamp) {
        bgWhite(level, message, timeStamp, true);
    }

    public static void bgWhite(final int level, final Exception message, final long timeStamp) {
        bgWhite(level, message, timeStamp, true);
    }

    public static void bgWhite(final int level, final Object message) {
        bgWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final int level, final String message) {
        bgWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final int level, final Exception message) {
        bgWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final String message, final long timeStamp) {
        bgWhite(Level.INFO, message, timeStamp, true);
    }

    public static void bgWhite(final String message) {
        bgWhite(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final Object message, final long timeStamp) {
        bgWhite(Level.INFO, message, timeStamp, true);
    }

    public static void bgWhite(final Object message) {
        bgWhite(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final Exception exception, final long timeStamp) {
        bgWhite(Level.INFO, exception, timeStamp, true);
    }

    public static void bgWhite(final Exception exception) {
        bgWhite(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void bgWhite(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgWhite(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void bgWhite(final int level, final Object msg1, Object msg2, final long timeStamp) {
        bgWhite(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgWhite(final int level, final String msg1, String msg2, final long timeStamp) {
        bgWhite(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgWhite(final int level, final Object msg1, Object msg2) {
        bgWhite(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final int level, final String msg1, String msg2) {
        bgWhite(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final String msg1, String msg2, final long timeStamp) {
        bgWhite(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgWhite(final String msg1, String msg2) {
        bgWhite(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void bgWhite(final Object msg1, Object msg2, final long timeStamp) {
        bgWhite(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void bgWhite(final Object msg1, Object msg2) {
        bgWhite(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void bgWhite(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgWhite(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void bgWhite(final int level, final Object[] msgs, final long timeStamp) {
        bgWhite(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgWhite(final int level, final String[] msgs, final long timeStamp) {
        bgWhite(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgWhite(final int level, final Object... msgs) {
        bgWhite(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgWhite(final int level, final String... msgs) {
        bgWhite(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgWhite(final String[] msgs, final long timeStamp) {
        bgWhite(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgWhite(final String... msgs) {
        bgWhite(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void bgWhite(final Object[] msgs, final long timeStamp) {
        bgWhite(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void bgWhite(final Object... msgs) {
        bgWhite(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    private static void write_bgWhite(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgWhite_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgWhite_close + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
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
    

    // 1-arg methods
    public static void strike(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void strike(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, message);
    }

    public static void strike(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void strike(final int level, final Object message, final long timeStamp) {
        strike(level, message, timeStamp, true);
    }

    public static void strike(final int level, final String message, final long timeStamp) {
        strike(level, message, timeStamp, true);
    }

    public static void strike(final int level, final Exception message, final long timeStamp) {
        strike(level, message, timeStamp, true);
    }

    public static void strike(final int level, final Object message) {
        strike(level, message, System.currentTimeMillis(), true);
    }

    public static void strike(final int level, final String message) {
        strike(level, message, System.currentTimeMillis(), true);
    }

    public static void strike(final int level, final Exception message) {
        strike(level, message, System.currentTimeMillis(), true);
    }

    public static void strike(final String message, final long timeStamp) {
        strike(Level.INFO, message, timeStamp, true);
    }

    public static void strike(final String message) {
        strike(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void strike(final Object message, final long timeStamp) {
        strike(Level.INFO, message, timeStamp, true);
    }

    public static void strike(final Object message) {
        strike(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void strike(final Exception exception, final long timeStamp) {
        strike(Level.INFO, exception, timeStamp, true);
    }

    public static void strike(final Exception exception) {
        strike(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void strike(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void strike(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void strike(final int level, final Object msg1, Object msg2, final long timeStamp) {
        strike(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strike(final int level, final String msg1, String msg2, final long timeStamp) {
        strike(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strike(final int level, final Object msg1, Object msg2) {
        strike(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strike(final int level, final String msg1, String msg2) {
        strike(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strike(final String msg1, String msg2, final long timeStamp) {
        strike(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strike(final String msg1, String msg2) {
        strike(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void strike(final Object msg1, Object msg2, final long timeStamp) {
        strike(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void strike(final Object msg1, Object msg2) {
        strike(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void strike(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void strike(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_strikethrough(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void strike(final int level, final Object[] msgs, final long timeStamp) {
        strike(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strike(final int level, final String[] msgs, final long timeStamp) {
        strike(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strike(final int level, final Object... msgs) {
        strike(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strike(final int level, final String... msgs) {
        strike(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strike(final String[] msgs, final long timeStamp) {
        strike(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strike(final String... msgs) {
        strike(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void strike(final Object[] msgs, final long timeStamp) {
        strike(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void strike(final Object... msgs) {
        strike(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightRed(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightRed(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message);
    }

    public static void brightRed(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightRed(final int level, final Object message, final long timeStamp) {
        brightRed(level, message, timeStamp, true);
    }

    public static void brightRed(final int level, final String message, final long timeStamp) {
        brightRed(level, message, timeStamp, true);
    }

    public static void brightRed(final int level, final Exception message, final long timeStamp) {
        brightRed(level, message, timeStamp, true);
    }

    public static void brightRed(final int level, final Object message) {
        brightRed(level, message, System.currentTimeMillis(), true);
    }

    public static void brightRed(final int level, final String message) {
        brightRed(level, message, System.currentTimeMillis(), true);
    }

    public static void brightRed(final int level, final Exception message) {
        brightRed(level, message, System.currentTimeMillis(), true);
    }

    public static void brightRed(final String message, final long timeStamp) {
        brightRed(Level.INFO, message, timeStamp, true);
    }

    public static void brightRed(final String message) {
        brightRed(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightRed(final Object message, final long timeStamp) {
        brightRed(Level.INFO, message, timeStamp, true);
    }

    public static void brightRed(final Object message) {
        brightRed(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightRed(final Exception exception, final long timeStamp) {
        brightRed(Level.INFO, exception, timeStamp, true);
    }

    public static void brightRed(final Exception exception) {
        brightRed(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightRed(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightRed(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightRed(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightRed(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightRed(final int level, final String msg1, String msg2, final long timeStamp) {
        brightRed(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightRed(final int level, final Object msg1, Object msg2) {
        brightRed(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightRed(final int level, final String msg1, String msg2) {
        brightRed(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightRed(final String msg1, String msg2, final long timeStamp) {
        brightRed(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightRed(final String msg1, String msg2) {
        brightRed(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightRed(final Object msg1, Object msg2, final long timeStamp) {
        brightRed(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightRed(final Object msg1, Object msg2) {
        brightRed(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightRed(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightRed(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightRed(final int level, final Object[] msgs, final long timeStamp) {
        brightRed(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightRed(final int level, final String[] msgs, final long timeStamp) {
        brightRed(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightRed(final int level, final Object... msgs) {
        brightRed(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightRed(final int level, final String... msgs) {
        brightRed(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightRed(final String[] msgs, final long timeStamp) {
        brightRed(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightRed(final String... msgs) {
        brightRed(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightRed(final Object[] msgs, final long timeStamp) {
        brightRed(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightRed(final Object... msgs) {
        brightRed(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightGreen(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightGreen(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, message);
    }

    public static void brightGreen(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightGreen(final int level, final Object message, final long timeStamp) {
        brightGreen(level, message, timeStamp, true);
    }

    public static void brightGreen(final int level, final String message, final long timeStamp) {
        brightGreen(level, message, timeStamp, true);
    }

    public static void brightGreen(final int level, final Exception message, final long timeStamp) {
        brightGreen(level, message, timeStamp, true);
    }

    public static void brightGreen(final int level, final Object message) {
        brightGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final int level, final String message) {
        brightGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final int level, final Exception message) {
        brightGreen(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final String message, final long timeStamp) {
        brightGreen(Level.INFO, message, timeStamp, true);
    }

    public static void brightGreen(final String message) {
        brightGreen(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final Object message, final long timeStamp) {
        brightGreen(Level.INFO, message, timeStamp, true);
    }

    public static void brightGreen(final Object message) {
        brightGreen(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final Exception exception, final long timeStamp) {
        brightGreen(Level.INFO, exception, timeStamp, true);
    }

    public static void brightGreen(final Exception exception) {
        brightGreen(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightGreen(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGreen(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGreen(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightGreen(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGreen(final int level, final String msg1, String msg2, final long timeStamp) {
        brightGreen(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGreen(final int level, final Object msg1, Object msg2) {
        brightGreen(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final int level, final String msg1, String msg2) {
        brightGreen(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final String msg1, String msg2, final long timeStamp) {
        brightGreen(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGreen(final String msg1, String msg2) {
        brightGreen(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGreen(final Object msg1, Object msg2, final long timeStamp) {
        brightGreen(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGreen(final Object msg1, Object msg2) {
        brightGreen(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightGreen(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGreen(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_green(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGreen(final int level, final Object[] msgs, final long timeStamp) {
        brightGreen(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGreen(final int level, final String[] msgs, final long timeStamp) {
        brightGreen(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGreen(final int level, final Object... msgs) {
        brightGreen(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGreen(final int level, final String... msgs) {
        brightGreen(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGreen(final String[] msgs, final long timeStamp) {
        brightGreen(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGreen(final String... msgs) {
        brightGreen(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGreen(final Object[] msgs, final long timeStamp) {
        brightGreen(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGreen(final Object... msgs) {
        brightGreen(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightYellow(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightYellow(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message);
    }

    public static void brightYellow(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightYellow(final int level, final Object message, final long timeStamp) {
        brightYellow(level, message, timeStamp, true);
    }

    public static void brightYellow(final int level, final String message, final long timeStamp) {
        brightYellow(level, message, timeStamp, true);
    }

    public static void brightYellow(final int level, final Exception message, final long timeStamp) {
        brightYellow(level, message, timeStamp, true);
    }

    public static void brightYellow(final int level, final Object message) {
        brightYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final int level, final String message) {
        brightYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final int level, final Exception message) {
        brightYellow(level, message, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final String message, final long timeStamp) {
        brightYellow(Level.INFO, message, timeStamp, true);
    }

    public static void brightYellow(final String message) {
        brightYellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final Object message, final long timeStamp) {
        brightYellow(Level.INFO, message, timeStamp, true);
    }

    public static void brightYellow(final Object message) {
        brightYellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final Exception exception, final long timeStamp) {
        brightYellow(Level.INFO, exception, timeStamp, true);
    }

    public static void brightYellow(final Exception exception) {
        brightYellow(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightYellow(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightYellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightYellow(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightYellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightYellow(final int level, final String msg1, String msg2, final long timeStamp) {
        brightYellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightYellow(final int level, final Object msg1, Object msg2) {
        brightYellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final int level, final String msg1, String msg2) {
        brightYellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final String msg1, String msg2, final long timeStamp) {
        brightYellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightYellow(final String msg1, String msg2) {
        brightYellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightYellow(final Object msg1, Object msg2, final long timeStamp) {
        brightYellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightYellow(final Object msg1, Object msg2) {
        brightYellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightYellow(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightYellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightYellow(final int level, final Object[] msgs, final long timeStamp) {
        brightYellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightYellow(final int level, final String[] msgs, final long timeStamp) {
        brightYellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightYellow(final int level, final Object... msgs) {
        brightYellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightYellow(final int level, final String... msgs) {
        brightYellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightYellow(final String[] msgs, final long timeStamp) {
        brightYellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightYellow(final String... msgs) {
        brightYellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightYellow(final Object[] msgs, final long timeStamp) {
        brightYellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightYellow(final Object... msgs) {
        brightYellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightBlue(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightBlue(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, message);
    }

    public static void brightBlue(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightBlue(final int level, final Object message, final long timeStamp) {
        brightBlue(level, message, timeStamp, true);
    }

    public static void brightBlue(final int level, final String message, final long timeStamp) {
        brightBlue(level, message, timeStamp, true);
    }

    public static void brightBlue(final int level, final Exception message, final long timeStamp) {
        brightBlue(level, message, timeStamp, true);
    }

    public static void brightBlue(final int level, final Object message) {
        brightBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final int level, final String message) {
        brightBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final int level, final Exception message) {
        brightBlue(level, message, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final String message, final long timeStamp) {
        brightBlue(Level.INFO, message, timeStamp, true);
    }

    public static void brightBlue(final String message) {
        brightBlue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final Object message, final long timeStamp) {
        brightBlue(Level.INFO, message, timeStamp, true);
    }

    public static void brightBlue(final Object message) {
        brightBlue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final Exception exception, final long timeStamp) {
        brightBlue(Level.INFO, exception, timeStamp, true);
    }

    public static void brightBlue(final Exception exception) {
        brightBlue(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightBlue(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightBlue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightBlue(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightBlue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightBlue(final int level, final String msg1, String msg2, final long timeStamp) {
        brightBlue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightBlue(final int level, final Object msg1, Object msg2) {
        brightBlue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final int level, final String msg1, String msg2) {
        brightBlue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final String msg1, String msg2, final long timeStamp) {
        brightBlue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightBlue(final String msg1, String msg2) {
        brightBlue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightBlue(final Object msg1, Object msg2, final long timeStamp) {
        brightBlue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightBlue(final Object msg1, Object msg2) {
        brightBlue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightBlue(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightBlue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_blue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightBlue(final int level, final Object[] msgs, final long timeStamp) {
        brightBlue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightBlue(final int level, final String[] msgs, final long timeStamp) {
        brightBlue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightBlue(final int level, final Object... msgs) {
        brightBlue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightBlue(final int level, final String... msgs) {
        brightBlue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightBlue(final String[] msgs, final long timeStamp) {
        brightBlue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightBlue(final String... msgs) {
        brightBlue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightBlue(final Object[] msgs, final long timeStamp) {
        brightBlue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightBlue(final Object... msgs) {
        brightBlue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightMagenta(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightMagenta(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, message);
    }

    public static void brightMagenta(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightMagenta(final int level, final Object message, final long timeStamp) {
        brightMagenta(level, message, timeStamp, true);
    }

    public static void brightMagenta(final int level, final String message, final long timeStamp) {
        brightMagenta(level, message, timeStamp, true);
    }

    public static void brightMagenta(final int level, final Exception message, final long timeStamp) {
        brightMagenta(level, message, timeStamp, true);
    }

    public static void brightMagenta(final int level, final Object message) {
        brightMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final int level, final String message) {
        brightMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final int level, final Exception message) {
        brightMagenta(level, message, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final String message, final long timeStamp) {
        brightMagenta(Level.INFO, message, timeStamp, true);
    }

    public static void brightMagenta(final String message) {
        brightMagenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final Object message, final long timeStamp) {
        brightMagenta(Level.INFO, message, timeStamp, true);
    }

    public static void brightMagenta(final Object message) {
        brightMagenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final Exception exception, final long timeStamp) {
        brightMagenta(Level.INFO, exception, timeStamp, true);
    }

    public static void brightMagenta(final Exception exception) {
        brightMagenta(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightMagenta(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightMagenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightMagenta(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightMagenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightMagenta(final int level, final String msg1, String msg2, final long timeStamp) {
        brightMagenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightMagenta(final int level, final Object msg1, Object msg2) {
        brightMagenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final int level, final String msg1, String msg2) {
        brightMagenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final String msg1, String msg2, final long timeStamp) {
        brightMagenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightMagenta(final String msg1, String msg2) {
        brightMagenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final Object msg1, Object msg2, final long timeStamp) {
        brightMagenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightMagenta(final Object msg1, Object msg2) {
        brightMagenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightMagenta(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightMagenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_magenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightMagenta(final int level, final Object[] msgs, final long timeStamp) {
        brightMagenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightMagenta(final int level, final String[] msgs, final long timeStamp) {
        brightMagenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightMagenta(final int level, final Object... msgs) {
        brightMagenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final int level, final String... msgs) {
        brightMagenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final String[] msgs, final long timeStamp) {
        brightMagenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightMagenta(final String... msgs) {
        brightMagenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightMagenta(final Object[] msgs, final long timeStamp) {
        brightMagenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightMagenta(final Object... msgs) {
        brightMagenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightCyan(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightCyan(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message);
    }

    public static void brightCyan(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightCyan(final int level, final Object message, final long timeStamp) {
        brightCyan(level, message, timeStamp, true);
    }

    public static void brightCyan(final int level, final String message, final long timeStamp) {
        brightCyan(level, message, timeStamp, true);
    }

    public static void brightCyan(final int level, final Exception message, final long timeStamp) {
        brightCyan(level, message, timeStamp, true);
    }

    public static void brightCyan(final int level, final Object message) {
        brightCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final int level, final String message) {
        brightCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final int level, final Exception message) {
        brightCyan(level, message, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final String message, final long timeStamp) {
        brightCyan(Level.INFO, message, timeStamp, true);
    }

    public static void brightCyan(final String message) {
        brightCyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final Object message, final long timeStamp) {
        brightCyan(Level.INFO, message, timeStamp, true);
    }

    public static void brightCyan(final Object message) {
        brightCyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final Exception exception, final long timeStamp) {
        brightCyan(Level.INFO, exception, timeStamp, true);
    }

    public static void brightCyan(final Exception exception) {
        brightCyan(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightCyan(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightCyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightCyan(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightCyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightCyan(final int level, final String msg1, String msg2, final long timeStamp) {
        brightCyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightCyan(final int level, final Object msg1, Object msg2) {
        brightCyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final int level, final String msg1, String msg2) {
        brightCyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final String msg1, String msg2, final long timeStamp) {
        brightCyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightCyan(final String msg1, String msg2) {
        brightCyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightCyan(final Object msg1, Object msg2, final long timeStamp) {
        brightCyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightCyan(final Object msg1, Object msg2) {
        brightCyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightCyan(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightCyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightCyan(final int level, final Object[] msgs, final long timeStamp) {
        brightCyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightCyan(final int level, final String[] msgs, final long timeStamp) {
        brightCyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightCyan(final int level, final Object... msgs) {
        brightCyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightCyan(final int level, final String... msgs) {
        brightCyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightCyan(final String[] msgs, final long timeStamp) {
        brightCyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightCyan(final String... msgs) {
        brightCyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightCyan(final Object[] msgs, final long timeStamp) {
        brightCyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightCyan(final Object... msgs) {
        brightCyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightWhite(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightWhite(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, message);
    }

    public static void brightWhite(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightWhite(final int level, final Object message, final long timeStamp) {
        brightWhite(level, message, timeStamp, true);
    }

    public static void brightWhite(final int level, final String message, final long timeStamp) {
        brightWhite(level, message, timeStamp, true);
    }

    public static void brightWhite(final int level, final Exception message, final long timeStamp) {
        brightWhite(level, message, timeStamp, true);
    }

    public static void brightWhite(final int level, final Object message) {
        brightWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final int level, final String message) {
        brightWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final int level, final Exception message) {
        brightWhite(level, message, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final String message, final long timeStamp) {
        brightWhite(Level.INFO, message, timeStamp, true);
    }

    public static void brightWhite(final String message) {
        brightWhite(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final Object message, final long timeStamp) {
        brightWhite(Level.INFO, message, timeStamp, true);
    }

    public static void brightWhite(final Object message) {
        brightWhite(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final Exception exception, final long timeStamp) {
        brightWhite(Level.INFO, exception, timeStamp, true);
    }

    public static void brightWhite(final Exception exception) {
        brightWhite(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightWhite(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightWhite(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightWhite(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightWhite(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightWhite(final int level, final String msg1, String msg2, final long timeStamp) {
        brightWhite(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightWhite(final int level, final Object msg1, Object msg2) {
        brightWhite(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final int level, final String msg1, String msg2) {
        brightWhite(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final String msg1, String msg2, final long timeStamp) {
        brightWhite(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightWhite(final String msg1, String msg2) {
        brightWhite(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightWhite(final Object msg1, Object msg2, final long timeStamp) {
        brightWhite(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightWhite(final Object msg1, Object msg2) {
        brightWhite(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightWhite(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightWhite(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_white(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightWhite(final int level, final Object[] msgs, final long timeStamp) {
        brightWhite(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightWhite(final int level, final String[] msgs, final long timeStamp) {
        brightWhite(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightWhite(final int level, final Object... msgs) {
        brightWhite(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightWhite(final int level, final String... msgs) {
        brightWhite(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightWhite(final String[] msgs, final long timeStamp) {
        brightWhite(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightWhite(final String... msgs) {
        brightWhite(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightWhite(final Object[] msgs, final long timeStamp) {
        brightWhite(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightWhite(final Object... msgs) {
        brightWhite(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightGray(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightGray(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, message);
    }

    public static void brightGray(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightGray(final int level, final Object message, final long timeStamp) {
        brightGray(level, message, timeStamp, true);
    }

    public static void brightGray(final int level, final String message, final long timeStamp) {
        brightGray(level, message, timeStamp, true);
    }

    public static void brightGray(final int level, final Exception message, final long timeStamp) {
        brightGray(level, message, timeStamp, true);
    }

    public static void brightGray(final int level, final Object message) {
        brightGray(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGray(final int level, final String message) {
        brightGray(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGray(final int level, final Exception message) {
        brightGray(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGray(final String message, final long timeStamp) {
        brightGray(Level.INFO, message, timeStamp, true);
    }

    public static void brightGray(final String message) {
        brightGray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGray(final Object message, final long timeStamp) {
        brightGray(Level.INFO, message, timeStamp, true);
    }

    public static void brightGray(final Object message) {
        brightGray(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGray(final Exception exception, final long timeStamp) {
        brightGray(Level.INFO, exception, timeStamp, true);
    }

    public static void brightGray(final Exception exception) {
        brightGray(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightGray(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGray(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGray(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightGray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGray(final int level, final String msg1, String msg2, final long timeStamp) {
        brightGray(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGray(final int level, final Object msg1, Object msg2) {
        brightGray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGray(final int level, final String msg1, String msg2) {
        brightGray(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGray(final String msg1, String msg2, final long timeStamp) {
        brightGray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGray(final String msg1, String msg2) {
        brightGray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGray(final Object msg1, Object msg2, final long timeStamp) {
        brightGray(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGray(final Object msg1, Object msg2) {
        brightGray(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightGray(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGray(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_gray(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGray(final int level, final Object[] msgs, final long timeStamp) {
        brightGray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGray(final int level, final String[] msgs, final long timeStamp) {
        brightGray(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGray(final int level, final Object... msgs) {
        brightGray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGray(final int level, final String... msgs) {
        brightGray(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGray(final String[] msgs, final long timeStamp) {
        brightGray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGray(final String... msgs) {
        brightGray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGray(final Object[] msgs, final long timeStamp) {
        brightGray(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGray(final Object... msgs) {
        brightGray(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void brightGrey(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void brightGrey(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, message);
    }

    public static void brightGrey(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void brightGrey(final int level, final Object message, final long timeStamp) {
        brightGrey(level, message, timeStamp, true);
    }

    public static void brightGrey(final int level, final String message, final long timeStamp) {
        brightGrey(level, message, timeStamp, true);
    }

    public static void brightGrey(final int level, final Exception message, final long timeStamp) {
        brightGrey(level, message, timeStamp, true);
    }

    public static void brightGrey(final int level, final Object message) {
        brightGrey(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final int level, final String message) {
        brightGrey(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final int level, final Exception message) {
        brightGrey(level, message, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final String message, final long timeStamp) {
        brightGrey(Level.INFO, message, timeStamp, true);
    }

    public static void brightGrey(final String message) {
        brightGrey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final Object message, final long timeStamp) {
        brightGrey(Level.INFO, message, timeStamp, true);
    }

    public static void brightGrey(final Object message) {
        brightGrey(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final Exception exception, final long timeStamp) {
        brightGrey(Level.INFO, exception, timeStamp, true);
    }

    public static void brightGrey(final Exception exception) {
        brightGrey(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void brightGrey(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGrey(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void brightGrey(final int level, final Object msg1, Object msg2, final long timeStamp) {
        brightGrey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGrey(final int level, final String msg1, String msg2, final long timeStamp) {
        brightGrey(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGrey(final int level, final Object msg1, Object msg2) {
        brightGrey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final int level, final String msg1, String msg2) {
        brightGrey(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final String msg1, String msg2, final long timeStamp) {
        brightGrey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGrey(final String msg1, String msg2) {
        brightGrey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void brightGrey(final Object msg1, Object msg2, final long timeStamp) {
        brightGrey(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void brightGrey(final Object msg1, Object msg2) {
        brightGrey(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void brightGrey(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGrey(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_grey(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void brightGrey(final int level, final Object[] msgs, final long timeStamp) {
        brightGrey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGrey(final int level, final String[] msgs, final long timeStamp) {
        brightGrey(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGrey(final int level, final Object... msgs) {
        brightGrey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGrey(final int level, final String... msgs) {
        brightGrey(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGrey(final String[] msgs, final long timeStamp) {
        brightGrey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGrey(final String... msgs) {
        brightGrey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void brightGrey(final Object[] msgs, final long timeStamp) {
        brightGrey(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void brightGrey(final Object... msgs) {
        brightGrey(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_black(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_black(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, message);
    }

    public static void background_black(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_black(final int level, final Object message, final long timeStamp) {
        background_black(level, message, timeStamp, true);
    }

    public static void background_black(final int level, final String message, final long timeStamp) {
        background_black(level, message, timeStamp, true);
    }

    public static void background_black(final int level, final Exception message, final long timeStamp) {
        background_black(level, message, timeStamp, true);
    }

    public static void background_black(final int level, final Object message) {
        background_black(level, message, System.currentTimeMillis(), true);
    }

    public static void background_black(final int level, final String message) {
        background_black(level, message, System.currentTimeMillis(), true);
    }

    public static void background_black(final int level, final Exception message) {
        background_black(level, message, System.currentTimeMillis(), true);
    }

    public static void background_black(final String message, final long timeStamp) {
        background_black(Level.INFO, message, timeStamp, true);
    }

    public static void background_black(final String message) {
        background_black(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_black(final Object message, final long timeStamp) {
        background_black(Level.INFO, message, timeStamp, true);
    }

    public static void background_black(final Object message) {
        background_black(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_black(final Exception exception, final long timeStamp) {
        background_black(Level.INFO, exception, timeStamp, true);
    }

    public static void background_black(final Exception exception) {
        background_black(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_black(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_black(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_black(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_black(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_black(final int level, final String msg1, String msg2, final long timeStamp) {
        background_black(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_black(final int level, final Object msg1, Object msg2) {
        background_black(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_black(final int level, final String msg1, String msg2) {
        background_black(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_black(final String msg1, String msg2, final long timeStamp) {
        background_black(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_black(final String msg1, String msg2) {
        background_black(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_black(final Object msg1, Object msg2, final long timeStamp) {
        background_black(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_black(final Object msg1, Object msg2) {
        background_black(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_black(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_black(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlack(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_black(final int level, final Object[] msgs, final long timeStamp) {
        background_black(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_black(final int level, final String[] msgs, final long timeStamp) {
        background_black(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_black(final int level, final Object... msgs) {
        background_black(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_black(final int level, final String... msgs) {
        background_black(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_black(final String[] msgs, final long timeStamp) {
        background_black(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_black(final String... msgs) {
        background_black(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_black(final Object[] msgs, final long timeStamp) {
        background_black(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_black(final Object... msgs) {
        background_black(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_red(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_red(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, message);
    }

    public static void background_red(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_red(final int level, final Object message, final long timeStamp) {
        background_red(level, message, timeStamp, true);
    }

    public static void background_red(final int level, final String message, final long timeStamp) {
        background_red(level, message, timeStamp, true);
    }

    public static void background_red(final int level, final Exception message, final long timeStamp) {
        background_red(level, message, timeStamp, true);
    }

    public static void background_red(final int level, final Object message) {
        background_red(level, message, System.currentTimeMillis(), true);
    }

    public static void background_red(final int level, final String message) {
        background_red(level, message, System.currentTimeMillis(), true);
    }

    public static void background_red(final int level, final Exception message) {
        background_red(level, message, System.currentTimeMillis(), true);
    }

    public static void background_red(final String message, final long timeStamp) {
        background_red(Level.INFO, message, timeStamp, true);
    }

    public static void background_red(final String message) {
        background_red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_red(final Object message, final long timeStamp) {
        background_red(Level.INFO, message, timeStamp, true);
    }

    public static void background_red(final Object message) {
        background_red(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_red(final Exception exception, final long timeStamp) {
        background_red(Level.INFO, exception, timeStamp, true);
    }

    public static void background_red(final Exception exception) {
        background_red(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_red(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_red(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_red(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_red(final int level, final String msg1, String msg2, final long timeStamp) {
        background_red(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_red(final int level, final Object msg1, Object msg2) {
        background_red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_red(final int level, final String msg1, String msg2) {
        background_red(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_red(final String msg1, String msg2, final long timeStamp) {
        background_red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_red(final String msg1, String msg2) {
        background_red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_red(final Object msg1, Object msg2, final long timeStamp) {
        background_red(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_red(final Object msg1, Object msg2) {
        background_red(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_red(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_red(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgRed(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_red(final int level, final Object[] msgs, final long timeStamp) {
        background_red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_red(final int level, final String[] msgs, final long timeStamp) {
        background_red(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_red(final int level, final Object... msgs) {
        background_red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_red(final int level, final String... msgs) {
        background_red(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_red(final String[] msgs, final long timeStamp) {
        background_red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_red(final String... msgs) {
        background_red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_red(final Object[] msgs, final long timeStamp) {
        background_red(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_red(final Object... msgs) {
        background_red(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_green(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_green(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, message);
    }

    public static void background_green(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_green(final int level, final Object message, final long timeStamp) {
        background_green(level, message, timeStamp, true);
    }

    public static void background_green(final int level, final String message, final long timeStamp) {
        background_green(level, message, timeStamp, true);
    }

    public static void background_green(final int level, final Exception message, final long timeStamp) {
        background_green(level, message, timeStamp, true);
    }

    public static void background_green(final int level, final Object message) {
        background_green(level, message, System.currentTimeMillis(), true);
    }

    public static void background_green(final int level, final String message) {
        background_green(level, message, System.currentTimeMillis(), true);
    }

    public static void background_green(final int level, final Exception message) {
        background_green(level, message, System.currentTimeMillis(), true);
    }

    public static void background_green(final String message, final long timeStamp) {
        background_green(Level.INFO, message, timeStamp, true);
    }

    public static void background_green(final String message) {
        background_green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_green(final Object message, final long timeStamp) {
        background_green(Level.INFO, message, timeStamp, true);
    }

    public static void background_green(final Object message) {
        background_green(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_green(final Exception exception, final long timeStamp) {
        background_green(Level.INFO, exception, timeStamp, true);
    }

    public static void background_green(final Exception exception) {
        background_green(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_green(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_green(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_green(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_green(final int level, final String msg1, String msg2, final long timeStamp) {
        background_green(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_green(final int level, final Object msg1, Object msg2) {
        background_green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_green(final int level, final String msg1, String msg2) {
        background_green(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_green(final String msg1, String msg2, final long timeStamp) {
        background_green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_green(final String msg1, String msg2) {
        background_green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_green(final Object msg1, Object msg2, final long timeStamp) {
        background_green(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_green(final Object msg1, Object msg2) {
        background_green(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_green(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_green(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgGreen(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_green(final int level, final Object[] msgs, final long timeStamp) {
        background_green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_green(final int level, final String[] msgs, final long timeStamp) {
        background_green(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_green(final int level, final Object... msgs) {
        background_green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_green(final int level, final String... msgs) {
        background_green(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_green(final String[] msgs, final long timeStamp) {
        background_green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_green(final String... msgs) {
        background_green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_green(final Object[] msgs, final long timeStamp) {
        background_green(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_green(final Object... msgs) {
        background_green(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_yellow(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_yellow(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, message);
    }

    public static void background_yellow(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_yellow(final int level, final Object message, final long timeStamp) {
        background_yellow(level, message, timeStamp, true);
    }

    public static void background_yellow(final int level, final String message, final long timeStamp) {
        background_yellow(level, message, timeStamp, true);
    }

    public static void background_yellow(final int level, final Exception message, final long timeStamp) {
        background_yellow(level, message, timeStamp, true);
    }

    public static void background_yellow(final int level, final Object message) {
        background_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final int level, final String message) {
        background_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final int level, final Exception message) {
        background_yellow(level, message, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final String message, final long timeStamp) {
        background_yellow(Level.INFO, message, timeStamp, true);
    }

    public static void background_yellow(final String message) {
        background_yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final Object message, final long timeStamp) {
        background_yellow(Level.INFO, message, timeStamp, true);
    }

    public static void background_yellow(final Object message) {
        background_yellow(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final Exception exception, final long timeStamp) {
        background_yellow(Level.INFO, exception, timeStamp, true);
    }

    public static void background_yellow(final Exception exception) {
        background_yellow(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_yellow(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_yellow(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_yellow(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_yellow(final int level, final String msg1, String msg2, final long timeStamp) {
        background_yellow(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_yellow(final int level, final Object msg1, Object msg2) {
        background_yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final int level, final String msg1, String msg2) {
        background_yellow(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final String msg1, String msg2, final long timeStamp) {
        background_yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_yellow(final String msg1, String msg2) {
        background_yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_yellow(final Object msg1, Object msg2, final long timeStamp) {
        background_yellow(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_yellow(final Object msg1, Object msg2) {
        background_yellow(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_yellow(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_yellow(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgYellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_yellow(final int level, final Object[] msgs, final long timeStamp) {
        background_yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_yellow(final int level, final String[] msgs, final long timeStamp) {
        background_yellow(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_yellow(final int level, final Object... msgs) {
        background_yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_yellow(final int level, final String... msgs) {
        background_yellow(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_yellow(final String[] msgs, final long timeStamp) {
        background_yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_yellow(final String... msgs) {
        background_yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_yellow(final Object[] msgs, final long timeStamp) {
        background_yellow(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_yellow(final Object... msgs) {
        background_yellow(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_blue(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_blue(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, message);
    }

    public static void background_blue(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_blue(final int level, final Object message, final long timeStamp) {
        background_blue(level, message, timeStamp, true);
    }

    public static void background_blue(final int level, final String message, final long timeStamp) {
        background_blue(level, message, timeStamp, true);
    }

    public static void background_blue(final int level, final Exception message, final long timeStamp) {
        background_blue(level, message, timeStamp, true);
    }

    public static void background_blue(final int level, final Object message) {
        background_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void background_blue(final int level, final String message) {
        background_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void background_blue(final int level, final Exception message) {
        background_blue(level, message, System.currentTimeMillis(), true);
    }

    public static void background_blue(final String message, final long timeStamp) {
        background_blue(Level.INFO, message, timeStamp, true);
    }

    public static void background_blue(final String message) {
        background_blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_blue(final Object message, final long timeStamp) {
        background_blue(Level.INFO, message, timeStamp, true);
    }

    public static void background_blue(final Object message) {
        background_blue(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_blue(final Exception exception, final long timeStamp) {
        background_blue(Level.INFO, exception, timeStamp, true);
    }

    public static void background_blue(final Exception exception) {
        background_blue(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_blue(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_blue(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_blue(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_blue(final int level, final String msg1, String msg2, final long timeStamp) {
        background_blue(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_blue(final int level, final Object msg1, Object msg2) {
        background_blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_blue(final int level, final String msg1, String msg2) {
        background_blue(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_blue(final String msg1, String msg2, final long timeStamp) {
        background_blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_blue(final String msg1, String msg2) {
        background_blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_blue(final Object msg1, Object msg2, final long timeStamp) {
        background_blue(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_blue(final Object msg1, Object msg2) {
        background_blue(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_blue(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_blue(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgBlue(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_blue(final int level, final Object[] msgs, final long timeStamp) {
        background_blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_blue(final int level, final String[] msgs, final long timeStamp) {
        background_blue(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_blue(final int level, final Object... msgs) {
        background_blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_blue(final int level, final String... msgs) {
        background_blue(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_blue(final String[] msgs, final long timeStamp) {
        background_blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_blue(final String... msgs) {
        background_blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_blue(final Object[] msgs, final long timeStamp) {
        background_blue(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_blue(final Object... msgs) {
        background_blue(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_magenta(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_magenta(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, message);
    }

    public static void background_magenta(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_magenta(final int level, final Object message, final long timeStamp) {
        background_magenta(level, message, timeStamp, true);
    }

    public static void background_magenta(final int level, final String message, final long timeStamp) {
        background_magenta(level, message, timeStamp, true);
    }

    public static void background_magenta(final int level, final Exception message, final long timeStamp) {
        background_magenta(level, message, timeStamp, true);
    }

    public static void background_magenta(final int level, final Object message) {
        background_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final int level, final String message) {
        background_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final int level, final Exception message) {
        background_magenta(level, message, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final String message, final long timeStamp) {
        background_magenta(Level.INFO, message, timeStamp, true);
    }

    public static void background_magenta(final String message) {
        background_magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final Object message, final long timeStamp) {
        background_magenta(Level.INFO, message, timeStamp, true);
    }

    public static void background_magenta(final Object message) {
        background_magenta(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final Exception exception, final long timeStamp) {
        background_magenta(Level.INFO, exception, timeStamp, true);
    }

    public static void background_magenta(final Exception exception) {
        background_magenta(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_magenta(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_magenta(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_magenta(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_magenta(final int level, final String msg1, String msg2, final long timeStamp) {
        background_magenta(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_magenta(final int level, final Object msg1, Object msg2) {
        background_magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final int level, final String msg1, String msg2) {
        background_magenta(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final String msg1, String msg2, final long timeStamp) {
        background_magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_magenta(final String msg1, String msg2) {
        background_magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_magenta(final Object msg1, Object msg2, final long timeStamp) {
        background_magenta(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_magenta(final Object msg1, Object msg2) {
        background_magenta(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_magenta(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_magenta(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgMagenta(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_magenta(final int level, final Object[] msgs, final long timeStamp) {
        background_magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_magenta(final int level, final String[] msgs, final long timeStamp) {
        background_magenta(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_magenta(final int level, final Object... msgs) {
        background_magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_magenta(final int level, final String... msgs) {
        background_magenta(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_magenta(final String[] msgs, final long timeStamp) {
        background_magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_magenta(final String... msgs) {
        background_magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_magenta(final Object[] msgs, final long timeStamp) {
        background_magenta(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_magenta(final Object... msgs) {
        background_magenta(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_cyan(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_cyan(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, message);
    }

    public static void background_cyan(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_cyan(final int level, final Object message, final long timeStamp) {
        background_cyan(level, message, timeStamp, true);
    }

    public static void background_cyan(final int level, final String message, final long timeStamp) {
        background_cyan(level, message, timeStamp, true);
    }

    public static void background_cyan(final int level, final Exception message, final long timeStamp) {
        background_cyan(level, message, timeStamp, true);
    }

    public static void background_cyan(final int level, final Object message) {
        background_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final int level, final String message) {
        background_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final int level, final Exception message) {
        background_cyan(level, message, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final String message, final long timeStamp) {
        background_cyan(Level.INFO, message, timeStamp, true);
    }

    public static void background_cyan(final String message) {
        background_cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final Object message, final long timeStamp) {
        background_cyan(Level.INFO, message, timeStamp, true);
    }

    public static void background_cyan(final Object message) {
        background_cyan(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final Exception exception, final long timeStamp) {
        background_cyan(Level.INFO, exception, timeStamp, true);
    }

    public static void background_cyan(final Exception exception) {
        background_cyan(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_cyan(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_cyan(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_cyan(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_cyan(final int level, final String msg1, String msg2, final long timeStamp) {
        background_cyan(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_cyan(final int level, final Object msg1, Object msg2) {
        background_cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final int level, final String msg1, String msg2) {
        background_cyan(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final String msg1, String msg2, final long timeStamp) {
        background_cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_cyan(final String msg1, String msg2) {
        background_cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_cyan(final Object msg1, Object msg2, final long timeStamp) {
        background_cyan(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_cyan(final Object msg1, Object msg2) {
        background_cyan(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_cyan(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_cyan(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgCyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_cyan(final int level, final Object[] msgs, final long timeStamp) {
        background_cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_cyan(final int level, final String[] msgs, final long timeStamp) {
        background_cyan(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_cyan(final int level, final Object... msgs) {
        background_cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_cyan(final int level, final String... msgs) {
        background_cyan(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_cyan(final String[] msgs, final long timeStamp) {
        background_cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_cyan(final String... msgs) {
        background_cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_cyan(final Object[] msgs, final long timeStamp) {
        background_cyan(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_cyan(final Object... msgs) {
        background_cyan(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void background_white(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void background_white(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, message);
    }

    public static void background_white(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void background_white(final int level, final Object message, final long timeStamp) {
        background_white(level, message, timeStamp, true);
    }

    public static void background_white(final int level, final String message, final long timeStamp) {
        background_white(level, message, timeStamp, true);
    }

    public static void background_white(final int level, final Exception message, final long timeStamp) {
        background_white(level, message, timeStamp, true);
    }

    public static void background_white(final int level, final Object message) {
        background_white(level, message, System.currentTimeMillis(), true);
    }

    public static void background_white(final int level, final String message) {
        background_white(level, message, System.currentTimeMillis(), true);
    }

    public static void background_white(final int level, final Exception message) {
        background_white(level, message, System.currentTimeMillis(), true);
    }

    public static void background_white(final String message, final long timeStamp) {
        background_white(Level.INFO, message, timeStamp, true);
    }

    public static void background_white(final String message) {
        background_white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_white(final Object message, final long timeStamp) {
        background_white(Level.INFO, message, timeStamp, true);
    }

    public static void background_white(final Object message) {
        background_white(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void background_white(final Exception exception, final long timeStamp) {
        background_white(Level.INFO, exception, timeStamp, true);
    }

    public static void background_white(final Exception exception) {
        background_white(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void background_white(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_white(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void background_white(final int level, final Object msg1, Object msg2, final long timeStamp) {
        background_white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_white(final int level, final String msg1, String msg2, final long timeStamp) {
        background_white(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_white(final int level, final Object msg1, Object msg2) {
        background_white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_white(final int level, final String msg1, String msg2) {
        background_white(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_white(final String msg1, String msg2, final long timeStamp) {
        background_white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_white(final String msg1, String msg2) {
        background_white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void background_white(final Object msg1, Object msg2, final long timeStamp) {
        background_white(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void background_white(final Object msg1, Object msg2) {
        background_white(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void background_white(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_white(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bgWhite(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void background_white(final int level, final Object[] msgs, final long timeStamp) {
        background_white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_white(final int level, final String[] msgs, final long timeStamp) {
        background_white(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_white(final int level, final Object... msgs) {
        background_white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_white(final int level, final String... msgs) {
        background_white(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_white(final String[] msgs, final long timeStamp) {
        background_white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_white(final String... msgs) {
        background_white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void background_white(final Object[] msgs, final long timeStamp) {
        background_white(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void background_white(final Object... msgs) {
        background_white(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void log(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void log(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, message);
    }

    public static void log(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void log(final int level, final Object message, final long timeStamp) {
        log(level, message, timeStamp, true);
    }

    public static void log(final int level, final String message, final long timeStamp) {
        log(level, message, timeStamp, true);
    }

    public static void log(final int level, final Exception message, final long timeStamp) {
        log(level, message, timeStamp, true);
    }

    public static void log(final int level, final Object message) {
        log(level, message, System.currentTimeMillis(), true);
    }

    public static void log(final int level, final String message) {
        log(level, message, System.currentTimeMillis(), true);
    }

    public static void log(final int level, final Exception message) {
        log(level, message, System.currentTimeMillis(), true);
    }

    public static void log(final String message, final long timeStamp) {
        log(Level.INFO, message, timeStamp, true);
    }

    public static void log(final String message) {
        log(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void log(final Object message, final long timeStamp) {
        log(Level.INFO, message, timeStamp, true);
    }

    public static void log(final Object message) {
        log(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void log(final Exception exception, final long timeStamp) {
        log(Level.INFO, exception, timeStamp, true);
    }

    public static void log(final Exception exception) {
        log(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void log(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void log(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void log(final int level, final Object msg1, Object msg2, final long timeStamp) {
        log(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void log(final int level, final String msg1, String msg2, final long timeStamp) {
        log(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void log(final int level, final Object msg1, Object msg2) {
        log(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void log(final int level, final String msg1, String msg2) {
        log(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void log(final String msg1, String msg2, final long timeStamp) {
        log(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void log(final String msg1, String msg2) {
        log(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void log(final Object msg1, Object msg2, final long timeStamp) {
        log(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void log(final Object msg1, Object msg2) {
        log(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void log(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void log(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void log(final int level, final Object[] msgs, final long timeStamp) {
        log(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void log(final int level, final String[] msgs, final long timeStamp) {
        log(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void log(final int level, final Object... msgs) {
        log(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void log(final int level, final String... msgs) {
        log(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void log(final String[] msgs, final long timeStamp) {
        log(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void log(final String... msgs) {
        log(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void log(final Object[] msgs, final long timeStamp) {
        log(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void log(final Object... msgs) {
        log(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void error(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void error(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message);
    }

    public static void error(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void error(final int level, final Object message, final long timeStamp) {
        error(level, message, timeStamp, true);
    }

    public static void error(final int level, final String message, final long timeStamp) {
        error(level, message, timeStamp, true);
    }

    public static void error(final int level, final Exception message, final long timeStamp) {
        error(level, message, timeStamp, true);
    }

    public static void error(final int level, final Object message) {
        error(level, message, System.currentTimeMillis(), true);
    }

    public static void error(final int level, final String message) {
        error(level, message, System.currentTimeMillis(), true);
    }

    public static void error(final int level, final Exception message) {
        error(level, message, System.currentTimeMillis(), true);
    }

    public static void error(final String message, final long timeStamp) {
        error(Level.INFO, message, timeStamp, true);
    }

    public static void error(final String message) {
        error(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void error(final Object message, final long timeStamp) {
        error(Level.INFO, message, timeStamp, true);
    }

    public static void error(final Object message) {
        error(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void error(final Exception exception, final long timeStamp) {
        error(Level.INFO, exception, timeStamp, true);
    }

    public static void error(final Exception exception) {
        error(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void error(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void error(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void error(final int level, final Object msg1, Object msg2, final long timeStamp) {
        error(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void error(final int level, final String msg1, String msg2, final long timeStamp) {
        error(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void error(final int level, final Object msg1, Object msg2) {
        error(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void error(final int level, final String msg1, String msg2) {
        error(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void error(final String msg1, String msg2, final long timeStamp) {
        error(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void error(final String msg1, String msg2) {
        error(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void error(final Object msg1, Object msg2, final long timeStamp) {
        error(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void error(final Object msg1, Object msg2) {
        error(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void error(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void error(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void error(final int level, final Object[] msgs, final long timeStamp) {
        error(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void error(final int level, final String[] msgs, final long timeStamp) {
        error(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void error(final int level, final Object... msgs) {
        error(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void error(final int level, final String... msgs) {
        error(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void error(final String[] msgs, final long timeStamp) {
        error(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void error(final String... msgs) {
        error(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void error(final Object[] msgs, final long timeStamp) {
        error(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void error(final Object... msgs) {
        error(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void severe(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void severe(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message);
    }

    public static void severe(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void severe(final int level, final Object message, final long timeStamp) {
        severe(level, message, timeStamp, true);
    }

    public static void severe(final int level, final String message, final long timeStamp) {
        severe(level, message, timeStamp, true);
    }

    public static void severe(final int level, final Exception message, final long timeStamp) {
        severe(level, message, timeStamp, true);
    }

    public static void severe(final int level, final Object message) {
        severe(level, message, System.currentTimeMillis(), true);
    }

    public static void severe(final int level, final String message) {
        severe(level, message, System.currentTimeMillis(), true);
    }

    public static void severe(final int level, final Exception message) {
        severe(level, message, System.currentTimeMillis(), true);
    }

    public static void severe(final String message, final long timeStamp) {
        severe(Level.INFO, message, timeStamp, true);
    }

    public static void severe(final String message) {
        severe(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void severe(final Object message, final long timeStamp) {
        severe(Level.INFO, message, timeStamp, true);
    }

    public static void severe(final Object message) {
        severe(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void severe(final Exception exception, final long timeStamp) {
        severe(Level.INFO, exception, timeStamp, true);
    }

    public static void severe(final Exception exception) {
        severe(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void severe(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void severe(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void severe(final int level, final Object msg1, Object msg2, final long timeStamp) {
        severe(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void severe(final int level, final String msg1, String msg2, final long timeStamp) {
        severe(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void severe(final int level, final Object msg1, Object msg2) {
        severe(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void severe(final int level, final String msg1, String msg2) {
        severe(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void severe(final String msg1, String msg2, final long timeStamp) {
        severe(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void severe(final String msg1, String msg2) {
        severe(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void severe(final Object msg1, Object msg2, final long timeStamp) {
        severe(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void severe(final Object msg1, Object msg2) {
        severe(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void severe(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void severe(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void severe(final int level, final Object[] msgs, final long timeStamp) {
        severe(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void severe(final int level, final String[] msgs, final long timeStamp) {
        severe(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void severe(final int level, final Object... msgs) {
        severe(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void severe(final int level, final String... msgs) {
        severe(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void severe(final String[] msgs, final long timeStamp) {
        severe(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void severe(final String... msgs) {
        severe(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void severe(final Object[] msgs, final long timeStamp) {
        severe(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void severe(final Object... msgs) {
        severe(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void fatal(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void fatal(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message);
    }

    public static void fatal(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void fatal(final int level, final Object message, final long timeStamp) {
        fatal(level, message, timeStamp, true);
    }

    public static void fatal(final int level, final String message, final long timeStamp) {
        fatal(level, message, timeStamp, true);
    }

    public static void fatal(final int level, final Exception message, final long timeStamp) {
        fatal(level, message, timeStamp, true);
    }

    public static void fatal(final int level, final Object message) {
        fatal(level, message, System.currentTimeMillis(), true);
    }

    public static void fatal(final int level, final String message) {
        fatal(level, message, System.currentTimeMillis(), true);
    }

    public static void fatal(final int level, final Exception message) {
        fatal(level, message, System.currentTimeMillis(), true);
    }

    public static void fatal(final String message, final long timeStamp) {
        fatal(Level.INFO, message, timeStamp, true);
    }

    public static void fatal(final String message) {
        fatal(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void fatal(final Object message, final long timeStamp) {
        fatal(Level.INFO, message, timeStamp, true);
    }

    public static void fatal(final Object message) {
        fatal(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void fatal(final Exception exception, final long timeStamp) {
        fatal(Level.INFO, exception, timeStamp, true);
    }

    public static void fatal(final Exception exception) {
        fatal(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void fatal(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void fatal(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void fatal(final int level, final Object msg1, Object msg2, final long timeStamp) {
        fatal(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void fatal(final int level, final String msg1, String msg2, final long timeStamp) {
        fatal(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void fatal(final int level, final Object msg1, Object msg2) {
        fatal(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void fatal(final int level, final String msg1, String msg2) {
        fatal(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void fatal(final String msg1, String msg2, final long timeStamp) {
        fatal(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void fatal(final String msg1, String msg2) {
        fatal(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void fatal(final Object msg1, Object msg2, final long timeStamp) {
        fatal(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void fatal(final Object msg1, Object msg2) {
        fatal(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void fatal(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void fatal(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_red(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void fatal(final int level, final Object[] msgs, final long timeStamp) {
        fatal(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void fatal(final int level, final String[] msgs, final long timeStamp) {
        fatal(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void fatal(final int level, final Object... msgs) {
        fatal(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void fatal(final int level, final String... msgs) {
        fatal(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void fatal(final String[] msgs, final long timeStamp) {
        fatal(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void fatal(final String... msgs) {
        fatal(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void fatal(final Object[] msgs, final long timeStamp) {
        fatal(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void fatal(final Object... msgs) {
        fatal(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void warn(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void warn(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message);
    }

    public static void warn(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void warn(final int level, final Object message, final long timeStamp) {
        warn(level, message, timeStamp, true);
    }

    public static void warn(final int level, final String message, final long timeStamp) {
        warn(level, message, timeStamp, true);
    }

    public static void warn(final int level, final Exception message, final long timeStamp) {
        warn(level, message, timeStamp, true);
    }

    public static void warn(final int level, final Object message) {
        warn(level, message, System.currentTimeMillis(), true);
    }

    public static void warn(final int level, final String message) {
        warn(level, message, System.currentTimeMillis(), true);
    }

    public static void warn(final int level, final Exception message) {
        warn(level, message, System.currentTimeMillis(), true);
    }

    public static void warn(final String message, final long timeStamp) {
        warn(Level.INFO, message, timeStamp, true);
    }

    public static void warn(final String message) {
        warn(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void warn(final Object message, final long timeStamp) {
        warn(Level.INFO, message, timeStamp, true);
    }

    public static void warn(final Object message) {
        warn(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void warn(final Exception exception, final long timeStamp) {
        warn(Level.INFO, exception, timeStamp, true);
    }

    public static void warn(final Exception exception) {
        warn(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void warn(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void warn(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void warn(final int level, final Object msg1, Object msg2, final long timeStamp) {
        warn(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void warn(final int level, final String msg1, String msg2, final long timeStamp) {
        warn(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void warn(final int level, final Object msg1, Object msg2) {
        warn(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void warn(final int level, final String msg1, String msg2) {
        warn(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void warn(final String msg1, String msg2, final long timeStamp) {
        warn(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void warn(final String msg1, String msg2) {
        warn(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void warn(final Object msg1, Object msg2, final long timeStamp) {
        warn(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void warn(final Object msg1, Object msg2) {
        warn(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void warn(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void warn(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void warn(final int level, final Object[] msgs, final long timeStamp) {
        warn(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void warn(final int level, final String[] msgs, final long timeStamp) {
        warn(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void warn(final int level, final Object... msgs) {
        warn(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void warn(final int level, final String... msgs) {
        warn(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void warn(final String[] msgs, final long timeStamp) {
        warn(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void warn(final String... msgs) {
        warn(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void warn(final Object[] msgs, final long timeStamp) {
        warn(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void warn(final Object... msgs) {
        warn(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void info(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void info(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message);
    }

    public static void info(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void info(final int level, final Object message, final long timeStamp) {
        info(level, message, timeStamp, true);
    }

    public static void info(final int level, final String message, final long timeStamp) {
        info(level, message, timeStamp, true);
    }

    public static void info(final int level, final Exception message, final long timeStamp) {
        info(level, message, timeStamp, true);
    }

    public static void info(final int level, final Object message) {
        info(level, message, System.currentTimeMillis(), true);
    }

    public static void info(final int level, final String message) {
        info(level, message, System.currentTimeMillis(), true);
    }

    public static void info(final int level, final Exception message) {
        info(level, message, System.currentTimeMillis(), true);
    }

    public static void info(final String message, final long timeStamp) {
        info(Level.INFO, message, timeStamp, true);
    }

    public static void info(final String message) {
        info(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void info(final Object message, final long timeStamp) {
        info(Level.INFO, message, timeStamp, true);
    }

    public static void info(final Object message) {
        info(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void info(final Exception exception, final long timeStamp) {
        info(Level.INFO, exception, timeStamp, true);
    }

    public static void info(final Exception exception) {
        info(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void info(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void info(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void info(final int level, final Object msg1, Object msg2, final long timeStamp) {
        info(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void info(final int level, final String msg1, String msg2, final long timeStamp) {
        info(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void info(final int level, final Object msg1, Object msg2) {
        info(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void info(final int level, final String msg1, String msg2) {
        info(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void info(final String msg1, String msg2, final long timeStamp) {
        info(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void info(final String msg1, String msg2) {
        info(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void info(final Object msg1, Object msg2, final long timeStamp) {
        info(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void info(final Object msg1, Object msg2) {
        info(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void info(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void info(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void info(final int level, final Object[] msgs, final long timeStamp) {
        info(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void info(final int level, final String[] msgs, final long timeStamp) {
        info(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void info(final int level, final Object... msgs) {
        info(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void info(final int level, final String... msgs) {
        info(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void info(final String[] msgs, final long timeStamp) {
        info(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void info(final String... msgs) {
        info(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void info(final Object[] msgs, final long timeStamp) {
        info(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void info(final Object... msgs) {
        info(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    // 1-arg methods
    public static void debug(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void debug(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, message);
    }

    public static void debug(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void debug(final int level, final Object message, final long timeStamp) {
        debug(level, message, timeStamp, true);
    }

    public static void debug(final int level, final String message, final long timeStamp) {
        debug(level, message, timeStamp, true);
    }

    public static void debug(final int level, final Exception message, final long timeStamp) {
        debug(level, message, timeStamp, true);
    }

    public static void debug(final int level, final Object message) {
        debug(level, message, System.currentTimeMillis(), true);
    }

    public static void debug(final int level, final String message) {
        debug(level, message, System.currentTimeMillis(), true);
    }

    public static void debug(final int level, final Exception message) {
        debug(level, message, System.currentTimeMillis(), true);
    }

    public static void debug(final String message, final long timeStamp) {
        debug(Level.INFO, message, timeStamp, true);
    }

    public static void debug(final String message) {
        debug(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void debug(final Object message, final long timeStamp) {
        debug(Level.INFO, message, timeStamp, true);
    }

    public static void debug(final Object message) {
        debug(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void debug(final Exception exception, final long timeStamp) {
        debug(Level.INFO, exception, timeStamp, true);
    }

    public static void debug(final Exception exception) {
        debug(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void debug(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void debug(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void debug(final int level, final Object msg1, Object msg2, final long timeStamp) {
        debug(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void debug(final int level, final String msg1, String msg2, final long timeStamp) {
        debug(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void debug(final int level, final Object msg1, Object msg2) {
        debug(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void debug(final int level, final String msg1, String msg2) {
        debug(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void debug(final String msg1, String msg2, final long timeStamp) {
        debug(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void debug(final String msg1, String msg2) {
        debug(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void debug(final Object msg1, Object msg2, final long timeStamp) {
        debug(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void debug(final Object msg1, Object msg2) {
        debug(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void debug(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void debug(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void debug(final int level, final Object[] msgs, final long timeStamp) {
        debug(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void debug(final int level, final String[] msgs, final long timeStamp) {
        debug(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void debug(final int level, final Object... msgs) {
        debug(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void debug(final int level, final String... msgs) {
        debug(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void debug(final String[] msgs, final long timeStamp) {
        debug(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void debug(final String... msgs) {
        debug(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void debug(final Object[] msgs, final long timeStamp) {
        debug(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void debug(final Object... msgs) {
        debug(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }


    /**
     * Utility class for dealing with cooooooolors
     *
     * @author Rafael / Chalk contributors (base is used in this)
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

        public static transient final String bright_red_open = "\u001b[31;1m";
        public static transient final String bright_red_close = "\u001b[0m";

        public static transient final String bright_green_open = "\u001b[32;1m";
        public static transient final String bright_green_close = "\u001b[0m";

        public static transient final String bright_yellow_open = "\u001b[33;1m";
        public static transient final String bright_yellow_close = "\u001b[0m";

        public static transient final String bright_blue_open = "\u001b[34;1m";
        public static transient final String bright_blue_close = "\u001b[0m";

        public static transient final String bright_magenta_open = "\u001b[35;1m";
        public static transient final String bright_magenta_close = "\u001b[0m";

        public static transient final String bright_cyan_open = "\u001b[36;1m";
        public static transient final String bright_cyan_close = "\u001b[0m";

        public static transient final String bright_white_open = "\u001b[37;1m";
        public static transient final String bright_white_close = "\u001b[0m";

        public static transient final String bright_gray_open = "\u001b[90;1m";
        public static transient final String bright_gray_close = "\u001b[0m";

        public static transient final String bright_grey_open = "\u001b[90;1m";
        public static transient final String bright_grey_close = "\u001b[0m";

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

        public static void p_bright_red(final String s) {
            HLogger.log(bright_red_open + s + bright_red_close);
        }

        public static void p_bright_green(final String s) {
            HLogger.log(bright_green_open + s + bright_green_close);
        }

        public static void p_bright_yellow(final String s) {
            HLogger.log(bright_yellow_open + s + bright_yellow_close);
        }

        public static void p_bright_blue(final String s) {
            HLogger.log(bright_blue_open + s + bright_blue_close);
        }

        public static void p_bright_magenta(final String s) {
            HLogger.log(bright_magenta_open + s + bright_magenta_close);
        }

        public static void p_bright_cyan(final String s) {
            HLogger.log(bright_cyan_open + s + bright_cyan_close);
        }

        public static void p_bright_white(final String s) {
            HLogger.log(bright_white_open + s + bright_white_close);
        }

        public static void p_bright_gray(final String s) {
            HLogger.log(bright_gray_open + s + bright_gray_close);
        }

        public static void p_bright_grey(final String s) {
            HLogger.log(bright_grey_open + s + bright_grey_close);
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
        public static String bright_red(final String s) {
            return bright_red_open + s + bright_red_close;
        }
        public static String bright_green(final String s) {
            return bright_green_open + s + bright_green_close;
        }
        public static String bright_yellow(final String s) {
            return bright_yellow_open + s + bright_yellow_close;
        }
        public static String bright_blue(final String s) {
            return bright_blue_open + s + bright_blue_close;
        }
        public static String bright_magenta(final String s) {
            return bright_magenta_open + s + bright_magenta_close;
        }
        public static String bright_cyan(final String s) {
            return bright_cyan_open + s + bright_cyan_close;
        }
        public static String bright_white(final String s) {
            return bright_white_open + s + bright_white_close;
        }
        public static String bright_gray(final String s) {
            return bright_gray_open + s + bright_gray_close;
        }
        public static String bright_grey(final String s) {
            return bright_grey_open + s + bright_grey_close;
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

    }
//! $CHALK_END

}

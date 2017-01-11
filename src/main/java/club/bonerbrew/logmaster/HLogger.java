package club.bonerbrew.logmaster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * It's a logger
 *
 * @author Eli / Rafael / Some fuckin discord bot author
 */
public final class HLogger {

    private static final float minutesInHour = 60f;
    private static final float secondsInMinute = 60f;
    private static final float milisecondsInSecond = 1000f;

    private HLogger() {
    }

    ///** formatter used for String.format */
    //private static final Formatter FORMATTER = new Formatter();

    private static final long initialTime = System.currentTimeMillis();

    /**
     * file to log to
     */
    private static final File logFile;

    /**
     * print to stdout?
     */
    private static final boolean stdOut = true;
    /**
     * print to stderr?
     */
    public static final boolean stdErr = true;

    /**
     * write to file?
     */
    private static final boolean outputToFile = true;

    /**
     * print DEBUG to stdout
     */
    private static final boolean outputFine = true;

    /**
     * fileWriter to output log data to if trailfile is true
     */
    private static PrintWriter fileWriter;

    static {
        if (outputToFile) {
            final String mainClassName = getMainClassName();
            logFile = new File("./logs/" + mainClassName + '-' + LocalDate.now().toString() + ".log");

            // make folder if not exists
            final File logDir = new File("./logs");
            if (!logDir.exists()) {
                logDir.mkdir();
            } else {
                if (!logDir.isDirectory()) {
                    logDir.delete();
                    logDir.mkdir();
                }
            }

            // create log file if not exists
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            // make writer
            try {
                fileWriter = new PrintWriter(new FileWriter(logFile,
                        // required since we've made the file already
                        true),
                        // saves a constant call to .flush()
                        true);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            fileWriter.println(mainClassName + " --- " + LocalDateTime.now());

            // make sure we flush even if it didn't do it automatically
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    fileWriter.flush();
                    fileWriter.close();
                }
            });
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

    private static String getMainClassName() {
        { // Oracle JVM only
            final String s = System.getProperty("sun.java.command");
            if (s != null) {
                return s;
            }
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

    /**
     * do not call this method directly
     * @return caller class name of whoever called the method that called this method
     */
    public static String getCallerCallerClassName() { 
        StackTraceElement[] stElements = new Throwable().getStackTrace();
        return stElements[3].getClassName();
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

    public static void log(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void log(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, message);
    }

    public static void log(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, message == null ? "null" : message.toString());
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

    private static void write(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + '\n';
        if (outputToFile && logToFile) {
            fileWriter.print(fString);
        }
        if (stdOut) {
            if (level >= Level.SEVERE) {
                System.err.print(fString);
            } else if (level != Level.DEBUG) {
                System.out.print(fString);
            } else if (outputFine) {
                System.out.print(fString);
            }
        }
    }
    

    public static void error(final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void error(final String message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, message);
    }

    public static void error(final Object message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void error(final String message, final long timeStamp) {
        error(message, timeStamp, true);
    }

    public static void error(final String message) {
        error(message, System.currentTimeMillis(), true);
    }

    public static void error(final Object message, final long timeStamp) {
        error(message, timeStamp, true);
    }

    public static void error(final Object message) {
        error(message, System.currentTimeMillis(), true);
    }

    public static void error(final Exception exception, final long timeStamp) {
        error(exception, timeStamp, true);
    }

    public static void error(final Exception exception) {
        error(exception, System.currentTimeMillis(), true);
    }

    public static void severe(final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void severe(final String message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, message);
    }

    public static void severe(final Object message, final long timeStamp, final boolean logToFile) {
        write_red(Level.SEVERE, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void severe(final String message, final long timeStamp) {
        severe(message, timeStamp, true);
    }

    public static void severe(final String message) {
        severe(message, System.currentTimeMillis(), true);
    }

    public static void severe(final Object message, final long timeStamp) {
        severe(message, timeStamp, true);
    }

    public static void severe(final Object message) {
        severe(message, System.currentTimeMillis(), true);
    }

    public static void severe(final Exception exception, final long timeStamp) {
        severe(exception, timeStamp, true);
    }

    public static void severe(final Exception exception) {
        severe(exception, System.currentTimeMillis(), true);
    }

    public static void fatal(final Exception message, final long timeStamp, final boolean logToFile) {
        write_red(Level.FATAL, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void fatal(final String message, final long timeStamp, final boolean logToFile) {
        write_red(Level.FATAL, timeStamp, logToFile, message);
    }

    public static void fatal(final Object message, final long timeStamp, final boolean logToFile) {
        write_red(Level.FATAL, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void fatal(final String message, final long timeStamp) {
        fatal(message, timeStamp, true);
    }

    public static void fatal(final String message) {
        fatal(message, System.currentTimeMillis(), true);
    }

    public static void fatal(final Object message, final long timeStamp) {
        fatal(message, timeStamp, true);
    }

    public static void fatal(final Object message) {
        fatal(message, System.currentTimeMillis(), true);
    }

    public static void fatal(final Exception exception, final long timeStamp) {
        fatal(exception, timeStamp, true);
    }

    public static void fatal(final Exception exception) {
        fatal(exception, System.currentTimeMillis(), true);
    }

    public static void warn(final Exception message, final long timeStamp, final boolean logToFile) {
        write_yellow(Level.WARNING, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void warn(final String message, final long timeStamp, final boolean logToFile) {
        write_yellow(Level.WARNING, timeStamp, logToFile, message);
    }

    public static void warn(final Object message, final long timeStamp, final boolean logToFile) {
        write_yellow(Level.WARNING, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void warn(final String message, final long timeStamp) {
        warn(message, timeStamp, true);
    }

    public static void warn(final String message) {
        warn(message, System.currentTimeMillis(), true);
    }

    public static void warn(final Object message, final long timeStamp) {
        warn(message, timeStamp, true);
    }

    public static void warn(final Object message) {
        warn(message, System.currentTimeMillis(), true);
    }

    public static void warn(final Exception exception, final long timeStamp) {
        warn(exception, timeStamp, true);
    }

    public static void warn(final Exception exception) {
        warn(exception, System.currentTimeMillis(), true);
    }

    public static void info(final Exception message, final long timeStamp, final boolean logToFile) {
        write_cyan(Level.INFO, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void info(final String message, final long timeStamp, final boolean logToFile) {
        write_cyan(Level.INFO, timeStamp, logToFile, message);
    }

    public static void info(final Object message, final long timeStamp, final boolean logToFile) {
        write_cyan(Level.INFO, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void info(final String message, final long timeStamp) {
        info(message, timeStamp, true);
    }

    public static void info(final String message) {
        info(message, System.currentTimeMillis(), true);
    }

    public static void info(final Object message, final long timeStamp) {
        info(message, timeStamp, true);
    }

    public static void info(final Object message) {
        info(message, System.currentTimeMillis(), true);
    }

    public static void info(final Exception exception, final long timeStamp) {
        info(exception, timeStamp, true);
    }

    public static void info(final Exception exception) {
        info(exception, System.currentTimeMillis(), true);
    }

    public static void debug(final Exception message, final long timeStamp, final boolean logToFile) {
        write(Level.DEBUG, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void debug(final String message, final long timeStamp, final boolean logToFile) {
        write(Level.DEBUG, timeStamp, logToFile, message);
    }

    public static void debug(final Object message, final long timeStamp, final boolean logToFile) {
        write(Level.DEBUG, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void debug(final String message, final long timeStamp) {
        debug(message, timeStamp, true);
    }

    public static void debug(final String message) {
        debug(message, System.currentTimeMillis(), true);
    }

    public static void debug(final Object message, final long timeStamp) {
        debug(message, timeStamp, true);
    }

    public static void debug(final Object message) {
        debug(message, System.currentTimeMillis(), true);
    }

    public static void debug(final Exception exception, final long timeStamp) {
        debug(exception, timeStamp, true);
    }

    public static void debug(final Exception exception) {
        debug(exception, System.currentTimeMillis(), true);
    }

    /*
     * Chalk logger
     */
    
    //! $CHALK_START
    //! $CHALK_END

}

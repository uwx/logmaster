package club.bonerbrew.logmaster;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
                fileWriter = new NullWriter();
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
                if (stdErr) System.err.print(fString);
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
        write_bright_red(Level.FATAL, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void fatal(final String message, final long timeStamp, final boolean logToFile) {
        write_bright_red(Level.FATAL, timeStamp, logToFile, message);
    }

    public static void fatal(final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_red(Level.FATAL, timeStamp, logToFile, message == null ? "null" : message.toString());
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
        write_bright_yellow(Level.WARNING, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void warn(final String message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(Level.WARNING, timeStamp, logToFile, message);
    }

    public static void warn(final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_yellow(Level.WARNING, timeStamp, logToFile, message == null ? "null" : message.toString());
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
        write_bright_cyan(Level.INFO, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void info(final String message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(Level.INFO, timeStamp, logToFile, message);
    }

    public static void info(final Object message, final long timeStamp, final boolean logToFile) {
        write_bright_cyan(Level.INFO, timeStamp, logToFile, message == null ? "null" : message.toString());
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
    
    private static void write_reset(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.reset_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.reset_close + '\n';
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
    
    private static void write_bold(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bold_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bold_close + '\n';
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
    
    private static void write_dim(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.dim_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.dim_close + '\n';
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
    
    private static void write_italic(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.italic_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.italic_close + '\n';
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
    
    private static void write_underline(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.underline_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.underline_close + '\n';
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
    
    private static void write_inverse(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.inverse_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.inverse_close + '\n';
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
    
    private static void write_hidden(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.hidden_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.hidden_close + '\n';
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
    
    private static void write_strikethrough(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.strikethrough_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.strikethrough_close + '\n';
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
    
    private static void write_black(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.black_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.black_close + '\n';
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
    
    private static void write_red(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.red_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.red_close + '\n';
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
    
    private static void write_green(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.green_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.green_close + '\n';
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
    
    private static void write_yellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.yellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.yellow_close + '\n';
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
    
    private static void write_blue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.blue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.blue_close + '\n';
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
    
    private static void write_magenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.magenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.magenta_close + '\n';
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
    
    private static void write_cyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.cyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.cyan_close + '\n';
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
    
    private static void write_white(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.white_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.white_close + '\n';
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
    
    private static void write_gray(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.gray_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.gray_close + '\n';
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
    
    private static void write_grey(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.grey_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.grey_close + '\n';
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
    
    private static void write_bright_red(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_red_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_red_close + '\n';
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
    
    private static void write_bright_green(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_green_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_green_close + '\n';
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
    
    private static void write_bright_yellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_yellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_yellow_close + '\n';
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
    
    private static void write_bright_blue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_blue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_blue_close + '\n';
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
    
    private static void write_bright_magenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_magenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_magenta_close + '\n';
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
    
    private static void write_bright_cyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_cyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_cyan_close + '\n';
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
    
    private static void write_bright_white(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_white_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_white_close + '\n';
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
    
    private static void write_bright_gray(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_gray_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_gray_close + '\n';
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
    
    private static void write_bright_grey(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bright_grey_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bright_grey_close + '\n';
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
    
    private static void write_bgBlack(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgBlack_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgBlack_close + '\n';
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
    
    private static void write_bgRed(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgRed_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgRed_close + '\n';
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
    
    private static void write_bgGreen(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgGreen_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgGreen_close + '\n';
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
    
    private static void write_bgYellow(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgYellow_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgYellow_close + '\n';
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
    
    private static void write_bgBlue(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgBlue_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgBlue_close + '\n';
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
    
    private static void write_bgMagenta(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgMagenta_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgMagenta_close + '\n';
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
    
    private static void write_bgCyan(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgCyan_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgCyan_close + '\n';
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
    
    private static void write_bgWhite(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.bgWhite_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.bgWhite_close + '\n';
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

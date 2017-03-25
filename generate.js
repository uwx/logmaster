
'use strict';

const escapes = {
reset: [ "\\u001b[0m", "\\u001b[0m" ],        
bold: [ "\\u001b[1m", "\\u001b[22m" ],        
dim: [ "\\u001b[2m", "\\u001b[22m" ],        
italic: [ "\\u001b[3m", "\\u001b[23m" ],        
underline: [ "\\u001b[4m", "\\u001b[24m" ],        
inverse: [ "\\u001b[7m", "\\u001b[27m" ],        
hidden: [ "\\u001b[8m", "\\u001b[28m" ],        
strikethrough: [ "\\u001b[9m", "\\u001b[29m" ],        
black: [ "\\u001b[30m", "\\u001b[39m" ],        
red: [ "\\u001b[31m", "\\u001b[39m" ],        
green: [ "\\u001b[32m", "\\u001b[39m" ],        
yellow: [ "\\u001b[33m", "\\u001b[39m" ],        
blue: [ "\\u001b[34m", "\\u001b[39m" ],        
magenta: [ "\\u001b[35m", "\\u001b[39m" ],        
cyan: [ "\\u001b[36m", "\\u001b[39m" ],        
white: [ "\\u001b[37m", "\\u001b[39m" ],        
gray: [ "\\u001b[90m", "\\u001b[39m" ],        
grey: [ "\\u001b[90m", "\\u001b[39m" ],
bright_red: [ "\\u001b[31;1m", "\\u001b[0m" ],        
bright_green: [ "\\u001b[32;1m", "\\u001b[0m" ],        
bright_yellow: [ "\\u001b[33;1m", "\\u001b[0m" ],        
bright_blue: [ "\\u001b[34;1m", "\\u001b[0m" ],        
bright_magenta: [ "\\u001b[35;1m", "\\u001b[0m" ],        
bright_cyan: [ "\\u001b[36;1m", "\\u001b[0m" ],        
bright_white: [ "\\u001b[37;1m", "\\u001b[0m" ],        
bright_gray: [ "\\u001b[90;1m", "\\u001b[0m" ],        
bright_grey: [ "\\u001b[90;1m", "\\u001b[0m" ],        
bgBlack: [ "\\u001b[40m", "\\u001b[49m" ],        
bgRed: [ "\\u001b[41m", "\\u001b[49m" ],        
bgGreen: [ "\\u001b[42m", "\\u001b[49m" ],        
bgYellow: [ "\\u001b[43m", "\\u001b[49m" ],        
bgBlue: [ "\\u001b[44m", "\\u001b[49m" ],        
bgMagenta: [ "\\u001b[45m", "\\u001b[49m" ],        
bgCyan: [ "\\u001b[46m", "\\u001b[49m" ],        
bgWhite: [ "\\u001b[47m", "\\u001b[49m" ],        
};

const writes = {
  reset: 'reset',
  bold: 'bold',
  dim: 'dim',
  italic: 'italic',
  underline: 'underline',
  inverse: 'inverse',
  hidden: 'hidden',
  strikethrough: 'strikethrough',
  black: 'black',
  red: 'red',
  green: 'green',
  yellow: 'yellow',
  blue: 'blue',
  magenta: 'magenta',
  cyan: 'cyan',
  white: 'white',
  gray: 'gray',
  grey: 'grey',
  bright_red: 'bright_red',
  bright_green: 'bright_green',
  bright_yellow: 'bright_yellow',
  bright_blue: 'bright_blue',
  bright_magenta: 'bright_magenta',
  bright_cyan: 'bright_cyan',
  bright_white: 'bright_white',
  bright_gray: 'bright_gray',
  bright_grey: 'bright_grey',
  bgBlack: 'bgBlack',
  bgRed: 'bgRed',
  bgGreen: 'bgGreen',
  bgYellow: 'bgYellow',
  bgBlue: 'bgBlue',
  bgMagenta: 'bgMagenta',
  bgCyan: 'bgCyan',
  bgWhite: 'bgWhite',

  strike: 'strikethrough',
  brightRed: 'bright_red',
  brightGreen: 'bright_green',
  brightYellow: 'bright_yellow',
  brightBlue: 'bright_blue',
  brightMagenta: 'bright_magenta',
  brightCyan: 'bright_cyan',
  brightWhite: 'bright_white',
  brightGray: 'bright_gray',
  brightGrey: 'bright_grey',
  background_black: 'bgBlack',
  background_red: 'bgRed',
  background_green: 'bgGreen',
  background_yellow: 'bgYellow',
  background_blue: 'bgBlue',
  background_magenta: 'bgMagenta',
  background_cyan: 'bgCyan',
  background_white: 'bgWhite',
  log: '',
  error: 'red',
  severe: 'red',
  fatal: 'bright_red',
  warn: 'bright_yellow',
  info: 'bright_cyan',
  debug: '',
  trace: '',

};

const output = [];

Object.keys(writes).forEach(k => {
  output.push(`
    // 1-arg methods
    public static void ${k}(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void ${k}(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, message);
    }

    public static void ${k}(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void ${k}(final int level, final Object message, final long timeStamp) {
        ${k}(level, message, timeStamp, true);
    }

    public static void ${k}(final int level, final String message, final long timeStamp) {
        ${k}(level, message, timeStamp, true);
    }

    public static void ${k}(final int level, final Exception message, final long timeStamp) {
        ${k}(level, message, timeStamp, true);
    }

    public static void ${k}(final int level, final Object message) {
        ${k}(level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}(final int level, final String message) {
        ${k}(level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}(final int level, final Exception message) {
        ${k}(level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}(final String message, final long timeStamp) {
        ${k}(Level.INFO, message, timeStamp, true);
    }

    public static void ${k}(final String message) {
        ${k}(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void ${k}(final Object message, final long timeStamp) {
        ${k}(Level.INFO, message, timeStamp, true);
    }

    public static void ${k}(final Object message) {
        ${k}(Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void ${k}(final Exception exception, final long timeStamp) {
        ${k}(Level.INFO, exception, timeStamp, true);
    }

    public static void ${k}(final Exception exception) {
        ${k}(Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void ${k}(final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void ${k}(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void ${k}(final int level, final Object msg1, Object msg2, final long timeStamp) {
        ${k}(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}(final int level, final String msg1, String msg2, final long timeStamp) {
        ${k}(level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}(final int level, final Object msg1, Object msg2) {
        ${k}(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}(final int level, final String msg1, String msg2) {
        ${k}(level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}(final String msg1, String msg2, final long timeStamp) {
        ${k}(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}(final String msg1, String msg2) {
        ${k}(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}(final Object msg1, Object msg2, final long timeStamp) {
        ${k}(Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}(final Object msg1, Object msg2) {
        ${k}(Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void ${k}(final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void ${k}(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}(level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void ${k}(final int level, final Object[] msgs, final long timeStamp) {
        ${k}(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}(final int level, final String[] msgs, final long timeStamp) {
        ${k}(level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}(final int level, final Object... msgs) {
        ${k}(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}(final int level, final String... msgs) {
        ${k}(level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}(final String[] msgs, final long timeStamp) {
        ${k}(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}(final String... msgs) {
        ${k}(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}(final Object[] msgs, final long timeStamp) {
        ${k}(Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}(final Object... msgs) {
        ${k}(Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }
    
    // ext methods
    
    // 1-arg methods
    public static void ${k}_ext(final String callerClassName, final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, message);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, message == null ? "null" : message.toString());
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object message, final long timeStamp) {
        ${k}_ext(callerClassName, level, message, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String message, final long timeStamp) {
        ${k}_ext(callerClassName, level, message, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Exception message, final long timeStamp) {
        ${k}_ext(callerClassName, level, message, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object message) {
        ${k}_ext(callerClassName, level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String message) {
        ${k}_ext(callerClassName, level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Exception message) {
        ${k}_ext(callerClassName, level, message, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final String message, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, message, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final String message) {
        ${k}_ext(callerClassName, Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final Object message, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, message, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final Object message) {
        ${k}_ext(callerClassName, Level.INFO, message, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final Exception exception, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, exception, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final Exception exception) {
        ${k}_ext(callerClassName, Level.SEVERE, exception, System.currentTimeMillis(), true);
    }
    
    // 2-arg methods
    public static void ${k}_ext(final String callerClassName, final int level, final String msg1, String msg2, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, msg1 + " " + msg2);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object msg1, Object msg2, final long timeStamp) {
        ${k}_ext(callerClassName, level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String msg1, String msg2, final long timeStamp) {
        ${k}_ext(callerClassName, level, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object msg1, Object msg2) {
        ${k}_ext(callerClassName, level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String msg1, String msg2) {
        ${k}_ext(callerClassName, level, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final String msg1, String msg2, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final String msg1, String msg2) {
        ${k}_ext(callerClassName, Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final Object msg1, Object msg2, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, msg1 + " " + msg2, timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final Object msg1, Object msg2) {
        ${k}_ext(callerClassName, Level.INFO, msg1 + " " + msg2, System.currentTimeMillis(), true);
    }
    
    // vararg methods
    public static void ${k}_ext(final String callerClassName, final int level, final String[] msgs, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write_${writes[k]}_ext(callerClassName, level, timeStamp, logToFile, Arrays.toString(msgs));
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object[] msgs, final long timeStamp) {
        ${k}_ext(callerClassName, level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String[] msgs, final long timeStamp) {
        ${k}_ext(callerClassName, level, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final Object... msgs) {
        ${k}_ext(callerClassName, level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final int level, final String... msgs) {
        ${k}_ext(callerClassName, level, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final String[] msgs, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final String... msgs) {
        ${k}_ext(callerClassName, Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }

    public static void ${k}_ext(final String callerClassName, final Object[] msgs, final long timeStamp) {
        ${k}_ext(callerClassName, Level.INFO, Arrays.toString(msgs), timeStamp, true);
    }

    public static void ${k}_ext(final String callerClassName, final Object... msgs) {
        ${k}_ext(callerClassName, Level.INFO, Arrays.toString(msgs), System.currentTimeMillis(), true);
    }
`);
  if (k === writes[k]) {
    output.push(`
    private static void write_${k}(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.${k}_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.${k}_close + '\\n';
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
    
    private static void write_${k}_ext(final String sourceClassName, final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.${k}_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + sourceClassName + "] " + message + Chalk.${k}_close + '\\n';
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
    `);
  }
});

output.push(`
    /**
     * Utility class for dealing with cooooooolors
     *
     * @author Rafael / Chalk contributors (base is used in this)
     *` + `/
    @SuppressWarnings("all")
    public static final class Chalk {
        private Chalk() {
        }
        `);


Object.keys(escapes).forEach(k => {
  output.push(`        public static transient final String ${k}_open = "${escapes[k][0]}";`);
  output.push(`        public static transient final String ${k}_close = "${escapes[k][1]}";`);
  output.push('');
});

Object.keys(escapes).forEach(k => {
  output.push(`        public static void p_${k}(final String s) {`);
  output.push(`            HLogger.log(${k}_open + s + ${k}_close);`);
  output.push(`        }`);
  output.push('');
});

Object.keys(escapes).forEach(k => {
  output.push(`        public static String ${k}(final String s) {`);
  output.push(`            return ${k}_open + s + ${k}_close;`);
  output.push(`        }`);
});

output.push(`
    }`);

//console.log(output.join('\n'));
const fs = require('fs');

fs.writeFileSync('./src/main/java/club/bonerbrew/logmaster/HLogger.java', 
  fs.readFileSync('./src/main/java/club/bonerbrew/logmaster/HLogger.java', 'utf8')
    .replace(/\/\/\! \$CHALK_START[^]*\/\/\! \$CHALK_END/, '//! $$CHALK_START\r\n' + output.join('\r\n') + '\r\n//! $$CHALK_END')
);
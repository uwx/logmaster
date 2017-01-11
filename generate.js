
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

const output = [];

Object.keys(escapes).forEach(k => {
  output.push(`

    public static void ${k}(final int level, final Exception message, final long timeStamp, final boolean logToFile) {
        write_${k}(level, timeStamp, logToFile, ThrowableUtils.getStackTrace(message));
    }

    public static void ${k}(final int level, final String message, final long timeStamp, final boolean logToFile) {
        write_${k}(level, timeStamp, logToFile, message);
    }

    public static void ${k}(final int level, final Object message, final long timeStamp, final boolean logToFile) {
        write_${k}(level, timeStamp, logToFile, message == null ? "null" : message.toString());
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
    
    private static void write_${k}(final int level, final long timeStamp, final boolean logToFile, final String message) {
        final String fString = Chalk.${k}_open + '[' + formatTime(timeStamp) + "] [" + Level.name[level] + "] [" + getCallerCallerClassName() + "] " + message + Chalk.${k}_close + '\\n';
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
`);
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

console.log(output.join('\n'));
const fs = require('fs');

fs.writeFileSync('./src/main/java/club/bonerbrew/logmaster/HLogger.java', 
  fs.readFileSync('./src/main/java/club/bonerbrew/logmaster/HLogger.java', 'utf8')
    .replace(/\/\/\! \$CHALK_START[^]*\/\/\! \$CHALK_END/, '//! $$CHALK_START\r\n' + output.join('\r\n') + '\r\n//! $$CHALK_END')
);
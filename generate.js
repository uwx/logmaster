
'use strict';

const escapes = {
  reset: ["\\u001b[0m", "\\u001b[0m"],
  bold: ["\\u001b[1m", "\\u001b[22m"],
  dim: ["\\u001b[2m", "\\u001b[22m"],
  italic: ["\\u001b[3m", "\\u001b[23m"],
  underline: ["\\u001b[4m", "\\u001b[24m"],
  inverse: ["\\u001b[7m", "\\u001b[27m"],
  hidden: ["\\u001b[8m", "\\u001b[28m"],
  strikethrough: ["\\u001b[9m", "\\u001b[29m"],
  black: ["\\u001b[30m", "\\u001b[39m"],
  red: ["\\u001b[31m", "\\u001b[39m"],
  green: ["\\u001b[32m", "\\u001b[39m"],
  yellow: ["\\u001b[33m", "\\u001b[39m"],
  blue: ["\\u001b[34m", "\\u001b[39m"],
  magenta: ["\\u001b[35m", "\\u001b[39m"],
  cyan: ["\\u001b[36m", "\\u001b[39m"],
  white: ["\\u001b[37m", "\\u001b[39m"],
  gray: ["\\u001b[90m", "\\u001b[39m"],
  grey: ["\\u001b[90m", "\\u001b[39m"],
  brightBlack: ["\\u001b[30;1m", "\\u001b[0m"],
  brightRed: ["\\u001b[31;1m", "\\u001b[0m"],
  brightGreen: ["\\u001b[32;1m", "\\u001b[0m"],
  brightYellow: ["\\u001b[33;1m", "\\u001b[0m"],
  brightBlue: ["\\u001b[34;1m", "\\u001b[0m"],
  brightMagenta: ["\\u001b[35;1m", "\\u001b[0m"],
  brightCyan: ["\\u001b[36;1m", "\\u001b[0m"],
  brightWhite: ["\\u001b[37;1m", "\\u001b[0m"],
  brightGray: ["\\u001b[90;1m", "\\u001b[0m"],
  bgBlack: ["\\u001b[40m", "\\u001b[49m"],
  bgRed: ["\\u001b[41m", "\\u001b[49m"],
  bgGreen: ["\\u001b[42m", "\\u001b[49m"],
  bgYellow: ["\\u001b[43m", "\\u001b[49m"],
  bgBlue: ["\\u001b[44m", "\\u001b[49m"],
  bgMagenta: ["\\u001b[45m", "\\u001b[49m"],
  bgCyan: ["\\u001b[46m", "\\u001b[49m"],
  bgWhite: ["\\u001b[47m", "\\u001b[49m"],
};

const writes = {
  // reset: 'reset',
  // bold: 'bold',
  // dim: 'dim',
  // italic: 'italic',
  // underline: 'underline',
  // inverse: 'inverse',
  // hidden: 'hidden',
  // strikethrough: 'strikethrough',

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
  bgBlack: 'bgBlack',
  bgRed: 'bgRed',
  bgGreen: 'bgGreen',
  bgYellow: 'bgYellow',
  bgBlue: 'bgBlue',
  bgMagenta: 'bgMagenta',
  bgCyan: 'bgCyan',
  bgWhite: 'bgWhite',

  brightBlack: 'brightBlack',
  brightRed: 'brightRed',
  brightGreen: 'brightGreen',
  brightYellow: 'brightYellow',
  brightBlue: 'brightBlue',
  brightMagenta: 'brightMagenta',
  brightCyan: 'brightCyan',
  brightWhite: 'brightWhite',
  brightGray: 'brightGray',

  log: ['', 'Level.INFO'],
  error: ['', 'Level.SEVERE'],
  warn: ['', 'Level.WARNING'],

  debug: ['', 'Level.DEBUG'],
  info: ['', 'Level.INFO'],
  warning: ['', 'Level.WARNING'],
  severe: ['', 'Level.SEVERE'],
  fatal: ['', 'Level.FATAL'],
};

const output = [];

Object.keys(writes).forEach(k => {
  let colorCode = writes[k];
  let forcedLevel;

  if (Array.isArray(writes[k])) {
    [colorCode, forcedLevel] = writes[k];
  }

  const colorOpen = colorCode ? `Chalk.${colorCode}_open` : 'null';
  const colorClose = colorCode ? `Chalk.${colorCode}_close` : 'null';

  output.push(`
    // 1-arg methods
    public static void ${k}(final int level, final Object message) {
        write(level, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final int level, final Exception exception) {
        write(level, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final Object message) {
        write(${forcedLevel || 'Level.INFO'}, System.currentTimeMillis(), true, message == null ? "null" : message.toString(), ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final Exception exception) {
        write(${forcedLevel || 'Level.SEVERE'}, System.currentTimeMillis(), true, ThrowableUtils.getStackTrace(exception), ${colorOpen}, ${colorClose});
    }
    
    // 2-arg methods
    public static void ${k}(final int level, final Object msg1, Object msg2, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, msg1 + " " + msg2, ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final int level, final Object msg1, Object msg2) {
        write(level, System.currentTimeMillis(), true, msg1 + " " + msg2, ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final Object msg1, Object msg2) {
        write(${forcedLevel || 'Level.INFO'}, System.currentTimeMillis(), true, msg1 + " " + msg2, ${colorOpen}, ${colorClose});
    }
    
    // vararg methods
    public static void ${k}(final int level, final Object[] msgs, final long timeStamp, final boolean logToFile) {
        write(level, timeStamp, logToFile, Arrays.toString(msgs), ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final int level, final Object... msgs) {
        write(level, System.currentTimeMillis(), true, Arrays.toString(msgs), ${colorOpen}, ${colorClose});
    }

    public static void ${k}(final Object... msgs) {
        write(${forcedLevel || 'Level.INFO'}, System.currentTimeMillis(), true, Arrays.toString(msgs), ${colorOpen}, ${colorClose});
    }
`);
});

output.push(`
    /**
     * Utility class for dealing with cooooooolors
     *
     * @author Maxine / Chalk contributors (base is used in this)
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

fs.writeFileSync('./src/main/java/fallk/logmaster/HLogger.java', 
  fs.readFileSync('./src/main/java/fallk/logmaster/HLogger.java', 'utf8')
    .replace(/\/\/\! \$CHALK_START[^]*\/\/\! \$CHALK_END/, '//! $$CHALK_START\r\n' + output.join('\r\n') + '\r\n//! $$CHALK_END')
);
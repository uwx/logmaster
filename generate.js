
'use strict';

function getEscape(codes) {
  let open = `\\u001b[${codes[0]}m`;
  let close = `\\u001b[${codes[1]}m`;
  return [open, close];
}

const escapes = {
  reset: getEscape([0, 0]),
  bold: getEscape([1, 22]),
  dim: getEscape([2, 22]),
  italic: getEscape([3, 23]),
  underline: getEscape([4, 24]),
  inverse: getEscape([7, 27]),
  hidden: getEscape([8, 28]),
  strikethrough: getEscape([9, 29]),

  black: getEscape([30, 39]),
  red: getEscape([31, 39]),
  green: getEscape([32, 39]),
  yellow: getEscape([33, 39]),
  blue: getEscape([34, 39]),
  magenta: getEscape([35, 39]),
  cyan: getEscape([36, 39]),
  white: getEscape([37, 39]),
  gray: getEscape([90, 39]),
  grey: getEscape([90, 39]),

  bgBlack: getEscape([40, 49]),
  bgRed: getEscape([41, 49]),
  bgGreen: getEscape([42, 49]),
  bgYellow: getEscape([43, 49]),
  bgBlue: getEscape([44, 49]),
  bgMagenta: getEscape([45, 49]),
  bgCyan: getEscape([46, 49]),
  bgWhite: getEscape([47, 49]),

  brightBlack: getEscape([90, 39]),
  brightRed: getEscape([91, 39]),
  brightGreen: getEscape([92, 39]),
  brightYellow: getEscape([93, 39]),
  brightBlue: getEscape([94, 39]),
  brightMagenta: getEscape([95, 39]),
  brightCyan: getEscape([96, 39]),
  brightWhite: getEscape([97, 39]),

  bgBrightBlack: getEscape([100, 49]),
  bgBrightRed: getEscape([101, 49]),
  bgBrightGreen: getEscape([102, 49]),
  bgBrightYellow: getEscape([103, 49]),
  bgBrightBlue: getEscape([104, 49]),
  bgBrightMagenta: getEscape([105, 49]),
  bgBrightCyan: getEscape([106, 49]),
  bgBrightWhite: getEscape([107, 49]),
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

  bgBrightBlack: 'bgBrightBlack',
  bgBrightRed: 'bgBrightRed',
  bgBrightGreen: 'bgBrightGreen',
  bgBrightYellow: 'bgBrightYellow',
  bgBrightBlue: 'bgBrightBlue',
  bgBrightMagenta: 'bgBrightMagenta',
  bgBrightCyan: 'bgBrightCyan',
  bgBrightWhite: 'bgBrightWhite',

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
    // region ${k}
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
    // endregion ${k}
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
const bigArr = [
["log", ["int", "Exception", "long", "boolean"]],
["log", ["int", "String", "long", "boolean"]],
["log", ["int", "Object", "long", "boolean"]],
["log", ["int", "Object", "long"]],
["log", ["int", "String", "long"]],
["log", ["int", "Exception", "long"]],
["log", ["int", "Object"]],
["log", ["int", "String"]],
["log", ["int", "Exception"]],
["log", ["String", "long"]],
["log", ["String"]],
["log", ["Object", "long"]],
["log", ["Object"]],
["log", ["Exception", "long"]],
["log", ["Exception"]],
["error", ["Exception", "long", "boolean"]],
["error", ["String", "long", "boolean"]],
["error", ["Object", "long", "boolean"]],
["error", ["String", "long"]],
["error", ["String"]],
["error", ["Object", "long"]],
["error", ["Object"]],
["error", ["Exception", "long"]],
["error", ["Exception"]],
["severe", ["Exception", "long", "boolean"]],
["severe", ["String", "long", "boolean"]],
["severe", ["Object", "long", "boolean"]],
["severe", ["String", "long"]],
["severe", ["String"]],
["severe", ["Object", "long"]],
["severe", ["Object"]],
["severe", ["Exception", "long"]],
["severe", ["Exception"]],
["fatal", ["Exception", "long", "boolean"]],
["fatal", ["String", "long", "boolean"]],
["fatal", ["Object", "long", "boolean"]],
["fatal", ["String", "long"]],
["fatal", ["String"]],
["fatal", ["Object", "long"]],
["fatal", ["Object"]],
["fatal", ["Exception", "long"]],
["fatal", ["Exception"]],
["warn", ["Exception", "long", "boolean"]],
["warn", ["String", "long", "boolean"]],
["warn", ["Object", "long", "boolean"]],
["warn", ["String", "long"]],
["warn", ["String"]],
["warn", ["Object", "long"]],
["warn", ["Object"]],
["warn", ["Exception", "long"]],
["warn", ["Exception"]],
["info", ["Exception", "long", "boolean"]],
["info", ["String", "long", "boolean"]],
["info", ["Object", "long", "boolean"]],
["info", ["String", "long"]],
["info", ["String"]],
["info", ["Object", "long"]],
["info", ["Object"]],
["info", ["Exception", "long"]],
["info", ["Exception"]],
["debug", ["Exception", "long", "boolean"]],
["debug", ["String", "long", "boolean"]],
["debug", ["Object", "long", "boolean"]],
["debug", ["String", "long"]],
["debug", ["String"]],
["debug", ["Object", "long"]],
["debug", ["Object"]],
["debug", ["Exception", "long"]],
["debug", ["Exception"]],
["reset", ["int", "Exception", "long", "boolean"]],
["reset", ["int", "String", "long", "boolean"]],
["reset", ["int", "Object", "long", "boolean"]],
["reset", ["int", "Object", "long"]],
["reset", ["int", "String", "long"]],
["reset", ["int", "Exception", "long"]],
["reset", ["int", "Object"]],
["reset", ["int", "String"]],
["reset", ["int", "Exception"]],
["reset", ["String", "long"]],
["reset", ["String"]],
["reset", ["Object", "long"]],
["reset", ["Object"]],
["reset", ["Exception", "long"]],
["reset", ["Exception"]],
["bold", ["int", "Exception", "long", "boolean"]],
["bold", ["int", "String", "long", "boolean"]],
["bold", ["int", "Object", "long", "boolean"]],
["bold", ["int", "Object", "long"]],
["bold", ["int", "String", "long"]],
["bold", ["int", "Exception", "long"]],
["bold", ["int", "Object"]],
["bold", ["int", "String"]],
["bold", ["int", "Exception"]],
["bold", ["String", "long"]],
["bold", ["String"]],
["bold", ["Object", "long"]],
["bold", ["Object"]],
["bold", ["Exception", "long"]],
["bold", ["Exception"]],
["dim", ["int", "Exception", "long", "boolean"]],
["dim", ["int", "String", "long", "boolean"]],
["dim", ["int", "Object", "long", "boolean"]],
["dim", ["int", "Object", "long"]],
["dim", ["int", "String", "long"]],
["dim", ["int", "Exception", "long"]],
["dim", ["int", "Object"]],
["dim", ["int", "String"]],
["dim", ["int", "Exception"]],
["dim", ["String", "long"]],
["dim", ["String"]],
["dim", ["Object", "long"]],
["dim", ["Object"]],
["dim", ["Exception", "long"]],
["dim", ["Exception"]],
["italic", ["int", "Exception", "long", "boolean"]],
["italic", ["int", "String", "long", "boolean"]],
["italic", ["int", "Object", "long", "boolean"]],
["italic", ["int", "Object", "long"]],
["italic", ["int", "String", "long"]],
["italic", ["int", "Exception", "long"]],
["italic", ["int", "Object"]],
["italic", ["int", "String"]],
["italic", ["int", "Exception"]],
["italic", ["String", "long"]],
["italic", ["String"]],
["italic", ["Object", "long"]],
["italic", ["Object"]],
["italic", ["Exception", "long"]],
["italic", ["Exception"]],
["underline", ["int", "Exception", "long", "boolean"]],
["underline", ["int", "String", "long", "boolean"]],
["underline", ["int", "Object", "long", "boolean"]],
["underline", ["int", "Object", "long"]],
["underline", ["int", "String", "long"]],
["underline", ["int", "Exception", "long"]],
["underline", ["int", "Object"]],
["underline", ["int", "String"]],
["underline", ["int", "Exception"]],
["underline", ["String", "long"]],
["underline", ["String"]],
["underline", ["Object", "long"]],
["underline", ["Object"]],
["underline", ["Exception", "long"]],
["underline", ["Exception"]],
["inverse", ["int", "Exception", "long", "boolean"]],
["inverse", ["int", "String", "long", "boolean"]],
["inverse", ["int", "Object", "long", "boolean"]],
["inverse", ["int", "Object", "long"]],
["inverse", ["int", "String", "long"]],
["inverse", ["int", "Exception", "long"]],
["inverse", ["int", "Object"]],
["inverse", ["int", "String"]],
["inverse", ["int", "Exception"]],
["inverse", ["String", "long"]],
["inverse", ["String"]],
["inverse", ["Object", "long"]],
["inverse", ["Object"]],
["inverse", ["Exception", "long"]],
["inverse", ["Exception"]],
["hidden", ["int", "Exception", "long", "boolean"]],
["hidden", ["int", "String", "long", "boolean"]],
["hidden", ["int", "Object", "long", "boolean"]],
["hidden", ["int", "Object", "long"]],
["hidden", ["int", "String", "long"]],
["hidden", ["int", "Exception", "long"]],
["hidden", ["int", "Object"]],
["hidden", ["int", "String"]],
["hidden", ["int", "Exception"]],
["hidden", ["String", "long"]],
["hidden", ["String"]],
["hidden", ["Object", "long"]],
["hidden", ["Object"]],
["hidden", ["Exception", "long"]],
["hidden", ["Exception"]],
["strikethrough", ["int", "Exception", "long", "boolean"]],
["strikethrough", ["int", "String", "long", "boolean"]],
["strikethrough", ["int", "Object", "long", "boolean"]],
["strikethrough", ["int", "Object", "long"]],
["strikethrough", ["int", "String", "long"]],
["strikethrough", ["int", "Exception", "long"]],
["strikethrough", ["int", "Object"]],
["strikethrough", ["int", "String"]],
["strikethrough", ["int", "Exception"]],
["strikethrough", ["String", "long"]],
["strikethrough", ["String"]],
["strikethrough", ["Object", "long"]],
["strikethrough", ["Object"]],
["strikethrough", ["Exception", "long"]],
["strikethrough", ["Exception"]],
["black", ["int", "Exception", "long", "boolean"]],
["black", ["int", "String", "long", "boolean"]],
["black", ["int", "Object", "long", "boolean"]],
["black", ["int", "Object", "long"]],
["black", ["int", "String", "long"]],
["black", ["int", "Exception", "long"]],
["black", ["int", "Object"]],
["black", ["int", "String"]],
["black", ["int", "Exception"]],
["black", ["String", "long"]],
["black", ["String"]],
["black", ["Object", "long"]],
["black", ["Object"]],
["black", ["Exception", "long"]],
["black", ["Exception"]],
["red", ["int", "Exception", "long", "boolean"]],
["red", ["int", "String", "long", "boolean"]],
["red", ["int", "Object", "long", "boolean"]],
["red", ["int", "Object", "long"]],
["red", ["int", "String", "long"]],
["red", ["int", "Exception", "long"]],
["red", ["int", "Object"]],
["red", ["int", "String"]],
["red", ["int", "Exception"]],
["red", ["String", "long"]],
["red", ["String"]],
["red", ["Object", "long"]],
["red", ["Object"]],
["red", ["Exception", "long"]],
["red", ["Exception"]],
["green", ["int", "Exception", "long", "boolean"]],
["green", ["int", "String", "long", "boolean"]],
["green", ["int", "Object", "long", "boolean"]],
["green", ["int", "Object", "long"]],
["green", ["int", "String", "long"]],
["green", ["int", "Exception", "long"]],
["green", ["int", "Object"]],
["green", ["int", "String"]],
["green", ["int", "Exception"]],
["green", ["String", "long"]],
["green", ["String"]],
["green", ["Object", "long"]],
["green", ["Object"]],
["green", ["Exception", "long"]],
["green", ["Exception"]],
["yellow", ["int", "Exception", "long", "boolean"]],
["yellow", ["int", "String", "long", "boolean"]],
["yellow", ["int", "Object", "long", "boolean"]],
["yellow", ["int", "Object", "long"]],
["yellow", ["int", "String", "long"]],
["yellow", ["int", "Exception", "long"]],
["yellow", ["int", "Object"]],
["yellow", ["int", "String"]],
["yellow", ["int", "Exception"]],
["yellow", ["String", "long"]],
["yellow", ["String"]],
["yellow", ["Object", "long"]],
["yellow", ["Object"]],
["yellow", ["Exception", "long"]],
["yellow", ["Exception"]],
["blue", ["int", "Exception", "long", "boolean"]],
["blue", ["int", "String", "long", "boolean"]],
["blue", ["int", "Object", "long", "boolean"]],
["blue", ["int", "Object", "long"]],
["blue", ["int", "String", "long"]],
["blue", ["int", "Exception", "long"]],
["blue", ["int", "Object"]],
["blue", ["int", "String"]],
["blue", ["int", "Exception"]],
["blue", ["String", "long"]],
["blue", ["String"]],
["blue", ["Object", "long"]],
["blue", ["Object"]],
["blue", ["Exception", "long"]],
["blue", ["Exception"]],
["magenta", ["int", "Exception", "long", "boolean"]],
["magenta", ["int", "String", "long", "boolean"]],
["magenta", ["int", "Object", "long", "boolean"]],
["magenta", ["int", "Object", "long"]],
["magenta", ["int", "String", "long"]],
["magenta", ["int", "Exception", "long"]],
["magenta", ["int", "Object"]],
["magenta", ["int", "String"]],
["magenta", ["int", "Exception"]],
["magenta", ["String", "long"]],
["magenta", ["String"]],
["magenta", ["Object", "long"]],
["magenta", ["Object"]],
["magenta", ["Exception", "long"]],
["magenta", ["Exception"]],
["cyan", ["int", "Exception", "long", "boolean"]],
["cyan", ["int", "String", "long", "boolean"]],
["cyan", ["int", "Object", "long", "boolean"]],
["cyan", ["int", "Object", "long"]],
["cyan", ["int", "String", "long"]],
["cyan", ["int", "Exception", "long"]],
["cyan", ["int", "Object"]],
["cyan", ["int", "String"]],
["cyan", ["int", "Exception"]],
["cyan", ["String", "long"]],
["cyan", ["String"]],
["cyan", ["Object", "long"]],
["cyan", ["Object"]],
["cyan", ["Exception", "long"]],
["cyan", ["Exception"]],
["white", ["int", "Exception", "long", "boolean"]],
["white", ["int", "String", "long", "boolean"]],
["white", ["int", "Object", "long", "boolean"]],
["white", ["int", "Object", "long"]],
["white", ["int", "String", "long"]],
["white", ["int", "Exception", "long"]],
["white", ["int", "Object"]],
["white", ["int", "String"]],
["white", ["int", "Exception"]],
["white", ["String", "long"]],
["white", ["String"]],
["white", ["Object", "long"]],
["white", ["Object"]],
["white", ["Exception", "long"]],
["white", ["Exception"]],
["gray", ["int", "Exception", "long", "boolean"]],
["gray", ["int", "String", "long", "boolean"]],
["gray", ["int", "Object", "long", "boolean"]],
["gray", ["int", "Object", "long"]],
["gray", ["int", "String", "long"]],
["gray", ["int", "Exception", "long"]],
["gray", ["int", "Object"]],
["gray", ["int", "String"]],
["gray", ["int", "Exception"]],
["gray", ["String", "long"]],
["gray", ["String"]],
["gray", ["Object", "long"]],
["gray", ["Object"]],
["gray", ["Exception", "long"]],
["gray", ["Exception"]],
["grey", ["int", "Exception", "long", "boolean"]],
["grey", ["int", "String", "long", "boolean"]],
["grey", ["int", "Object", "long", "boolean"]],
["grey", ["int", "Object", "long"]],
["grey", ["int", "String", "long"]],
["grey", ["int", "Exception", "long"]],
["grey", ["int", "Object"]],
["grey", ["int", "String"]],
["grey", ["int", "Exception"]],
["grey", ["String", "long"]],
["grey", ["String"]],
["grey", ["Object", "long"]],
["grey", ["Object"]],
["grey", ["Exception", "long"]],
["grey", ["Exception"]],
["bright_red", ["int", "Exception", "long", "boolean"]],
["bright_red", ["int", "String", "long", "boolean"]],
["bright_red", ["int", "Object", "long", "boolean"]],
["bright_red", ["int", "Object", "long"]],
["bright_red", ["int", "String", "long"]],
["bright_red", ["int", "Exception", "long"]],
["bright_red", ["int", "Object"]],
["bright_red", ["int", "String"]],
["bright_red", ["int", "Exception"]],
["bright_red", ["String", "long"]],
["bright_red", ["String"]],
["bright_red", ["Object", "long"]],
["bright_red", ["Object"]],
["bright_red", ["Exception", "long"]],
["bright_red", ["Exception"]],
["bright_green", ["int", "Exception", "long", "boolean"]],
["bright_green", ["int", "String", "long", "boolean"]],
["bright_green", ["int", "Object", "long", "boolean"]],
["bright_green", ["int", "Object", "long"]],
["bright_green", ["int", "String", "long"]],
["bright_green", ["int", "Exception", "long"]],
["bright_green", ["int", "Object"]],
["bright_green", ["int", "String"]],
["bright_green", ["int", "Exception"]],
["bright_green", ["String", "long"]],
["bright_green", ["String"]],
["bright_green", ["Object", "long"]],
["bright_green", ["Object"]],
["bright_green", ["Exception", "long"]],
["bright_green", ["Exception"]],
["bright_yellow", ["int", "Exception", "long", "boolean"]],
["bright_yellow", ["int", "String", "long", "boolean"]],
["bright_yellow", ["int", "Object", "long", "boolean"]],
["bright_yellow", ["int", "Object", "long"]],
["bright_yellow", ["int", "String", "long"]],
["bright_yellow", ["int", "Exception", "long"]],
["bright_yellow", ["int", "Object"]],
["bright_yellow", ["int", "String"]],
["bright_yellow", ["int", "Exception"]],
["bright_yellow", ["String", "long"]],
["bright_yellow", ["String"]],
["bright_yellow", ["Object", "long"]],
["bright_yellow", ["Object"]],
["bright_yellow", ["Exception", "long"]],
["bright_yellow", ["Exception"]],
["bright_blue", ["int", "Exception", "long", "boolean"]],
["bright_blue", ["int", "String", "long", "boolean"]],
["bright_blue", ["int", "Object", "long", "boolean"]],
["bright_blue", ["int", "Object", "long"]],
["bright_blue", ["int", "String", "long"]],
["bright_blue", ["int", "Exception", "long"]],
["bright_blue", ["int", "Object"]],
["bright_blue", ["int", "String"]],
["bright_blue", ["int", "Exception"]],
["bright_blue", ["String", "long"]],
["bright_blue", ["String"]],
["bright_blue", ["Object", "long"]],
["bright_blue", ["Object"]],
["bright_blue", ["Exception", "long"]],
["bright_blue", ["Exception"]],
["bright_magenta", ["int", "Exception", "long", "boolean"]],
["bright_magenta", ["int", "String", "long", "boolean"]],
["bright_magenta", ["int", "Object", "long", "boolean"]],
["bright_magenta", ["int", "Object", "long"]],
["bright_magenta", ["int", "String", "long"]],
["bright_magenta", ["int", "Exception", "long"]],
["bright_magenta", ["int", "Object"]],
["bright_magenta", ["int", "String"]],
["bright_magenta", ["int", "Exception"]],
["bright_magenta", ["String", "long"]],
["bright_magenta", ["String"]],
["bright_magenta", ["Object", "long"]],
["bright_magenta", ["Object"]],
["bright_magenta", ["Exception", "long"]],
["bright_magenta", ["Exception"]],
["bright_cyan", ["int", "Exception", "long", "boolean"]],
["bright_cyan", ["int", "String", "long", "boolean"]],
["bright_cyan", ["int", "Object", "long", "boolean"]],
["bright_cyan", ["int", "Object", "long"]],
["bright_cyan", ["int", "String", "long"]],
["bright_cyan", ["int", "Exception", "long"]],
["bright_cyan", ["int", "Object"]],
["bright_cyan", ["int", "String"]],
["bright_cyan", ["int", "Exception"]],
["bright_cyan", ["String", "long"]],
["bright_cyan", ["String"]],
["bright_cyan", ["Object", "long"]],
["bright_cyan", ["Object"]],
["bright_cyan", ["Exception", "long"]],
["bright_cyan", ["Exception"]],
["bright_white", ["int", "Exception", "long", "boolean"]],
["bright_white", ["int", "String", "long", "boolean"]],
["bright_white", ["int", "Object", "long", "boolean"]],
["bright_white", ["int", "Object", "long"]],
["bright_white", ["int", "String", "long"]],
["bright_white", ["int", "Exception", "long"]],
["bright_white", ["int", "Object"]],
["bright_white", ["int", "String"]],
["bright_white", ["int", "Exception"]],
["bright_white", ["String", "long"]],
["bright_white", ["String"]],
["bright_white", ["Object", "long"]],
["bright_white", ["Object"]],
["bright_white", ["Exception", "long"]],
["bright_white", ["Exception"]],
["bright_gray", ["int", "Exception", "long", "boolean"]],
["bright_gray", ["int", "String", "long", "boolean"]],
["bright_gray", ["int", "Object", "long", "boolean"]],
["bright_gray", ["int", "Object", "long"]],
["bright_gray", ["int", "String", "long"]],
["bright_gray", ["int", "Exception", "long"]],
["bright_gray", ["int", "Object"]],
["bright_gray", ["int", "String"]],
["bright_gray", ["int", "Exception"]],
["bright_gray", ["String", "long"]],
["bright_gray", ["String"]],
["bright_gray", ["Object", "long"]],
["bright_gray", ["Object"]],
["bright_gray", ["Exception", "long"]],
["bright_gray", ["Exception"]],
["bright_grey", ["int", "Exception", "long", "boolean"]],
["bright_grey", ["int", "String", "long", "boolean"]],
["bright_grey", ["int", "Object", "long", "boolean"]],
["bright_grey", ["int", "Object", "long"]],
["bright_grey", ["int", "String", "long"]],
["bright_grey", ["int", "Exception", "long"]],
["bright_grey", ["int", "Object"]],
["bright_grey", ["int", "String"]],
["bright_grey", ["int", "Exception"]],
["bright_grey", ["String", "long"]],
["bright_grey", ["String"]],
["bright_grey", ["Object", "long"]],
["bright_grey", ["Object"]],
["bright_grey", ["Exception", "long"]],
["bright_grey", ["Exception"]],
["bgBlack", ["int", "Exception", "long", "boolean"]],
["bgBlack", ["int", "String", "long", "boolean"]],
["bgBlack", ["int", "Object", "long", "boolean"]],
["bgBlack", ["int", "Object", "long"]],
["bgBlack", ["int", "String", "long"]],
["bgBlack", ["int", "Exception", "long"]],
["bgBlack", ["int", "Object"]],
["bgBlack", ["int", "String"]],
["bgBlack", ["int", "Exception"]],
["bgBlack", ["String", "long"]],
["bgBlack", ["String"]],
["bgBlack", ["Object", "long"]],
["bgBlack", ["Object"]],
["bgBlack", ["Exception", "long"]],
["bgBlack", ["Exception"]],
["bgRed", ["int", "Exception", "long", "boolean"]],
["bgRed", ["int", "String", "long", "boolean"]],
["bgRed", ["int", "Object", "long", "boolean"]],
["bgRed", ["int", "Object", "long"]],
["bgRed", ["int", "String", "long"]],
["bgRed", ["int", "Exception", "long"]],
["bgRed", ["int", "Object"]],
["bgRed", ["int", "String"]],
["bgRed", ["int", "Exception"]],
["bgRed", ["String", "long"]],
["bgRed", ["String"]],
["bgRed", ["Object", "long"]],
["bgRed", ["Object"]],
["bgRed", ["Exception", "long"]],
["bgRed", ["Exception"]],
["bgGreen", ["int", "Exception", "long", "boolean"]],
["bgGreen", ["int", "String", "long", "boolean"]],
["bgGreen", ["int", "Object", "long", "boolean"]],
["bgGreen", ["int", "Object", "long"]],
["bgGreen", ["int", "String", "long"]],
["bgGreen", ["int", "Exception", "long"]],
["bgGreen", ["int", "Object"]],
["bgGreen", ["int", "String"]],
["bgGreen", ["int", "Exception"]],
["bgGreen", ["String", "long"]],
["bgGreen", ["String"]],
["bgGreen", ["Object", "long"]],
["bgGreen", ["Object"]],
["bgGreen", ["Exception", "long"]],
["bgGreen", ["Exception"]],
["bgYellow", ["int", "Exception", "long", "boolean"]],
["bgYellow", ["int", "String", "long", "boolean"]],
["bgYellow", ["int", "Object", "long", "boolean"]],
["bgYellow", ["int", "Object", "long"]],
["bgYellow", ["int", "String", "long"]],
["bgYellow", ["int", "Exception", "long"]],
["bgYellow", ["int", "Object"]],
["bgYellow", ["int", "String"]],
["bgYellow", ["int", "Exception"]],
["bgYellow", ["String", "long"]],
["bgYellow", ["String"]],
["bgYellow", ["Object", "long"]],
["bgYellow", ["Object"]],
["bgYellow", ["Exception", "long"]],
["bgYellow", ["Exception"]],
["bgBlue", ["int", "Exception", "long", "boolean"]],
["bgBlue", ["int", "String", "long", "boolean"]],
["bgBlue", ["int", "Object", "long", "boolean"]],
["bgBlue", ["int", "Object", "long"]],
["bgBlue", ["int", "String", "long"]],
["bgBlue", ["int", "Exception", "long"]],
["bgBlue", ["int", "Object"]],
["bgBlue", ["int", "String"]],
["bgBlue", ["int", "Exception"]],
["bgBlue", ["String", "long"]],
["bgBlue", ["String"]],
["bgBlue", ["Object", "long"]],
["bgBlue", ["Object"]],
["bgBlue", ["Exception", "long"]],
["bgBlue", ["Exception"]],
["bgMagenta", ["int", "Exception", "long", "boolean"]],
["bgMagenta", ["int", "String", "long", "boolean"]],
["bgMagenta", ["int", "Object", "long", "boolean"]],
["bgMagenta", ["int", "Object", "long"]],
["bgMagenta", ["int", "String", "long"]],
["bgMagenta", ["int", "Exception", "long"]],
["bgMagenta", ["int", "Object"]],
["bgMagenta", ["int", "String"]],
["bgMagenta", ["int", "Exception"]],
["bgMagenta", ["String", "long"]],
["bgMagenta", ["String"]],
["bgMagenta", ["Object", "long"]],
["bgMagenta", ["Object"]],
["bgMagenta", ["Exception", "long"]],
["bgMagenta", ["Exception"]],
["bgCyan", ["int", "Exception", "long", "boolean"]],
["bgCyan", ["int", "String", "long", "boolean"]],
["bgCyan", ["int", "Object", "long", "boolean"]],
["bgCyan", ["int", "Object", "long"]],
["bgCyan", ["int", "String", "long"]],
["bgCyan", ["int", "Exception", "long"]],
["bgCyan", ["int", "Object"]],
["bgCyan", ["int", "String"]],
["bgCyan", ["int", "Exception"]],
["bgCyan", ["String", "long"]],
["bgCyan", ["String"]],
["bgCyan", ["Object", "long"]],
["bgCyan", ["Object"]],
["bgCyan", ["Exception", "long"]],
["bgCyan", ["Exception"]],
["bgWhite", ["int", "Exception", "long", "boolean"]],
["bgWhite", ["int", "String", "long", "boolean"]],
["bgWhite", ["int", "Object", "long", "boolean"]],
["bgWhite", ["int", "Object", "long"]],
["bgWhite", ["int", "String", "long"]],
["bgWhite", ["int", "Exception", "long"]],
["bgWhite", ["int", "Object"]],
["bgWhite", ["int", "String"]],
["bgWhite", ["int", "Exception"]],
["bgWhite", ["String", "long"]],
["bgWhite", ["String"]],
["bgWhite", ["Object", "long"]],
["bgWhite", ["Object"]],
["bgWhite", ["Exception", "long"]],
["bgWhite", ["Exception"]],
];

// 
// success tests
// 

const fs = require('fs');

bigArr.forEach((v, _i) => {
  let output = [];
  //const methodName = v[0];
  const options = v[1].slice();//slice makes a copy
  
  if (options.length === 1) {//if length == 1 combs doesnt work so we do it manually
    const b = opts(options[0]);
    output.push(`
    @Test
    public final void test_${v[0]}_${v[1].join('_')}() {`);
    for (let i = 0; i < b.length; i++) {
    output.push(`
        HLogger.${v[0]}(${b[i]});`);
    }
  output.push(`
    }
`);
    return;
  }
  
  for (let i = 0; i < options.length; i++) {
    options[i] = opts(options[i]);
  }
  
  const boptions = combinations(options);
  
  /* legacy parser: (fuckload of methods)
  
  for (let i = 0; i < boptions.length; i++) {
    if (!boptions[i][0]) continue;
    output.push(`
    @Test
    public final void test_${v[0]}_${v[1].join('_')}_${i}() {
        HLogger.${v[0]}(${boptions[i].join(', ')});
    }
`);
  }
  */
  
  // new parser: (1 method for each method)
  output.push(`
    @Test
    public final void test_${v[0]}_${v[1].join('_')}() {`);
  for (let i = 0; i < boptions.length; i++) {
    if (!boptions[i][0]) {
      continue;
    }
    
    output.push(`
        HLogger.${v[0]}(${boptions[i].join(', ')});`);
  }
  output.push(`
    }
`);


  fs.writeFileSync('./src/test/java/fallk/logmaster/LogTest' + _i + '.java',
    `
    package fallk.logmaster;

import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogTest${_i} {

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
    
    ${output.join('')}
    
    //! $CHALK_END
}
    `
  );

});

function opts(o) {
  if (o === 'String') {
    return ['"Test string"', '(String) null'];
  } else if (o === 'long') {
    return ['System.currentTimeMillis()', 'System.nanoTime()', 'Long.MAX_VALUE', 'Long.MIN_VALUE', '0'];
  } else if (o === 'Exception') {
    return ['new Exception()', '(Exception) null'];
  } else if (o === 'int') {
    return ['0', '1', '2', '3', '4'];
  } else if (o === 'boolean') {
    return ['true', 'false'];
  } else if (o === 'Object') {
    return ['new Object()', 'new Dummy()', 'new Dummy2()', '(Object) null'];
  }
}

function combinations(twoDimStringArray) {
  const sizeArray = [];
  const counterArray = [];

  let totalCombinationCount = 1;
  for(let i = 0; i < twoDimStringArray.length; ++i) {
      sizeArray[i] = twoDimStringArray[i].length;
      totalCombinationCount *= twoDimStringArray[i].length;
  }

  // Store the combinations in a List of String objects
  const combinationList = [];

  let sb;  // more efficient than String for concatenation

  for (let countdown = totalCombinationCount; countdown > 0; --countdown) {
      // Run through the inner arrays, grabbing the member from the index
      // specified by the counterArray for each inner array, and build a
      // combination string.
      sb = [];
      for(let i = 0; i < twoDimStringArray.length; ++i) {
          sb.push(twoDimStringArray[i][counterArray[i]]);
      }
      combinationList.push(sb);  // add new combination to list

      // Now we need to increment the counterArray so that the next
      // combination is taken on the next iteration of this loop.
      for(let incIndex = twoDimStringArray.length - 1; incIndex >= 0; --incIndex) {
          if(counterArray[incIndex] + 1 < sizeArray[incIndex]) {
              ++counterArray[incIndex];
              // None of the indices of higher significance need to be
              // incremented, so jump out of this for loop at this point.
              break;
          }
          // The index at this position is at its max value, so zero it
          // and continue this loop to increment the index which is more
          // significant than this one.
          counterArray[incIndex] = 0;
      }
  }
  return combinationList;
}


/**
 * Produce a List{@literal <}String> which contains every combination which can be
 * made by taking one String from each inner String array within the
 * provided two-dimensional String array.
 * @param twoDimStringArray a two-dimensional String array which contains
 * String arrays of variable length.
 * @return a List which contains every String which can be formed by taking
 * one String from each String array within the specified two-dimensional
 * array.
 */
/*
public static <E> List<List<E>> combinations(E[][] twoDimStringArray) {
    // keep track of the size of each inner String array
    int sizeArray[] = new int[twoDimStringArray.length];

    // keep track of the index of each inner String array which will be used
    // to make the next combination
    int counterArray[] = new int[twoDimStringArray.length];

    // Discover the size of each inner array and populate sizeArray.
    // Also calculate the total number of combinations possible using the
    // inner String array sizes.
    int totalCombinationCount = 1;
    for(int i = 0; i < twoDimStringArray.length; ++i) {
        sizeArray[i] = twoDimStringArray[i].length;
        totalCombinationCount *= twoDimStringArray[i].length;
    }

    // Store the combinations in a List of String objects
    List<List<E>> combinationList = new ArrayList<>(totalCombinationCount);

    List<E> sb;  // more efficient than String for concatenation

    for (int countdown = totalCombinationCount; countdown > 0; --countdown) {
        // Run through the inner arrays, grabbing the member from the index
        // specified by the counterArray for each inner array, and build a
        // combination string.
        sb = new ArrayList<>();
        for(int i = 0; i < twoDimStringArray.length; ++i) {
            sb.add(twoDimStringArray[i][counterArray[i]]);
        }
        combinationList.add(sb);  // add new combination to list

        // Now we need to increment the counterArray so that the next
        // combination is taken on the next iteration of this loop.
        for(int incIndex = twoDimStringArray.length - 1; incIndex >= 0; --incIndex) {
            if(counterArray[incIndex] + 1 < sizeArray[incIndex]) {
                ++counterArray[incIndex];
                // None of the indices of higher significance need to be
                // incremented, so jump out of this for loop at this point.
                break;
            }
            // The index at this position is at its max value, so zero it
            // and continue this loop to increment the index which is more
            // significant than this one.
            counterArray[incIndex] = 0;
        }
    }
    return combinationList;
}*/
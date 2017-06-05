package fallk.logmaster;

public class LogmasterSettings {
    /**
     * @see HLogger#outputFine
     * @see HLogger#stdErr
     * @see HLogger#outputToFile
     * @see HLogger#outputFine
     */
    boolean stdOut, stdErr, outputToFile, outputFine;
    private LogmasterSettings() {
        this.stdOut  = HLogger.stdOut;
        this.stdErr = HLogger.stdErr;
        this.outputToFile = HLogger.outputToFile;
        this.outputFine = HLogger.outputFine;
    }
    /**
     * Create a new Logmaster builder
     * @return the LogmasterSettings instance
     */
    public static LogmasterSettings c() {
        return new LogmasterSettings();
    }
    /**
     * Create a new Logmaster builder
     * @return the LogmasterSettings instance
     */
    public static LogmasterSettings setup() {
        return new LogmasterSettings();
    }
    public void apply() {
        HLogger.applySettings(this);
    }
    /**
     * Set output to stdout
     * @param b true to output to stdout, false to suppress
     * @return this
     */
    public LogmasterSettings stdOut(boolean b) {
        this.stdOut=b;
        return this;
    }
    /**
     * Enable output to stdout
     * @return this
     */
    public LogmasterSettings stdOut() {
        this.stdOut=true;
        return this;
    }
    /**
     * Set output to stderr
     * @param b true to output to stderr, false to suppress
     * @return this
     */
    public LogmasterSettings stdErr(boolean b) {
        this.stdErr=b;
        return this;
    }
    /**
     * Enable output to stderr
     * @return this
     */
    public LogmasterSettings stdErr() {
        this.stdErr=true;
        return this;
    }
    /**
     * Set output to file
     * @param b true to output to file, false to suppress
     * @return this
     */
    public LogmasterSettings outputToFile(boolean b) {
        this.outputToFile=b;
        return this;
    }
    /**
     * Enable output to file
     * @return this
     */
    public LogmasterSettings outputToFile() {
        this.outputToFile=true;
        return this;
    }
    public LogmasterSettings outputFine(boolean b) {
        this.outputFine=b;
        return this;
    }
    public LogmasterSettings outputFine() {
        this.outputFine=true;
        return this;
    }
}

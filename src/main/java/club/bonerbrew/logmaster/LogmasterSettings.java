package club.bonerbrew.logmaster;

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
    public static LogmasterSettings c() {
        return new LogmasterSettings();
    }
    public void apply() {
        HLogger.applySettings(this);
    }
    public LogmasterSettings stdOut(boolean b) {
        this.stdOut=b;
        return this;
    }
    public LogmasterSettings stdOut() {
        this.stdOut=true;
        return this;
    }
    public LogmasterSettings stdErr(boolean b) {
        this.stdErr=b;
        return this;
    }
    public LogmasterSettings stdErr() {
        this.stdErr=true;
        return this;
    }
    public LogmasterSettings outputToFile(boolean b) {
        this.outputToFile=b;
        return this;
    }
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

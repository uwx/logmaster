package fallk.logmaster;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public final class NullOutputStream extends OutputStream implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 0L;
    
    public NullOutputStream() {
        
    }

    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "NullOutputStream. There is nothing here.";
    }

}

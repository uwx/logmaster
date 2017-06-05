package fallk.logmaster;

import java.io.Serializable;

@SuppressWarnings("all")
public class Dummy implements Serializable, Cloneable, Comparable<Dummy> {

    /**
     * 
     */
    private static final long serialVersionUID = -856474354173609485L;
    
    private int i = 32434;
    private long l = 15123;
    private byte b = 120;
    private float f = 55555;
    private String s = "string!";
    private Dummy d = null;
    private String b2 = null;

    @Override
    public int compareTo(Dummy o) {
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + b;
        result = prime * result + ((b2 == null) ? 0 : b2.hashCode());
        result = prime * result + ((d == null) ? 0 : d.hashCode());
        result = prime * result + Float.floatToIntBits(f);
        result = prime * result + i;
        result = prime * result + (int) (l ^ (l >>> 32));
        result = prime * result + ((s == null) ? 0 : s.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dummy other = (Dummy) obj;
        if (b != other.b)
            return false;
        if (b2 == null) {
            if (other.b2 != null)
                return false;
        } else if (!b2.equals(other.b2))
            return false;
        if (d == null) {
            if (other.d != null)
                return false;
        } else if (!d.equals(other.d))
            return false;
        if (Float.floatToIntBits(f) != Float.floatToIntBits(other.f))
            return false;
        if (i != other.i)
            return false;
        if (l != other.l)
            return false;
        if (s == null) {
            if (other.s != null)
                return false;
        } else if (!s.equals(other.s))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Dummy [i=" + i + ", l=" + l + ", b=" + b + ", f=" + f + ", s=" + s + ", d=" + d + ", b2=" + b2 + "]";
    }

}

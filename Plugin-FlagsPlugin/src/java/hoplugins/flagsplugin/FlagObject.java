package hoplugins.flagsplugin;
/**
 * FlagObject.java
 *
 * @author Daniel González Fisher
 */

import hoplugins.FlagsPlugin;

public class FlagObject implements Comparable, java.io.Serializable {
    static final long serialVersionUID = -5727620448208543191L;

    private int id;
    transient private String simpleName;
    transient private String name;
    transient private double coolness;

    protected FlagObject() {
        id = 0;
        simpleName = "";
        name = "";
        coolness = 0.0;
    }

    public FlagObject(int i) {
        this(i, FlagsPlugin.getCountryName(i), 0.0);
    }
    public FlagObject(int i, double d) {
        this(i, FlagsPlugin.getCountryName(i), d);
    }
    public FlagObject(int i, String n) {
        this(i, n, 0.0);
    }
    public FlagObject(int i, String n, double d) {
        id = i;
        name = n;
        simpleName = FlagsPlugin.unencodePais(n);
        coolness = d;
    }

    public String getName()        { return name; }
    public String getSimpleName()  { return simpleName; }
    public String toString()       { return name; }
    public int getId()             { return id; }
    public double getCoolness()    { return coolness; }

    /* necessary for FlagCollection.contains() */
    public int hashCode() {
        return id;
    }
    public boolean equals(Object obj) {
        return id == ((FlagObject)obj).id;
    }
    /* *********************** */
    /* Interface COMPARABLE */
    public int compareTo(Object o) {
        FlagObject fo = (FlagObject)o;
        return simpleName.compareTo(fo.simpleName);
    }

    /* *********************** */
    /* Interface SERIALIZABLE */
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = FlagsPlugin.getCountryName(id);
        simpleName = FlagsPlugin.unencodePais(name);
        coolness = FlagsPlugin.getCoolnessRanking(id);
    }

    /* ******************************* */
    /* Nested class COOLNESSCOMPARATOR */
    /* ******************************* */

    public static class CoolnessComparator implements java.util.Comparator {

        public int compare(Object o1, Object o2) {
            /* reverse order, highest coolness first */
            FlagObject fo1 = (FlagObject)o1;
            FlagObject fo2 = (FlagObject)o2;
            //int result = 0;
            if ((fo1.coolness == 0.0) && (fo2.coolness == 0.0)) return fo1.compareTo(fo2);
            if (fo1.coolness > fo2.coolness) return -1;
            if (fo1.coolness == fo2.coolness) return 0;
            return 1;
        }
        public boolean equals(Object obj) { return super.equals(obj); }
    }
}

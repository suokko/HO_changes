// %572091042:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

/**
 * Filter für Sprachdateien
 */
public class SprachFileFilter implements java.io.FileFilter {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param file TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean accept(java.io.File file) {
        return file.getName().endsWith(".properties");
    }
}

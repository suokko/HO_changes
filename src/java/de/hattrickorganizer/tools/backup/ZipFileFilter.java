package de.hattrickorganizer.tools.backup;

public class ZipFileFilter implements java.io.FileFilter {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * 
     *
     * @param file 
     *
     * @return boolean
     */
    public final boolean accept(java.io.File file) {
        return file.getName().endsWith(".zip");
    }

}

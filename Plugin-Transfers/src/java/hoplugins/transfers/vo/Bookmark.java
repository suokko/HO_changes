// %1126721330901:hoplugins.transfers.vo%
package hoplugins.transfers.vo;

/**
 * Value Object representing a bookmark.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class Bookmark {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Team bookmark */
    public static final int TEAM = 0;

    /** Player bookmark */
    public static final int PLAYER = 1;

    //~ Instance fields ----------------------------------------------------------------------------

    /** Bookmark name */
    private String name;

    /** Bookmark id */
    private int id;

    /** Bookmark type */
    private int type;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates an instance of Bookmark representing a
     * bookmark of an item.
     * 
     * @param type Type of the bookmark
     * @param id Id of the bookmarked item
     * @param name name of the bookmark
     */
    public Bookmark(int type, int id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gets the id of the bookmark.
     *
     * @return Id of the bookmark
     */
    public final int getId() {
        return id;
    }

    /**
     * Gets the name of the bookmark.
     *
     * @return Name of the bookmark
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the type of the bookmark.
     *
     * @return Type of the bookmark
     */
    public final int getType() {
        return type;
    }
}

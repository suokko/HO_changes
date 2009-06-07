// %1126721329604:hoplugins.transfers.dao%
package hoplugins.transfers.dao;

import hoplugins.Commons;

import hoplugins.transfers.vo.Bookmark;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Vector;


/**
 * DAO to store and retrieve bookmarks in the HO database.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public final class BookmarkDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "transfers_bookmarks"; //$NON-NLS-1$

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation. 
     */
    private BookmarkDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gets all bookmarks of a specified type.
     *
     * @param type Type of bookmark
     *
     * @return List of bookmarks
     */
    public static List getBookmarks(int type) {
        final StringBuffer sqlStmt = new StringBuffer("SELECT * FROM " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append(" WHERE type=" + type); //$NON-NLS-1$

        final List results = new Vector();
        final ResultSet rs = Commons.getModel().getAdapter().executeQuery(sqlStmt.toString());

        if (rs == null) {
            return results;
        }

        try {
            while (rs.next()) {
                results.add(new Bookmark(type, rs.getInt("id"),
                                         Commons.getModel().getHelper().decodeStringFromDatabase(rs
                                                                                                 .getString("name"))));
            }
        } catch (SQLException e) {
        }

        return results;
    }

    /**
     * Adds a bookmark to the HO database.
     *
     * @param type Type of bookmark
     * @param id Bookmark id
     * @param name Bookmark name
     */
    public static void addBookmark(int type, int id, String name) {
        final StringBuffer sqlStmt = new StringBuffer("INSERT INTO " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append("(type, id, name) VALUES ("); //$NON-NLS-1$
        sqlStmt.append(type + ","); //$NON-NLS-1$
        sqlStmt.append(id + ","); //$NON-NLS-1$
        sqlStmt.append("'" + Commons.getModel().getHelper().encodeString4Database(name) + "',"); //$NON-NLS-1$ //$NON-NLS-2$
        sqlStmt.append(")"); //$NON-NLS-1$

        Commons.getModel().getAdapter().executeUpdate(sqlStmt.toString());
    }

    /**
     * Remove a bookmark from the HO database.
     *
     * @param type Type of bookmark
     * @param id Bookmark id
     */
    public static void removeBookmark(int type, int id) {
        final StringBuffer sqlStmt = new StringBuffer("DELETE FROM " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append(" WHERE type=" + type + " AND id=" + id); //$NON-NLS-1$

        Commons.getModel().getAdapter().executeUpdate(sqlStmt.toString());
    }

    /**
     * Method that check if the table exists, if not creates it and sets the values to default
     */
    private static void checkTable() {
        ResultSet rs = null;

        try {
            rs = Commons.getModel().getAdapter().executeQuery("SELECT * FROM " + TABLE_NAME); //$NON-NLS-1$
            rs.next();
        } catch (Exception e) {
            final StringBuffer sqlStmt = new StringBuffer("CREATE TABLE " + TABLE_NAME); //$NON-NLS-1$
            sqlStmt.append("("); //$NON-NLS-1$
            sqlStmt.append("type INTEGER NOT NULL,"); //$NON-NLS-1$
            sqlStmt.append("id INTEGER NOT NULL,"); //$NON-NLS-1$
            sqlStmt.append("name VARCHAR(256),"); //$NON-NLS-1$
            sqlStmt.append("PRIMARY KEY (type, id)"); //$NON-NLS-1$
            sqlStmt.append(")"); //$NON-NLS-1$

            Commons.getModel().getAdapter().executeUpdate(sqlStmt.toString());
        }
    }
}

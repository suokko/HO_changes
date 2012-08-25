// %1916127255:de.hattrickorganizer.model%
package ho.core.db;

import ho.core.util.HOLogger;
import ho.tool.updater.UpdateHelper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class User {
	//~ Static fields/initializers -----------------------------------------------------------------

	private static ArrayList<User> users = null;

	/** TODO Missing Parameter Documentation */
	private static final String FILENAME = "user.xml";

	/** TODO Missing Parameter Documentation */
	public static int INDEX = 0;

	//~ Instance fields ----------------------------------------------------------------------------

	private String driver = "org.hsqldb.jdbcDriver";
	private String name = "singleUser";
	private String pwd = "";
	private String url = "jdbc:hsqldb:file:db/database";
	private String user = "sa";
	private int backupLevel = 3;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new User object.
	 */
	private User() {
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public static ArrayList<User> getAllUser() {
		try {
			if (users == null) {
				load();
			}
		} catch (Exception ex) {
			HOLogger.instance().log(User.class,ex);
		}

		return users;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public static User getCurrentUser() {
		try {
			if (users == null) {
				load();
			}
		} catch (Exception ex) {
			HOLogger.instance().log(User.class,ex);
		}

		return users.get(INDEX);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final String getDriver() {
		return driver;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final String getName() {
		return name;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final String getPwd() {
		return pwd;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isSingleUser() {
		return (users.size() == 1) ? true : false;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	public static void load() throws Exception {
		File file = getFile();
		users = new ArrayList<User>();

		if (file.exists()) {
			Document doc = UpdateHelper.instance().getDocument(file);
			parseFile(doc.getChildNodes());
		} else {
			users.add(new User());
			save();
		}
		// add default user, if there was none loaded (e.g. corrupt user.xml)
		if (users.size()<1) {
			users.add(new User());
			save();
		}
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String getDBPath() {
		try {
			return url.substring(url.indexOf("file") + 5, url.indexOf("database") - 1);
		} catch (RuntimeException e) {
			return "db";
		}
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isHSQLDB() {
		return getDriver().equalsIgnoreCase("org.hsqldb.jdbcDriver");
	}

	/**
	 * TODO Missing Method Documentation
	 */
	public static void save() {
		try {
			File file = getFile();

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write("<?xml version='1.0' encoding='ISO-8859-1' ?>" + "\r\n");
			fileWriter.write("<HoUsers>\r\n");

			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				fileWriter.write(" <User>\r\n");
				fileWriter.write("   <Name>" + user.name + "</Name>\r\n");
				fileWriter.write("   <Url>" + user.url + "</Url>\r\n");
				fileWriter.write("   <User>" + user.user + "</User>\r\n");
				fileWriter.write("   <Password>" + user.pwd + "</Password>\r\n");
				fileWriter.write("   <Driver>" + user.driver + "</Driver>\r\n");
				fileWriter.write("   <BackupLevel>" + user.backupLevel + "</BackupLevel>\r\n");
				fileWriter.write(" </User>\r\n");
			}

			fileWriter.write("</HoUsers>");
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			HOLogger.instance().log(User.class,e);
		}
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final String getUser() {
		return user;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getBackupLevel() {
		return backupLevel;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param name TODO Missing Method Parameter Documentation
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param name TODO Missing Method Parameter Documentation
	 */
	public final void setBackupLevel(int level) {
		backupLevel = level;
	}
	/**
	 * TODO Missing Method Documentation
	 *
	 * @param path TODO Missing Method Parameter Documentation
	 */
	public final void setPath(String path) {
		url = "jdbc:hsqldb:file:" + path + "/database";
	}

	/**
	 * TODO Missing Method Documentation
	 */
	public static void addNewUser() {
		User newUser = new User();
		newUser.setName("user" + (users.size() + 1));
		newUser.setPath("db" + (users.size() + 1));
		users.add(newUser);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private static File getFile() {
		return new File(System.getProperty("user.dir") + File.separator + FILENAME);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param elements TODO Missing Method Parameter Documentation
	 */
	private static void parseFile(NodeList elements) {
		try {
			for (int i = 0; i < elements.getLength(); i++) {
				if (elements.item(i) instanceof Element) {
					Element element = (Element) elements.item(i);
					Text txt = (Text) element.getFirstChild();

					if (txt != null) {
						if (element.getTagName().equals("HoUsers")) {
							parseFile(element.getChildNodes());
						}

						if (element.getTagName().equals("User")) {
							User tmp = new User();
							tmp.parseUser(element.getChildNodes());
							users.add(tmp);
						}
					}
				}
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param elements TODO Missing Method Parameter Documentation
	 */
	private void parseUser(NodeList elements) {
		try {
			for (int i = 0; i < elements.getLength(); i++) {
				if (elements.item(i) instanceof Element) {
					Element element = (Element) elements.item(i);
					Text txt = (Text) element.getFirstChild();

					if (txt != null) {
						if (element.getTagName().equals("Name")) {
							name = txt.getData().trim();
						}

						if (element.getTagName().equals("Url")) {
							url = txt.getData().trim();
						}

						if (element.getTagName().equals("User")) {
							user = txt.getData().trim();
						}

						if (element.getTagName().equals("Password")) {
							pwd = txt.getData().trim();
						}

						if (element.getTagName().equals("Driver")) {
							driver = txt.getData().trim();
						}

						if (element.getTagName().equals("BackupLevel")) {
							backupLevel = Integer.parseInt(txt.getData().trim());
						}
					}
				}
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * Check if user decided to keep data in current path
	 *
	 * @return true if data is in current path
	 */
	public static boolean isDataInCurrentPath(File dataPath) {
		// TODO Allow user to fallback to old data path.
		return true;
	}
}

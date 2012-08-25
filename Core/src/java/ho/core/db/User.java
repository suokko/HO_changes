// %1916127255:de.hattrickorganizer.model%
package ho.core.db;

import ho.core.util.HOLogger;
import ho.tool.updater.UpdateHelper;
import ho.core.util.HOFile;

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

	private static final int DIR_UNDECIDED = 0;
	private static final int DIR_CWD = 1;
	private static final int DIR_HOME = 2;

	private static ArrayList<User> users = null;
	private static int dataDirSelection = DIR_UNDECIDED;

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
	public static boolean load(File file) throws Exception {
		users = new ArrayList<User>();
		dataDirSelection = DIR_UNDECIDED;

		if (file.exists()) {
			Document doc = UpdateHelper.instance().getDocument(file);
			parseFile(doc.getChildNodes());
		}
		// add default user, if there was none loaded (e.g. corrupt user.xml)
		return users.size() < 1;
	}

	public static void load() throws Exception {
		File file = getFile();
		if (load(file)) {
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

			if (dataDirSelection != DIR_UNDECIDED) {
				fileWriter.write("  <Options>\r\n");
				fileWriter.write("    <DataDir>" + (dataDirSelection == DIR_HOME ? "Home" : "Current") + "</DataDir>\r\n");
				fileWriter.write("  </Options>\r\n");
			}

			fileWriter.write("</HoUsers>\r\n");
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
		if (isHSQLDB()) {
			HOFile f = new HOFile(getDBPath(), HOFile.USER_ONLY);
			return "jdbc:hsqldb:file:" + new HOFile(f, "database").getPath();
		} else {
			return url;
		}
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
		url = "jdbc:hsqldb:file:" + path + File.separator + "database";
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
		return new HOFile(FILENAME, HOFile.SHARED);
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

						if (element.getTagName().equals("Options")) {
							parseFile(element.getChildNodes());
						}

						if (element.getTagName().equals("DataDir")) {
							if (txt.getData().trim().equals("Home")) {
								dataDirSelection = DIR_HOME;
							} else {
								dataDirSelection = DIR_CWD;
							}
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
	 * Check if user decided to keep data in current path. user.xml is searched
	 * first from dataPath and then from current working directory.
	 *
	 * This function MUST NOT use HOFile because HOFile uses this in construction.
	 *
	 * @return true if data is in current path
	 */
	public static boolean isDataInCurrentPath(File dataPath) {
		boolean needsMigration = false;
		/* Is result already known? */
		if (dataDirSelection != DIR_UNDECIDED)
			return dataDirSelection == DIR_CWD;

		/* Does proposed dataPath have user.xml? */
		try {
			load(new File(dataPath, FILENAME));
		} catch (Exception e) {
			HOLogger.instance().log(User.class, e);
		}

		if (dataDirSelection == DIR_HOME) {
			HOLogger.instance().info(User.class, "Using data from '" +
					(dataDirSelection == DIR_HOME ? dataPath.getAbsolutePath() :
						new File(System.getProperty("user.dir")).getAbsoluteFile()) +
					"' based on home directory.");
			return dataDirSelection == DIR_CWD;
		}

		/* Does current working directory have user.xml? */
		try {
			needsMigration = !load(new File(System.getProperty("user.dir"), FILENAME));
		} catch (Exception e) {
			HOLogger.instance().log(User.class, e);
		}

		if (dataDirSelection != DIR_UNDECIDED) {
			if (dataDirSelection == DIR_CWD) {
				HOLogger.instance().info(User.class, "Using data from '" +
						(dataDirSelection == DIR_HOME ? dataPath.getAbsolutePath() :
							new File(System.getProperty("user.dir")).getAbsoluteFile()) +
						"' based on working directory.");
				return true;
			} else {
				/* user.xml is configured for migration */
				needsMigration = true;
			}
		}

		/* If user configuration exists in current working directory but
		 * user haven't decided yet about migration we ask user if data files
		 * should be migrated to dataPath.
		 */
		if (needsMigration) {
			/* TODO Ask user if migration is wanted if not configured in user.xml */

			if (dataDirSelection == DIR_HOME) {
				try {
					HOFile.migrateWritableFiles(users);
				} catch (Exception e) {
					HOLogger.instance().log(User.class, e);
					System.exit(-1);
				}
			}

			/* Save user selection and user.xml to correct location */
			if (dataDirSelection != DIR_UNDECIDED) {
				save();
			}

			HOLogger.instance().info(User.class, "Using data from '" +
					(dataDirSelection == DIR_HOME ? dataPath.getAbsolutePath() :
						new File(System.getProperty("user.dir")).getAbsoluteFile()) +
					"' based on user selection.");
		} else {
			/* No configured users found. We create a new user.xml to dataPath. */
			dataDirSelection = DIR_HOME;
			users.add(new User());
			save();
			HOLogger.instance().info(User.class, "Using data from '" +
					(dataDirSelection == DIR_HOME ? dataPath.getAbsolutePath() :
						new File(System.getProperty("user.dir")).getAbsoluteFile()) +
					"' for a new user.");
		}

		return dataDirSelection == DIR_UNDECIDED || dataDirSelection == DIR_CWD;
	}
}

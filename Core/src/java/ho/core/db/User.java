// %1916127255:de.hattrickorganizer.model%
package ho.core.db;

import ho.core.util.HOLogger;
import ho.tool.updater.UpdateHelper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class User {

	private static List<User> users = null;
	private static final String FILENAME = "user.xml";
	public static int INDEX = 0;
	private String driver = "org.hsqldb.jdbcDriver";
	private String name = "singleUser";
	private String pwd = "";
	private String url = "jdbc:hsqldb:file:db/database";
	private String user = "sa";
	private int backupLevel = 3;

	/**
	 * Creates a new User object.
	 */
	private User() {
	}

	public static List<User> getAllUser() {
		try {
			if (users == null) {
				load();
			}
		} catch (Exception ex) {
			HOLogger.instance().log(User.class, ex);
		}

		return users;
	}

	public static User getCurrentUser() {
		try {
			if (users == null) {
				load();
			}
		} catch (Exception ex) {
			HOLogger.instance().log(User.class, ex);
		}

		return users.get(INDEX);
	}

	public final String getDriver() {
		return driver;
	}

	public final String getName() {
		return name;
	}

	public final String getPwd() {
		return pwd;
	}

	public boolean isSingleUser() {
		return (users.size() == 1) ? true : false;
	}

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
		if (users.size() < 1) {
			users.add(new User());
			save();
		}
	}

	public String getDBPath() {
		try {
			return url.substring(url.indexOf("file") + 5, url.indexOf("database") - 1);
		} catch (RuntimeException e) {
			return "db";
		}
	}

	public boolean isHSQLDB() {
		return getDriver().equalsIgnoreCase("org.hsqldb.jdbcDriver");
	}

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
			HOLogger.instance().log(User.class, e);
		}
	}

	public final String getUrl() {
		return url;
	}

	public final String getUser() {
		return user;
	}

	public int getBackupLevel() {
		return backupLevel;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setBackupLevel(int level) {
		backupLevel = level;
	}

	public final void setPath(String path) {
		url = "jdbc:hsqldb:file:" + path + "/database";
	}

	public static User addNewUser() {
		User newUser = new User();
		newUser.setName("user" + (users.size() + 1));
		newUser.setPath("db" + (users.size() + 1));
		users.add(newUser);
		return newUser;
	}

	@Override
	public String toString() {
		return name;
	}

	public static boolean isNameUnique(String name) {
		for (User user : getAllUser()) {
			if (user.getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDBPathUnique(String path) {
		for (User user : getAllUser()) {
			if (user.getDBPath().equalsIgnoreCase(path)) {
				return false;
			}
		}
		return true;
	}

	private static File getFile() {
		return new File(System.getProperty("user.dir") + File.separator + FILENAME);
	}

	private static void parseFile(NodeList elements) {
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
	}

	private void parseUser(NodeList elements) {
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
	}
}

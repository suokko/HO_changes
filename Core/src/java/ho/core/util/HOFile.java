package ho.core.util;

import ho.core.db.User;
import ho.core.file.ExampleFileFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface to the JNI wrapper module for windows native library call.
 */
class Win32ShellWrapper {
	static {
		String libName = "Shell32Wrapper";
		if (System.getProperty("os.arch").indexOf("64") >= 0)
			libName += "64";
		System.load(new File(System.mapLibraryName(libName)).getAbsolutePath());
	}
	public native String GetAppDataPath();
}

/**
 * Data path selection class that extends java.io.File to make using it very
 * simple. In most case it is enough to replace File with HOFile and select if
 * the file is shared or per user one.
 */
public class HOFile extends File {
	private static final long serialVersionUID = 1L;

	/**
	 * Helper constants for HOFile type
	 */
	public static final boolean SHARED = true;
	public static final boolean USER_ONLY = false;

	private static File dataPath = null;

	/**
	 * Construct a data directory object.
	 *
	 * @param path Relative path from user selected data directory
	 * @param shared Is the directory shared to all users?
	 */
	public HOFile(String path, boolean shared) {
		super(getDataPath(shared), path);
	}

	/**
	 * Append to existing HOFile path
	 *
	 * @param parent Path inside data directory
	 * @param child A component to append to the path
	 */
	public HOFile(HOFile parent, String child) {
		super(parent, child);
	}

	/**
	 * Selects data directory path based on system and user configuration.
	 *
	 * @param shared Is wanted path shared between users?
	 * @return Absolute path to data directory
	 */
	private static File getDataPath(boolean shared) {
		if (dataPath == null) {
			dataPath = computeDataPath();
			HOLogger.instance().info(HOFile.class, "'" + dataPath.getAbsolutePath() +
					"' is system application data path.");
		}

		/* Check user configuration */
		if (User.isDataInCurrentPath(dataPath))
			return new File(System.getProperty("user.dir"));

		/* The new location has per user directories */
		File rv = dataPath;
		if (!shared) {
			User user = User.getCurrentUser();
			rv = new File(rv, user.getName());
		}
		return rv;
	}

	/**
	 * Copy the file data from source to destination
	 *
	 * @param dst
	 * @param src
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void copyFile(File dst, File src) throws FileNotFoundException, IOException {
		FileInputStream s = null;
		FileOutputStream d = null;
		try {
			long len = src.length();
			long pos = 0;
			s = new FileInputStream(src);
			d = new FileOutputStream(dst);
			while (pos < len) {
				long t = d.getChannel().transferFrom(s.getChannel(), pos, len);
				if (t == 0) {
					throw new IOException("No data transfered");
				}
				pos += t;
			}
			dst.setLastModified(src.lastModified());
		} finally {
			if (s != null)
				s.close();
			if (d != null)
				d.close();
		}
	}

	/**
	 * Migrate all data files to the new location for all currently configured
	 * users.
	 *
	 * @param users The list of users configured
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void migrateWritableFiles(ArrayList<User> users) throws FileNotFoundException, IOException {
		File cwd = new File(System.getProperty("user.dir"));
		HOLogger.instance().log(HOFile.class, "Migrating data from '"+ cwd.getAbsolutePath() +"'");

		/* Create shared directory */
		dataPath.mkdirs();
		/* user.xml migration is handled in User after call to here */

		/* Migrate logs */
		File logs = new File(dataPath, "logs");
		File old_logs = new File(cwd, "logs");
		ExampleFileFilter filter = new ExampleFileFilter("log");
		File[] files = old_logs.listFiles(filter);
		logs.mkdir();
		for (int i = 0; i < files.length; i++) {
			copyFile(new File(logs, files[i].getName()), files[i]);
		}
	}

	/**
	 * Select system specific application data path
	 *
	 */
	private static File computeDataPath() {
		String os = System.getProperty("os.name").toLowerCase();
		String path;
		if (os.indexOf("win") >= 0) {
			Win32ShellWrapper sh = new Win32ShellWrapper();
			path = sh.GetAppDataPath();

			/* Fallback to user.home and hope for the best */
			if (path == null) {
				HOLogger.instance().warning(HOFile.class,
						"computeDataPath() failed to get application data path.");
				path = System.getProperty("user.home");
			}

		} else if (os.indexOf("mac") >= 0) {
			/* Mac convention is $HOME/Library/Application Support */
			path = System.getProperty("user.home") + File.separator +
					"Library" + File.separator + "Application Support";
		} else {
			/* Other follow XDG base directory specification: $HOME/.config */
			if (System.getenv().containsKey("XDG_CONFIG_HOME") &&
					System.getenv("XDG_CONFIG_HOME").length() > 0)
				path = System.getenv("XDG_CONFIG_HOME");
			else
				path = System.getProperty("user.home") + File.separator + ".config";
		}

		/* Append application name to the path */
		return new File(path, "Hattrick Organizer");
	}
}

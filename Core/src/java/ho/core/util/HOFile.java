package ho.core.util;


import java.io.File;

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

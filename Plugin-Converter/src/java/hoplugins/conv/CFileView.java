package hoplugins.conv;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileView;

final class CFileView extends FileView {

	public String getName(File f) {
		String tmp = f.getName();
		int index = tmp.lastIndexOf(".");
		if (RSC.isHTForverFile(f)) {
			return tmp.substring(0, 11);
		}
		if (RSC.isHTCoachFile(f)) {
			return tmp.substring(tmp.indexOf("_") + 1, index);
		}

		if (index > 0)
			return tmp.substring(0, index);
		return null;
	}

	public String getDescription(File f) {
		return null;
	}

	public Boolean isTraversable(File f) {
		return null;
	}

	public String getTypeDescription(File f) {
		return null;
	}

	public Icon getIcon(File f) {
		return null;
	}
}

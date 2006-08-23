package de.hattrickorganizer.tools;


import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Enanched ImageIcon class with better handling of copy and paste
 * 
 * @author Draghetto
 *
 */
class ExtendedImageIcon extends ImageIcon {
	
	public String description;
	
	public ExtendedImageIcon(String arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(URL arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(URL arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(Image arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(Image arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(byte[] arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon(byte[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ExtendedImageIcon() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return description;
	}

	public void setIconDescription(String string) {
		this.description = string;
	}
}

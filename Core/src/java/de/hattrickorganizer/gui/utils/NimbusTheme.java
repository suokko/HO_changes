package de.hattrickorganizer.gui.utils;

import gui.UserParameter;

import java.awt.Font;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.hattrickorganizer.tools.HOLogger;

public class NimbusTheme {
	
	private NimbusTheme() {
	}
	
	public static boolean enableNimbusTheme(int fontSize) {
		try {
			LookAndFeelInfo nimbus = null;
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            nimbus = info;
		            break;
		        }
		    }
			
			if (nimbus != null) {
				UIDefaults uid = new UIDefaults();
				final String fontName = FontUtil.getFontName(UserParameter.instance().sprachDatei);
				uid.put("defaultFont", new Font((fontName != null ? fontName : "SansSerif"), Font.PLAIN, fontSize));
				uid.put("Table.showGrid", true);

				if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Object mbUI = UIManager.get("MenuBarUI");
					Object mUI = UIManager.get("MenuUI");
					Object cbmiUI = UIManager.get("CheckBoxMenuItemUI");
					Object rbmiUI = UIManager.get("RadioButtonMenuItemUI");
					Object pmUI = UIManager.get("PopupMenuUI");

					UIManager.setLookAndFeel(nimbus.getClassName());

					UIManager.put("MenuBarUI", mbUI);
					UIManager.put("MenuUI", mUI);
					UIManager.put("CheckBoxMenuItemUI", cbmiUI);
					UIManager.put("RadioButtonMenuItemUI", rbmiUI);
					UIManager.put("PopupMenuUI", pmUI);
				} else {
					UIManager.setLookAndFeel(nimbus.getClassName());
				}
				return true;
			}
		} catch (Exception e) {
			HOLogger.instance().log(NimbusTheme.class, e);
		}
		return false;
	}

}

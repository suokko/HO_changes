package de.hattrickorganizer.gui.utils;

import gui.UserParameter;

import java.awt.Font;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;

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
				
				UIDefaults uid = UIManager.getLookAndFeelDefaults();
				final String fontName = FontUtil.getFontName(UserParameter.instance().sprachDatei);
				final Font userFont = new Font((fontName != null ? fontName : "SansSerif"), Font.PLAIN, fontSize);
				final Font boldFont = new Font((fontName != null ? fontName : "SansSerif"), Font.BOLD, fontSize);
				uid.put("defaultFont", userFont);
				uid.put("DesktopIcon.font", userFont);
				uid.put("FileChooser.font", userFont);
				uid.put("RootPane.font", userFont);
				uid.put("TextPane.font", userFont);
				uid.put("FormattedTextField.font", userFont);
				uid.put("Spinner.font", userFont);
				uid.put("PopupMenuSeparator.font", userFont);
				uid.put("Table.font", userFont);
				uid.put("TextArea.font", userFont);
				uid.put("Slider.font", userFont);
				uid.put("InternalFrameTitlePane.font", userFont);
				uid.put("ColorChooser.font", userFont);
				uid.put("DesktopPane.font", userFont);
				uid.put("Menu.font", userFont);
				uid.put("PasswordField.font", userFont);
				uid.put("InternalFrame.font", userFont);
				uid.put("InternalFrame.titleFont", boldFont);
				uid.put("Button.font", userFont);
				uid.put("Panel.font", userFont);
				uid.put("MenuBar.font", userFont);
				uid.put("ComboBox.font", userFont);
				uid.put("Tree.font", userFont);
				uid.put("EditorPane.font", userFont);
				uid.put("CheckBox.font", userFont);
				uid.put("ToggleButton.font", userFont);
				uid.put("TabbedPane.font", userFont);
				uid.put("TableHeader.font", userFont);
				uid.put("List.font", userFont);
				uid.put("PopupMenu.font", userFont);
				uid.put("ToolTip.font", userFont);
				uid.put("Separator.font", userFont);
				uid.put("RadioButtonMenuItem.font", userFont);
				uid.put("RadioButton.font", userFont);
				uid.put("ToolBar.font", userFont);
				uid.put("ScrollPane.font", userFont);
				uid.put("CheckBoxMenuItem.font", userFont);
				uid.put("Viewport.font", userFont);
				uid.put("TextField.font", userFont);
				uid.put("SplitPane.font", userFont);
				uid.put("MenuItem.font", userFont);
				uid.put("OptionPane.font", userFont);
				uid.put("ArrowButton.font", userFont);
				uid.put("Label.font", userFont);
				uid.put("ProgressBar.font", userFont);
				uid.put("ScrollBar.font", userFont);
				uid.put("ScrollBarThumb.font", userFont);
				uid.put("ScrollBarTrack.font", userFont);
				uid.put("SliderThumb.font", userFont);
				uid.put("SliderTrack.font", userFont);
				uid.put("TitledBorder.font", boldFont);
				
				uid.put("Table.intercellSpacing", new DimensionUIResource(1, 1));
				uid.put("Table.showGrid", new Boolean(true));
				uid.put("Table.gridColor", new ColorUIResource(214, 217, 223));
				
				return true;
			}
		} catch (Exception e) {
			HOLogger.instance().log(NimbusTheme.class, e);
		}
		return false;
	}

}
